class Arbiter {
    private int liczba = 0;
    private int czytelnicy = 0;

    public synchronized void pisz(int id, int liczba) throws InterruptedException {
        while(czytelnicy > 0){
            wait();
        }

        this.liczba = liczba;
        System.out.println("Pisarz zapisał: " + this.liczba);

        notifyAll();
    }

    public synchronized void czytaj(int id){
        czytelnicy++;
        System.out.println("Czytelnik " + id + " przeczytał: " + this.liczba);
        czytelnicy--;
        notifyAll();
    }
}

class Pisarz extends Thread{
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
                Thread.sleep(10);
                arbiter.pisz(id, x++);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Czytelnik extends Thread{
    private final Arbiter arbiter;
    private final int id;


    Czytelnik(Arbiter arbiter, int id) {
        this.arbiter = arbiter;
        this.id = id;
    }

    public void run(){
        while (true){
            try {
                Thread.sleep(10);
                arbiter.czytaj(id);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        int liczbaPisarz = 2;
        int liczbaCzytelnikow = 12;

        Arbiter arbiter = new Arbiter();
        Pisarz[] pisarze = new Pisarz[liczbaPisarz];
        Czytelnik[] czytelnicy = new Czytelnik[liczbaCzytelnikow];

        for(int i = 0; i < liczbaCzytelnikow; i++){
            czytelnicy[i] = new Czytelnik(arbiter, i);
            czytelnicy[i].start();
        }

        for(int i = 0; i < liczbaPisarz; i++){
            pisarze[i] = new Pisarz(arbiter, i);
            pisarze[i].start();
        }
    }
}