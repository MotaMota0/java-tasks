package org.example.lesson2;

public class MainThread {


    public static void main(String[] args) {


        Amount amount1 = new Amount();
        ThreadWithClass thread1 = new ThreadWithClass();
        //ThreadWithClass thread3 = new ThreadWithClass();
        ThreadWithClass2 thread2 = new ThreadWithClass2();


        thread1.start();
        thread2.start();
        //thread3.start();
    }
}
