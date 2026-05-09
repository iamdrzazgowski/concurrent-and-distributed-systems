import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) {

        BlockingQueue<Integer> queue1 = new LinkedBlockingQueue<>();
        BlockingQueue<Integer> queue2 = new LinkedBlockingQueue<>();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Random random = new Random();

                    int liczba = random.nextInt(100);
                    System.out.println("Wylosowano: " + liczba);
                    queue1.put(liczba);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    int liczba = queue1.take();
                    liczba += 2;

                    System.out.println("Powiększono liczbe do: " + liczba);
                    queue2.put(liczba);
                }catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int liczba = queue2.take();

                    System.out.println("Końcowa liczba: " + liczba);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}