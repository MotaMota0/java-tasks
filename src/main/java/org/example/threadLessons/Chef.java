package org.example.threadLessons;

import java.util.concurrent.BlockingQueue;

/*Задание 1: Симуляция работы ресторана
Описание:
Вы разрабатываете систему управления рестораном, в которой несколько официантов принимают заказы и передают их на кухню, где повара готовят еду.

Требования:
Создать класс Order, представляющий заказ, содержащий:

ID заказа.
Название блюда.
Время приготовления (в миллисекундах).
Создать класс Waiter (официант), который:

Генерирует случайные заказы и передает их на кухню.
Работает в отдельном потоке.
Использует BlockingQueue<Order> для передачи заказов на кухню.
Создать класс Chef (повар), который:

Получает заказы из BlockingQueue<Order>.
Готовит заказ (эмулируется Thread.sleep(время_приготовления)).
Выводит в консоль сообщение о завершении приготовления.
Работает в отдельном потоке.
Запустить несколько официантов и несколько повароводновременно.*/
public class Chef extends Thread {

    private BlockingQueue<Order> orders;

    public Chef(BlockingQueue<Order> orders) {
        this.orders = orders;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {


                    Order order = orders.take();
                    System.out.println("Order: " + order.getName_food() + " will be cooked");

                    Thread.sleep(order.getTime_to_cook());

                    System.out.println("Order: " + order.getName_food() + " was ready");

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupted();
            System.out.println("Chef finished cook");
        }
        System.out.println();
    }


}
