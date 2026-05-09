import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // P1 -> P4
        PipedOutputStream p1Out = new PipedOutputStream();
        PipedInputStream p4In1 = new PipedInputStream(p1Out);

        // P2 -> P4
        PipedOutputStream p2Out = new PipedOutputStream();
        PipedInputStream p4In2 = new PipedInputStream(p2Out);

        // P3 -> P4
        PipedOutputStream p3Out = new PipedOutputStream();
        PipedInputStream p4In3 = new PipedInputStream(p3Out);

        // P4 -> P5
        PipedOutputStream p4Out = new PipedOutputStream();
        PipedInputStream p5In = new PipedInputStream(p4Out);

        Random rand = new Random();
        Thread P1 = new Thread(new Runnable() {
            @Override
            public void run() {
                DataOutputStream out = new DataOutputStream(p1Out);

                while(true){
                    try {
                        int x = rand.nextInt(100);
                        System.out.println("P1: " + x);
                        out.writeInt(x);
                        out.flush();
                        Thread.sleep((rand.nextInt(3) + 1) * 1000);
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        Thread P2 = new Thread(new Runnable() {

            @Override
            public void run() {
                DataOutputStream out = new DataOutputStream(p2Out);

                while(true){
                    try {
                        int x = rand.nextInt(100);
                        System.out.println("P2: " + x);
                        out.writeInt(x);
                        out.flush();
                        Thread.sleep((rand.nextInt(3) + 1) * 1000);
                    } catch (InterruptedException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        Thread P3 = new Thread(new Runnable() {
            @Override
            public void run() {
                DataOutputStream out = new DataOutputStream(p3Out);
                while (true){
                    try {
                        int x = rand.nextInt(100);
                        System.out.println("P3: " + x);
                        out.writeInt(x);
                        out.flush();
                        Thread.sleep((rand.nextInt(3) + 1) * 1000);
                    } catch (InterruptedException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        Thread P4 = new Thread(new Runnable() {
            @Override
            public void run() {

                DataInputStream in1 = new DataInputStream(p4In1);
                DataInputStream in2 = new DataInputStream(p4In2);
                DataInputStream in3 = new DataInputStream(p4In3);

                DataOutputStream out = new DataOutputStream(p4Out);
                while(true){
                    try {
                        int a = in1.readInt();
                        int b = in2.readInt();
                        int c = in3.readInt();

                        int max = Math.max(a,Math.max(b,c));
                        System.out.println("P4: " + max);

                        out.writeInt(max);
                        out.flush();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        Thread P5 = new Thread(new Runnable() {
            @Override
            public void run() {
                DataInputStream in = new DataInputStream(p5In);
                List<Integer> list = new ArrayList<>();

                while(true){
                    try{
                        int x = in.readInt();
                        list.add(x);

                        if(list.size() == 10){
                            Collections.sort(list);

                            System.out.println("P5: " + list.toString());
                            list.clear();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        P1.start();
        P2.start();
        P3.start();
        P4.start();
        P5.start();
    }
}