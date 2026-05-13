import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Biblioteka {
    private final int K;
    private final boolean[] bookTaken;
    private final int[] rooms;

    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition condition = lock.newCondition();

    Biblioteka(int n,int k) {
        K = k;
        this.bookTaken = new boolean[n];
        this.rooms = new int[2];
    }

    public void enterAndRead(int readerId, int bookId, int roomId) throws InterruptedException {
        lock.lock();

        try {
            while (rooms[roomId] >= K || bookTaken[bookId]) {
                condition.await();
            }

            rooms[roomId]++;
            bookTaken[bookId] = true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

        System.out.println("Reader " + readerId +
                " reading book " + bookId +
                " in room " + roomId);

        Thread.sleep(50);

        lock.lock();

        try{
            bookTaken[bookId] = false;
            rooms[roomId]--;
            condition.signalAll();
        }finally {
            lock.unlock();
        }
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
            try{
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
            int roomId = rand.nextInt(2);

            czytelnicy[i] = new Czytelnik(biblioteka, i, bookId, roomId);
            czytelnicy[i].start();
        }
    }
}