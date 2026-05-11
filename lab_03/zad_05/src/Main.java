import java.util.Random;
import java.util.concurrent.Semaphore;

class Buffer {
    private static int SIZE = 5;

    private int[] buffer = new int[SIZE];

    private int in = 0;
    private int out = 0;

    private Semaphore empty = new Semaphore(SIZE);
    private Semaphore full = new Semaphore(0);
    private Semaphore mutex = new Semaphore(1);

    public void put(int value, int producerId){
        empty.acquireUninterruptibly();
        mutex.acquireUninterruptibly();

        buffer[in] = value;

        System.out.println("Producent " + producerId +
                " zapisal " + value +
                " na pozycji " + in);

        in = (in + 1) % SIZE;

        mutex.release();
        full.release();
    }

    public int get(){
        full.acquireUninterruptibly();
        mutex.acquireUninterruptibly();

        int value = buffer[out];

        System.out.println("Konsument pobral " + value +
                " z pozycji " + out);

        out = (out + 1) % SIZE;

        mutex.release();
        empty.release();

        return value;
    }
}

class Producer extends Thread {
    private Buffer buffer;
    private int producerId;
    private Random random = new Random();

    Producer(Buffer buffer, int producerId){
        this.buffer = buffer;
        this.producerId = producerId;
    }

    public void run(){
        try{
            while(true){
                int value = random.nextInt(100);

                System.out.println("Producent " + producerId +
                        " wylosowal: " + value);
                buffer.put(value, producerId);

                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Consumer extends Thread {
    private Buffer buffer;

    Consumer(Buffer buffer){
        this.buffer = buffer;
    }

    public void run(){
        try{
            while (true){
                int value = buffer.get();

                System.out.println("Konsument przetwarza: " + value);

                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();

        Producer producer1 = new Producer(buffer, 1);
        Producer producer2 = new Producer(buffer, 2);
        Producer producer3 = new Producer(buffer, 3);

        Consumer consumer = new Consumer(buffer);

        producer1.start();
        producer2.start();
        producer3.start();

        consumer.start();
    }
}