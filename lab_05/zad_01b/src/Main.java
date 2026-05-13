import java.util.Random;

class Biblioteka {
    private final int K;
    private final boolean[] booksTaken;
    private final int[] rooms;

    Biblioteka(int n, int k) {
        K = k;

        this.booksTaken = new boolean[n];
        this.rooms = new int[k];
    }

    private synchronized void aquireResources(int readerId, int bookId, int roomId) throws InterruptedException {
        while(booksTaken[bookId] || rooms[roomId] >= K){
            wait();
        }

        rooms[roomId]++;
        booksTaken[bookId] = true;
    }

    private synchronized void releaseResources(int bookId, int roomId) throws InterruptedException {
        booksTaken[bookId] = false;
        rooms[roomId]--;

        notifyAll();
    }

    public void enterAndRead(int readerId, int bookId, int roomId) throws InterruptedException {
        aquireResources(readerId, bookId, roomId);

        System.out.println("Reader " + readerId +
                " reads book " + bookId +
                " in room " + roomId);

        Thread.sleep(1000);
        releaseResources(bookId, roomId);

        System.out.println("Reader " + readerId + " leaves");
    }
}

class Czytelnik extends Thread {
    private final Biblioteka biblioteka;
    private final int id;
    private final int roomId;
    private final int bookId;

    Czytelnik(Biblioteka biblioteka, int id, int roomId, int bookId) {
        this.biblioteka = biblioteka;
        this.id = id;
        this.roomId = roomId;
        this.bookId = bookId;
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

        Random rand = new Random();
        Biblioteka biblioteka = new Biblioteka(N, K);
        Czytelnik[] czytelnicy = new Czytelnik[N];

        for(int i = 0; i < N; i++){
            int bookId = rand.nextInt(N);
            int roomId = rand.nextInt(K);

            czytelnicy[i] = new Czytelnik(biblioteka, i, roomId, bookId);
            czytelnicy[i].start();
        }
    }
}