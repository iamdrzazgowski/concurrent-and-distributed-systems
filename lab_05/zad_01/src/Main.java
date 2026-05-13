import java.util.Random;
import java.util.concurrent.Semaphore;

class Biblioteka {
    private final int K;
    private final Semaphore[] czytelnie;
    private final Semaphore[] ksiazki;

    Biblioteka(int n, int k) {
        K = k;

        czytelnie = new Semaphore[2];
        czytelnie[0] = new Semaphore(k, true);
        czytelnie[1] = new Semaphore(n, true);

        ksiazki = new Semaphore[n];
        for(int i = 0; i < n; i++){
            ksiazki[i] = new Semaphore(1, true);
        }
    }

    public void enterAndRead(int readerId, int bookId, int roomId) throws InterruptedException {
        czytelnie[roomId].acquire();
        ksiazki[bookId].acquire();

        System.out.println("Reader " + readerId +
                " reading book " + bookId +
                " in room " + roomId);

        Thread.sleep(50);

        ksiazki[bookId].release();
        czytelnie[roomId].release();

    }
}

class Czytelnik extends Thread {
    private final Biblioteka biblioteka;
    private final int id;
    private final int bookId;
    private final int roomId;


    Czytelnik(Biblioteka biblioteka, int id, int bookId, int roomId) {
        this.biblioteka = biblioteka;
        this.id = id;
        this.bookId = bookId;
        this.roomId = roomId;
    }

    public void run(){
        while(true){
            try {
                biblioteka.enterAndRead(id, bookId, roomId);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        int N = 10;
        int K = 3;
        Random r = new Random();

        Biblioteka biblioteka = new Biblioteka(N, K);
        Czytelnik[] czytelnicy = new Czytelnik[N];

        for(int i = 0; i < N; i++){
            int bookId = r.nextInt(N);
            int roomId = r.nextInt(2);

            czytelnicy[i] = new Czytelnik(biblioteka, i, bookId, roomId);
            czytelnicy[i].start();
        }
    }
}