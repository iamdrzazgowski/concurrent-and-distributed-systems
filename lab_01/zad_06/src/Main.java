class MyThread extends Thread {
    private int number;

    MyThread(int number) {
        this.number = number;
    }

    public void run(){
        System.out.println(number);
    }
}

class InfiniteThread extends Thread {
    private int number;

    InfiniteThread(int number) {
        this.number = number;
    }

    public void run(){
        while(true){
            System.out.println(number);
        }
    }
}

class FiniteThread extends Thread {
    private int number;

    FiniteThread(int number) {
        this.number = number;
    }

    public void run(){
        for(int i = 0; i < 6; i++){
            System.out.println(number);
        }
    }
}

public class Main {
    public static void main(String[] args) {
//        MyThread t1 = new MyThread(1);
//        MyThread t2 = new MyThread(2);

//        InfiniteThread t1 = new InfiniteThread(1);
//        InfiniteThread t2 = new InfiniteThread(2);

        FiniteThread t1 = new FiniteThread(1);
        FiniteThread t2 = new FiniteThread(2);

        t1.start();
        t2.start();
    }
}