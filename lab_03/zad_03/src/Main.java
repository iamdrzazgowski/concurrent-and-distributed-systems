import java.util.Random;
import java.util.concurrent.Semaphore;

class Buffer {
    private double value;

    private Semaphore empty = new Semaphore(1);
    private Semaphore full = new Semaphore(0);

    public void put(double v) {
        try {
            empty.acquire();
            value = v;

            System.out.println("Producent zapisal: " + value);
            full.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public double get(){
        try{
            full.acquire();

            double temp = value;
            System.out.println("Konsument pobrał: " + value);

            empty.release();
            return temp;

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Producer extends Thread {
    private Buffer buffer;
    private Random rand = new Random();

    Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    private double produkuj(){
        int sum = 0;

        for(int i = 0; i < 20; i++){
            int x = rand.nextInt(100);

            System.out.printf("%d ", x);
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

class Consumer extends Thread {
    private Buffer buffer;

    Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    private void konsumuj(double x){
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
                double value = buffer.get();
                konsumuj(value);

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