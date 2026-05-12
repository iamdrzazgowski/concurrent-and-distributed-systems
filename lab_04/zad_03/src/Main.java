class Magazyn {
    private final static int K = 10;
    private int a = 0;
    private int b = 0;
    private int c = 0;

    public synchronized void dodajProduktA(String id) throws InterruptedException {
        while(a + b + c >= K){
            wait();
        }
        a++;
        System.out.println(id + " -> dodano A | A=" + a + " B=" + b + " C=" + c);
        notifyAll();
    }

    public synchronized void dodajProduktB(String id) throws InterruptedException {
        while(a + b + c >= K){
            wait();
        }
        b++;
        System.out.println(id + " -> dodano B | A=" + a + " B=" + b + " C=" + c);
        notifyAll();
    }

    public synchronized void dodajProduktC(String id) throws InterruptedException {
        while(a + b + c >= K){
            wait();
        }
        c++;
        System.out.println(id + " -> dodano C | A=" + a + " B=" + b + " C=" + c);
        notifyAll();
    }

    public synchronized void montuj() throws InterruptedException {
        while(a == 0 || b == 0 || c == 0){
            wait();
        }

        a--;
        b--;
        c--;

        System.out.println("M -> zmontowano produkt | A=" + a + " B=" + b + " C=" + c);

        notifyAll();
    }
}

class ProduktA extends Thread {
    private final Magazyn magazyn;
    private final int id;

    public ProduktA(Magazyn magazyn, int id) {
        this.magazyn = magazyn;
        this.id = id;
    }

    public void run(){
        try{
            int i = 0;

            while(true){
                Thread.sleep((long)(Math.random()*1000));
                magazyn.dodajProduktA("A" + id + i);
                i++;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
class ProduktB extends Thread {
    private final Magazyn magazyn;
    private final int id;

    public ProduktB(Magazyn magazyn, int id) {
        this.magazyn = magazyn;
        this.id = id;
    }

    public void run(){
        try{
            int i = 0;
            while(true){
                Thread.sleep((long)(Math.random()*1000));
                magazyn.dodajProduktB("B" + id + i);
                i++;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
class ProduktC extends Thread {
    private final Magazyn magazyn;
    private final int id;

    public ProduktC(Magazyn magazyn, int id) {
        this.magazyn = magazyn;
        this.id = id;
    }

    public void run(){
        try{
            int i = 0;
            while(true){
                Thread.sleep((long)(Math.random()*1000));
                magazyn.dodajProduktC("C" + id + i);
                i++;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Monter extends Thread {
    private final Magazyn magazyn;
    private final int id;

    public Monter(Magazyn magazyn, int id) {
        this.magazyn = magazyn;
        this.id = id;
    }

    public void run(){
        try{
            while(true){
                magazyn.montuj();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Magazyn magazyn = new Magazyn();

        ProduktA[] produktA = new ProduktA[3];
        ProduktB[] produktB = new ProduktB[4];
        ProduktC[] produktC = new ProduktC[2];
        Monter[] monter = new Monter[2];

        for(int i = 0; i < 3; i++){
            produktA[i] = new ProduktA(magazyn, i);
            produktA[i].start();
        }

        for(int i = 0; i < 4; i++){
            produktB[i] = new ProduktB(magazyn, i);
            produktB[i].start();
        }

        for(int i = 0; i < 2; i++){
            produktC[i] = new ProduktC(magazyn, i);
            produktC[i].start();
        }

        for(int i = 0; i < 2; i++){
            monter[i] = new Monter(magazyn, i);
            monter[i].start();
        }
    }
}