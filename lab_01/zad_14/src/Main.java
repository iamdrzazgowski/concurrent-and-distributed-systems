class Worker extends Thread{
    public void run() {
        long count = 0;

        for(int i = 0;;i++){
            double mathResult = Math.atan(Math.tan(Math.sqrt(i)));
            count++;

            if(i % 1000 == 0){
                System.out.println("Worker: " + i + " iteracji");
            }

            if(Thread.currentThread().isInterrupted()){
                System.out.println("Worker interrupted");
                break;
            }
        }
    }
}

class Monitor extends Thread {
    private Worker worker;

    Monitor(Worker worker){
        this.worker = worker;
    }

    public void run() {
        while(true){
            try {
                Thread.sleep(1000);

                if(worker.isInterrupted()){
                    System.out.println("Monitor: Worker został przerwany");
                    break;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker();
        Monitor monitor = new Monitor(worker);

        worker.start();
        monitor.start();

        Thread.sleep(5000);
        worker.interrupt();
    }
}