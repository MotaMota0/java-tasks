package org.example.threadLessons;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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
Запустить несколько официантов и несколько поваров одновременно.*/
public class threadLesson {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Order> order = new LinkedBlockingQueue<>(3);

        Waiter waiter1 = new Waiter(order);
        Waiter waiter2 = new Waiter(order);
        Waiter waiter3 = new Waiter(order);
        Chef chef1= new Chef(order);
        Chef chef2 = new Chef(order);

        waiter1.start();
        waiter2.start();
        waiter3.start();

        chef1.start();
        chef2.start();


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }

        waiter1.interrupt();
        waiter2.interrupt();
        waiter3.interrupt();
        chef1.interrupt();
        chef2.interrupt();

        waiter1.join();
        waiter2.join();
        waiter3.join();

        chef1.join();
        chef2.join();



        System.out.println("Restaurant  closed.");

    }


}
