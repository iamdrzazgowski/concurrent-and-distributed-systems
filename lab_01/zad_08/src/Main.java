import java.util.concurrent.atomic.AtomicInteger;

class Licznik {
    private AtomicInteger licznik = new AtomicInteger(0);

    public void inc(){
        licznik.incrementAndGet();
    }

    public void dec(){
        licznik.decrementAndGet();
    }

    public int get(){
        return licznik.get();
    }
}

class MyThred extends Thread{
    private Licznik licznik;
    private boolean increase;

    MyThred(Licznik licznik, boolean increase){
        this.licznik = licznik;
        this.increase = increase;
    }

    public void run(){
        if(increase){
            for(int i = 0; i < 10000; i++){
                licznik.inc();
            }
        }else{
            for(int j = 0; j < 5000; j++){
                licznik.dec();
            }
        }
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Licznik licznik = new Licznik();

        MyThred t1 = new MyThred(licznik, true);
        MyThred t2 = new MyThred(licznik, false);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Wartość końcowa licznika: " + licznik.get());
    }
}