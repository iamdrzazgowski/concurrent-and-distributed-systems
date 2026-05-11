import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

class MyThread extends Thread {
    private int id;
    private int totalThreads;

    private static Semaphore mutex = new Semaphore(1);
    private static Semaphore barier = new Semaphore(0);
    private static Semaphore critical = new Semaphore(1);

    private static int counter = 0;
    private Random rand = new Random();

    MyThread(int id, int totalThreads) {
        this.id = id;
        this.totalThreads = totalThreads;
    }

    public void run() {
        try{
            System.out.println("Wątek numer " + this.id + " rozpoczyna pracę");

            mutex.acquire();
            counter++;

            if(counter == totalThreads) {
                barier.release(totalThreads);
            }

            mutex.release();

            barier.acquire();
            System.out.println("Wątek " + this.id + " czeka...");

            Thread.sleep(rand.nextInt(5000));

            critical.acquire();
            System.out.println("Watek numer " + this.id + " wchodzi do sekcji krytycznej");

            Thread.sleep(rand.nextInt(3000));
            System.out.println("Watek numer " + this.id + " wychodzi z sekcji krytycznej");
            critical.release();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter number: ");
        int n = in.nextInt();

        if (n < 1 || n > 8) {
            System.out.println("Niepoprawna liczba watkow!");
            return;
        }

        MyThread[] threads = new MyThread[n];

        for(int i = 0; i < n; i++){
            threads[i] = new MyThread(i + 1, n);

            threads[i].start();
        }
    }
}