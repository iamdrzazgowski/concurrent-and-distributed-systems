class Arbiter {
    private int liczba = 0;
    private int czytelnicy = 0;
    private boolean pisze = false;

    public synchronized void czytaj(int id) throws InterruptedException {
        while(pisze){
            wait();
        }

        czytelnicy++;
        System.out.println("Czytelnik: " + id + " przeczytał: " + this.liczba);
        czytelnicy--;
        notifyAll();
    }

    public synchronized void pisz(int id, int liczba) throws InterruptedException {
        while(pisze || czytelnicy > 0){
            wait();
        }

        pisze = true;
        this.liczba = liczba;
        System.out.println("Pisz: " + id + " liczba: " + this.liczba);
        pisze = false;
        notifyAll();
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
        while(true){
            try {
                sleep(2000);
                arbiter.pisz(id, x++);
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
                arbiter.czytaj(id);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        int liczbaPisarzy = 2;
        int liczbaCzytelnikow = 3;

        Arbiter arbiter = new Arbiter();
        Pisarz[] pisarze = new Pisarz[liczbaPisarzy];
        Czytelnik[] czytelnicy = new Czytelnik[liczbaCzytelnikow];

        for(int i = 0; i < liczbaPisarzy; i++){
            pisarze[i] = new Pisarz(arbiter, i);
            pisarze[i].start();
        }

        for(int i = 0; i < liczbaCzytelnikow; i++){
            czytelnicy[i] = new Czytelnik(arbiter, i);
            czytelnicy[i].start();
        }

    }
}