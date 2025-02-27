package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.stream.Collectors;

/*Задание 1
        Создайте класс User с параметрами:
        - int id;
        - String login;
        - String password;
        Геттеры и сеттеры
        Перегрузите метод String toString(), которая возвращает все поля объекта
        В основном классе Main.java, создайте два метода:
        List <Users> getUsersList();
        void saveUsersList(List<Users> users);
        Метод getUsersList возвращает динамический массив пользователей, которых мы загружаем из
        определенного файла memory.txt
        Метод saveUsersList сохраняет динамический массив пользователей наш файл memory.txt
        Используя эти методы, создайте мини приложение с интерфейсом:
        PRESS [1] TO ADD USERS
        PRESS [2] TO LIST USERS
        PRESS [3] TO DELETE USERS
        PRESS [4] TO EXIT*/
public class Main {


    public static List<User> getUsersList() {


        List<User> usersList = null;
        try (BufferedReader bfreader = new BufferedReader(new FileReader("src/main/resources/memory.txt"))) {


            String info = null;
            usersList = new ArrayList<>();
            while ((info = bfreader.readLine()) != null) {

                String[] oneUSer = info.split(";");

                for (String i : oneUSer) {
                    String[] detail = i.split(",");

                    int id = Integer.parseInt(detail[0].trim());
                    String login = detail[1];
                    String passw = detail[2];
                    usersList.add(new User(id, login, passw));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public static void saveUsersList(List<User> users) {

        try (BufferedWriter bfWriter = new BufferedWriter(new FileWriter("src/main/resources/memory.txt", true))) {
            for (User user : users) {
                bfWriter.write(user.getId() + "," + user.getLogin() + "," + user.getPassword() + ";");
                bfWriter.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUsers(int id) {


        List<User> allUsers = getUsersList();
        boolean isHave = allUsers.removeIf(user -> user.getId() == id);

        if (isHave) {
            try (BufferedWriter bfWriter = new BufferedWriter(new FileWriter("src/main/resources/memory.txt"))) {
                for (User user : allUsers) {
                    bfWriter.write(user.getId() + "," + user.getLogin() + "," + user.getPassword() + ";");
                    bfWriter.newLine();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("User deleted");
        } else System.out.println("User with id:" + id + " not found!");

    }


    public static void main(String[] args) {
        System.out.println(
                "PRESS [1] TO ADD USERS\n" +
                        "PRESS [2] TO LIST USERS\n" +
                        "PRESS [3] TO DELETE USERS\n" +
                        "PRESS [4] TO EXIT*");
        Scanner sc = new Scanner(System.in);
        int input =0;
        while (sc.hasNext()) {
            input = sc.nextInt();
            sc.nextLine();
            if (input == 1) {
                System.out.println("Enter a inform: ");
                String input2 = sc.nextLine().trim();
                String[] details = input2.split(",");
                List<User> users = new ArrayList<>();
                int id = Integer.parseInt(details[0].trim());
                String login = details[1];
                String passw = details[2];
                users.add(new User(id, login, passw));
                saveUsersList(users);
                System.out.println("Save successful");
            } else if (input == 2) {
                System.out.println("Get all users list");
                System.out.println(getUsersList());
            } else if (input == 3) {

                System.out.println("Enter id which will delete: ");
                int identificate = sc.nextInt();
                sc.nextLine();
                deleteUsers(identificate);
            } else if (input == 4) {
                System.out.println("exit");
                return;

            } else System.out.println("Enter a wrong number");
        }
        System.out.println("Exiiiit");

    }

}