package org.example.threadLessons;

import java.util.Random;

public class Order {

    private int id;
    private String name_food;
    private long time_to_cook;

    public Order(int id, String name_food, long time_to_cook) {

        this.id = id;
        this.name_food = name_food;
        this.time_to_cook = time_to_cook;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_food() {
        return name_food;
    }

    public void setName_food(String name_food) {
        this.name_food = name_food;
    }

    public long getTime_to_cook() {
        return time_to_cook;
    }

    public void setTime_to_cook(long time_to_cook) {
        this.time_to_cook = time_to_cook;
    }

    @Override
    public String toString() {
        return "food " + name_food  ;
    }
}
