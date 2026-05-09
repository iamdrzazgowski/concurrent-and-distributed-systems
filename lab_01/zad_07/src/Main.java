import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();

        Thread t1 = new Thread(() -> {
            while(true){
                int sum = 0;

                for(int i = 0; i < 100; i++){
                    sum += random.nextInt(100);
                }

                double avg = sum / 100.0;
                System.out.println("[1] " + avg);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    double iloczyn = 1.0;

                    for(int i = 0; i < 30; i++){
                        int liczba = random.nextInt(10) + 1;
                        iloczyn *= liczba;
                    }

                    double sredniaGeometryczna = Math.pow(iloczyn, 1.0 / 30);

                    System.out.println("[2] " + sredniaGeometryczna);

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    int x = random.nextInt(41) - 20;
                    double log = Math.log(x * x);

                    System.out.println("[3] " + log);

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}