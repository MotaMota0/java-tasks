package org.example.threadLessons;

import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Waiter extends Thread {
    private BlockingQueue<Order> orders;
    private Random random;
    public Waiter(BlockingQueue<Order> orders) {
        this.orders = orders;
        this.random = new Random();

    }
    @Override
    public void run() {

        System.out.println("Order is starts!!!");
        try {
            int id = 1;
            while (!Thread.currentThread().isInterrupted()) {
                String[] foods = {"Lazaniya", "Manty", "Plov"};
                String food = foods[random.nextInt(foods.length)];
                int time = random.nextInt(5000) + 1000;

                Order order = new Order(id++, food, time);
                orders.put(order);

                System.out.println("Order: " + order + " add !");
                Thread.sleep(2000);


            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupted();
            System.out.println("Waiters finished work");
        }



    }
}
