package org.example;
/*Создайте класс User с параметрами:
        - int id;
        - String login;
        - String password;
        Геттеры и сеттеры
        Перегрузите метод String toString(), которая возвращает все поля объекта*/
public class User {
    private int id;
    private String login;
    private String password;

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User with " + "id=" + id + " { login='" + login  + ", password='" + password  + '}';
    }
}
