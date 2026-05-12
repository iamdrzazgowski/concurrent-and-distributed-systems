import java.util.concurrent.Semaphore;

class Palacz extends Thread {
    private int id;
    private Semaphore ubijacz;
    private Semaphore zapalka;

    public Palacz(int id, Semaphore ubijacz, Semaphore zapalka) {
        this.id = id;
        this.ubijacz = ubijacz;
        this.zapalka = zapalka;
    }

    private void ubijanie() throws InterruptedException {
        System.out.println("Palacz " + id + " ubija fajkę");
        Thread.sleep((long) (Math.random() * 1000));
    }

    private void zapalanie() throws InterruptedException {
        System.out.println("Palacz " + id + " zapala fajkę");
        Thread.sleep((long) (Math.random() * 500));
    }

    private void palenie() throws InterruptedException {
        System.out.println("Palacz " + id + " pali fajkę");
        Thread.sleep((long) (Math.random() * 2000));
    }

    public void run(){
        try{
            while(true){
                System.out.println("Palacz " + id + " chce ubijacz");
                ubijacz.acquire();
                System.out.println("Palacz " + id + " bierze ubijacz");

                ubijanie();
                ubijacz.release();
                System.out.println("Palacz " + id + " zwraca ubijacz");

                System.out.println("Palacz " + id + " chce zapałki");
                zapalka.acquire();
                System.out.println("Palacz " + id + " bierze zapałki");
                zapalanie();
                zapalka.release();
                System.out.println("Palacz " + id + " oddaje zapałki");

                palenie();

            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        int k = 5; // palacze
        int l = 2; // ubijacze
        int m = 3; // zapałki

        Semaphore ubijacze = new Semaphore(l);
        Semaphore zapalki = new Semaphore(m);

        Palacz[] palacze = new Palacz[k];

        for(int i = 0; i < k; i++){
            palacze[i] = new Palacz(i + 1, ubijacze, zapalki);
            palacze[i].start();
        }
    }
}