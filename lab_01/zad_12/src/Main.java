import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("n = ");
        int n = in.nextInt();

        System.out.println("liczba wątków = ");
        int threads = in.nextInt();

        ExecutorService executor = Executors.newFixedThreadPool(threads);
        Future<Double>[] futures = new Future[threads];

//        n = 40, t = 4, chunk = 10
        int chunk = n / threads;

        for(int i = 0; i < threads; i++){
            int start = i * chunk + 1;
            int end = (i == threads - 1) ? n : (i + 1) * chunk;

            futures[i] = executor.submit(() -> {
                double sum = 0.0;

                for(int j = start; j <= end; j++){
                    sum += 1.0/j;
                }
                return sum;
            });
        }

        double res = 0.0;

        for(int i = 0; i < futures.length; i++){
            try {
                res += futures[i].get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        executor.shutdown();

        System.out.println(res);
    }
}