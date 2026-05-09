import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) {
        BlockingQueue<Integer> even = new LinkedBlockingQueue<>();
        BlockingQueue<Integer> odd = new LinkedBlockingQueue<>();

        Thread evenThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int n = 2;
                while(true){
                    try{
                        even.put(n);
                        System.out.println("Dodano: " + n);
                        n += 2;

                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        Thread oddThread = new Thread(new Runnable() {

            @Override
            public void run() {
                int n = 1;
                while(true){
                    try{
                        odd.put(n);
                        System.out.println("Dodano: " + n);
                        n += 2;

                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        Thread readThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while(true){
                    try {
                        int evenNum = even.take();
                        int oddNum = odd.take();

                        int max = Math.max(evenNum, oddNum);

                        System.out.println("większą liczbą jest " + max);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        evenThread.start();
        oddThread.start();
        readThread.start();
    }
}