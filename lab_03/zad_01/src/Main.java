import java.util.Random;
import java.util.concurrent.Semaphore;

class MyThread extends Thread {
    private Semaphore semaphore;
    private int id;
    Random random = new Random();

    MyThread(Semaphore semaphore, int id) {
        this.semaphore = semaphore;
        this.id = id;
    }

    public void run() {
        try{
            System.out.println("Wątek " + this.id + " zaczą prace");

            System.out.println("Wątek " + this.id + " czeka...");
            Thread.sleep(random.nextInt(5000));

            semaphore.acquire();

            System.out.println("Wątek " + this.id + " wszedł do sekcji krytycznej");

            Thread.sleep(random.nextInt(3000));

            System.out.println("Wątek " + this.id + " opuszcza sekcje krytyczną");
            semaphore.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);

        MyThread myThread = new MyThread(semaphore, 1);
        MyThread myThread2 = new MyThread(semaphore, 2);

        myThread.start();
        myThread2.start();
    }
}