import java.util.Random;
import java.util.concurrent.Semaphore;

class Buffer{
    private static int SIZE = 5;
    private double[] buffer = new double[SIZE];

    private Semaphore empty = new Semaphore(SIZE);
    private Semaphore full = new Semaphore(0);
    private Semaphore mutex = new Semaphore(1);

    private int in = 0;
    private int out = 0;

    public void put(double v){
        empty.acquireUninterruptibly();
        mutex.acquireUninterruptibly();

        buffer[in] = v;
        System.out.println("Producent zapisal " + v + " na pozycji " + in);
        in = (in + 1) % SIZE;

        mutex.release();
        full.release();
    }

    public double get(){
        full.acquireUninterruptibly();
        mutex.acquireUninterruptibly();

        double temp = buffer[out];

        System.out.println("Konsument pobral " + temp + " z pozycji " + out);
        out = (out + 1) % SIZE;

        mutex.release();
        empty.release();

        return temp;
    }
}

class Producer extends Thread{
    private Buffer buffer;
    private Random random = new Random();

    Producer(Buffer buffer){
        this.buffer = buffer;
    }

    private double produkuj(){
        int sum = 0;

        for(int i = 0; i < 20; i++){
            int x = random.nextInt(100);

            System.out.print(x + " ");

            sum += x;
        }

        double avg = sum / 20.0;
        System.out.println("\nSrednia = " + avg);
        return avg;
    }

    public void run(){
        try{
            while(true){
                double avg = produkuj();

                buffer.put(avg);
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Consumer extends Thread{
    private Buffer buffer;

    Consumer(Buffer buffer){
        this.buffer = buffer;
    }

    public void konsumuj(double x){
        double sqrt = Math.sqrt(x);
        double log = Math.log(x);
        double square = x * x;

        System.out.println("Pierwiastek = " + sqrt);
        System.out.println("Logarytm = " + log);
        System.out.println("Kwadrat = " + square);
    }

    public void run(){
        try{
            while(true){
                double avg = buffer.get();
                konsumuj(avg);

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
        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);

        producer.start();
        consumer.start();
    }
}