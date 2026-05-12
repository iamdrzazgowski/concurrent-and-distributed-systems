import java.util.concurrent.Semaphore;

class Filozof extends Thread {
    private int id;
    private Semaphore left;
    private Semaphore right;
    private Semaphore jadalnia;
    private boolean asymetryczny;

    public Filozof(int id, Semaphore left, Semaphore right, boolean asymetryczny) {
        this.id = id;
        this.left = left;
        this.right = right;
//        this.jadalnia = jadalnia;
        this.asymetryczny = asymetryczny;
    }

    private void myslenie() throws InterruptedException {
        System.out.println("Filozof " + id +" mysli");
        Thread.sleep(1000);
    }

    private void jedzenie() throws InterruptedException {
        System.out.println("Filozof " + id +" je");
        Thread.sleep(1000);
    }

    public void run(){
        try{
            while(true){
                myslenie();

//                jadalnia.acquire();

                if(asymetryczny){
                    left.acquire();
                    System.out.println("Filozof " + id + " bierze lewy widelec");

                    right.acquire();
                    System.out.println("Filozof " + id + " bierze prawy widelec");
                }else{
                    right.acquire();
                    System.out.println("Filozof " + id + " bierze prawy widelec");

                    left.acquire();
                    System.out.println("Filozof " + id + " bierze lewy widelec");
                }

                jedzenie();

                left.release();
                right.release();
                System.out.println("Filozof " + id + " odłożył widelce");

                System.out.println("Filozof " + id + " skończył jeść");
//                jadalnia.release();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Semaphore[] widelce = new Semaphore[5];

        for(int i = 0; i < 5; i++){
            widelce[i] = new Semaphore(1);
        }

        Semaphore jadalnia = new Semaphore(4);
        Filozof[] filozofowie = new Filozof[5];

        for(int i = 0; i < 5; i++){
            Semaphore left = widelce[i];
            Semaphore right = widelce[(i + 1) % 5];

            boolean asymetryczny = (i == 0);

            filozofowie[i] = new Filozof(i, left, right, asymetryczny);
            filozofowie[i].start();
        }
    }
}