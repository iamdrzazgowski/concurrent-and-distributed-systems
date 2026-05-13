import java.io.IOException;

class Buffor{
    private char[] buffer = new char[10];
    private int counter = 0;
    private int head = 0;
    private int tail = 0;

    public synchronized void putChar(char c) throws InterruptedException {
        while(counter == buffer.length){
            wait();
        }

        buffer[tail] = c;
        tail = (tail + 1) % buffer.length;
        counter++;

        notifyAll();
    }

    public synchronized char getChar() throws InterruptedException {
        while(counter == 0){
            wait();
        }

        char temp = buffer[head];
        head = (head + 1) % buffer.length;
        counter--;

        notifyAll();

        return temp;
    }
}

class Producer extends Thread {
    private final Buffor buffor;

    public Producer(Buffor buffor) {
        this.buffor = buffor;
    }

    public void run(){
        int prev = -1;

        try {


            while (true) {

                int current = System.in.read();

                if (current == -1) {
                    break;
                }

                char c = (char) current;

                if (prev == '*') {
                    if (c == '*') {
                        buffor.putChar('&');
                        prev = -1;
                    } else {
                        buffor.putChar('*');
                        prev = c;

                        if (prev != '*') {
                            buffor.putChar((char) prev);
                            prev = -1;
                        }
                    }
                } else {
                    if (c == '*') {
                        prev = '*';
                    } else {
                        buffor.putChar(c);
                    }
                }

            }

            if (prev == '*') {
                buffor.putChar('*');
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class Consumer extends Thread {
    private final Buffor buffor;

    public Consumer(Buffor buffor) {
        this.buffor = buffor;
    }

    public void run(){
        int counter = 0;

        while(true){
            try {
                char c = buffor.getChar();
                System.out.print(c);
                counter++;

                if(counter == 80){
                    System.out.println();
                    counter = 0;
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Buffor buffor = new Buffor();

        Consumer consumer = new Consumer(buffor);
        Producer producer = new Producer(buffor);

        producer.start();
        consumer.start();
    }
}