class Arbiter {
    private int liczba = 0;
    private boolean pisze = false;

    public synchronized void pisz(int id, int liczba) throws InterruptedException {
        while(pisze){
            wait();
        }

        pisze = true;
        this.liczba = liczba;
        System.out.println("Pisarz " + id +" zapisał: " + this.liczba);
        pisze = false;
        notifyAll();
    }

    public synchronized void czytaj(int id) throws InterruptedException {
        while(pisze){
            wait();
        }

        System.out.println("Czytelnik" + id + " przeczytał: " + this.liczba);

    }
}

class Pisarz extends Thread {
    private final Arbiter arbiter;
    private final int id;
    private int x = 0;

    Pisarz(Arbiter arbiter, int id) {
        this.arbiter = arbiter;
        this.id = id;
    }

    public void run(){
        while (true){
            try {
                sleep(1000);
                arbiter.pisz(this.id, this.x++);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Czytelnik extends Thread {
    private final Arbiter arbiter;
    private final int id;


    Czytelnik(Arbiter arbiter, int id) {
        this.arbiter = arbiter;
        this.id = id;
    }

    public void run(){
        while(true){
            try {
                sleep(2000);
                arbiter.czytaj(this.id);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        int liczbaPisarzy = 3;
        int liczbaCzytelnikow = 3;

        Arbiter arbiter = new Arbiter();
        Pisarz[] pisarz = new Pisarz[liczbaPisarzy];
        Czytelnik[] czytelnik = new Czytelnik[liczbaCzytelnikow];

        for(int i = 0; i < liczbaPisarzy; i++){
            pisarz[i] = new Pisarz(arbiter, i);
            pisarz[i].start();
        }

        for(int i = 0; i < liczbaCzytelnikow; i++){
            czytelnik[i] = new Czytelnik(arbiter, i);
            czytelnik[i].start();
        }
    }
}