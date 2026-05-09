public class Main {

    private static class MyThread extends Thread {

        public MyThread(ThreadGroup group,String threadName) {
            super(group, threadName);
        }

        public void run() {
            System.out.println(Thread.currentThread().getName() + " wystartował");

            for(int i = 0; i < 50; i++){
                System.out.println(Thread.currentThread().getName() + ": " + i);

                if(isInterrupted()){
                    System.out.println(Thread.currentThread().getName() + " interrupted");
                    return;
                }
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//
//                    System.out.println(Thread.currentThread().getName() + " interrupted");
//                    return;
//                }
            }

            System.out.println(Thread.currentThread().getName() + " zakończył działanie");
        }
    }
    public static void main(String[] args) throws InterruptedException {

        ThreadGroup group = new ThreadGroup("MyThreadGroup");
        MyThread t1 = new MyThread(group,"Thred1");
        MyThread t2 = new MyThread(group, "Thred2");
        MyThread t3 = new MyThread(group,"Thred3");

        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(10);

        group.interrupt();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Wszystkie wątki zakończyły pracę.");
    }
}