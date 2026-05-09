class WatekA extends Thread {
    public void run() {
        for(int i = 1; i < 34; i++){
            System.out.println(i);
            Thread.yield();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
class WatekB extends Thread {
    public void run() {
        for(int i = 50; i < 89; i++){
            System.out.println(i);
            Thread.yield();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
class WatekC extends Thread {
    public void run() {
        for(int i = 100; i < 131; i++){
            System.out.println(i);
            Thread.yield();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        WatekA a = new WatekA();
        WatekB b = new WatekB();
        WatekC c = new WatekC();

        a.start();
//        a.join();
        b.start();
//        b.join();
        c.start();
    }
}