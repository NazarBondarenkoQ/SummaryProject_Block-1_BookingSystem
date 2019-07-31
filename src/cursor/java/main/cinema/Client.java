package cursor.java.main.cinema;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

class Client {
    private String name;
    private String surName;
    private String email;
    private String status;
    private double money;
    private List<String> info = new ArrayList<>();

    Client(String name, String surName, String email, String status, int money) {
        this.name = name;
        this.surName = surName;
        this.email = email;
        this.status = status;
        this.money = money;
    }

    String getName() {
        return name;
    }

    String getEmail() {
        return email;
    }

    double getMoney() {
        return money;
    }

    void setMoney(double money) {
        this.money = money;
    }

    String getStatus() {
        return status;
    }

    void setInfo(String s) {
        info.add(s);
    }

    void printInfo() {
        for (String s : info) {
            System.out.println(s);
        }
    }
}
