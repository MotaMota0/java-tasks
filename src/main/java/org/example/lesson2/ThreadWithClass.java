package org.example.lesson2;


import java.util.Objects;
import java.util.concurrent.locks.Lock;

class Amount {
    static int amount ;

    private static final Object Lock1 = new Object();
    private static final Object Lock2 = new Object();
    public static void count() {
        synchronized (Lock1){
            for (int i = 0; i < 200; i++) {
                amount++;
            }
        System.out.println(amount);
    }}

}

public class ThreadWithClass extends Thread {





    @Override
    public  void run() {
        Amount.count();
    }

}


class ThreadWithClass2 extends Thread {



    @Override
    public void run() {
        Amount.count();
    }
}

