class MyThread extends Thread {
    private int id;

    public MyThread(int id){
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Hello world! Wątek (THREAD) nr: " + id);
    }
}

class MyRunnable implements Runnable{
    private int id;
    public MyRunnable(int id){
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Hello world! Wątek (RUNNABLE) nr: " + id);
    }
}

public class Main {
    public static void main(String[] args) {

        for(int i = 1; i <= 5; i++){
            MyThread t = new MyThread(i);
            t.start();
        }

        for(int j = 6; j <= 10; j++) {
            Thread t2 = new Thread(new MyRunnable(j));
            t2.start();
        }
    }
}