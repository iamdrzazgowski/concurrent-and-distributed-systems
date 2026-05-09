public class Main {

    private static class MyThread extends Thread {
        public MyThread(ThreadGroup group, String name){
            super(group, name);
        }

        public void run(){
            System.out.println(Thread.currentThread().getName() + " wystartował");

            try {
                Thread.sleep(3000);

                System.out.println(Thread.currentThread().getName() + " zakończył działanie");
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " został przerwany");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ThreadGroup group = new ThreadGroup("MyThreadGroup");
        MyThread t1 = new MyThread(group, "Thread1");
        MyThread t2 = new MyThread(group, "Thread2");
        MyThread t3 = new MyThread(group, "Thread3");

        t1.start();
        t2.start();
        t3.start();

        System.out.println("Liczba działających wątków: " + group.activeCount());

        System.out.println("Lista wątków w grupie: ");
        group.list();

        Thread.sleep(2000);

        System.out.println("Przerywanie wszystkich wątków...");
        group.interrupt();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Program zakończył działanie");
    }
}