package org.example.lesson2;


class Amount {
    static int amount ;


    public synchronized static void count() {
            for (int i = 0; i < 200; i++) {
                amount++;
            }
        System.out.println(amount);
    }

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

