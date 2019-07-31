package cursor.java.main.cinema;

import java.time.LocalDate;
import java.util.Date;

class Movie {
    private String name;
    private Status status;
    private double price;
    private String category;
    private Date dateTime;

    Movie(String name, String type, int price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
        dateTime = java.sql.Date.valueOf(LocalDate.now());
        status = Status.ACTIVE;
    }

    void info() {
        System.out.print("\"" + getName() + "\"" + " Price: " + getPrice()+"$, ");
    }

    String getName() {
        return name;
    }

    Date getDateTime() {
        return dateTime;
    }

    double getPrice() {
        return price;
    }

    String getCategory() {
        return category;
    }

    void setStatus(Status status) {
        this.status = status;
    }
}
