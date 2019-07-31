package cursor.java.main.cinema;

class Place {
    private String type;
    private int number;

    Place(String type, int number) {
        this.type = type;
        this.number = number;
    }

    String getType() {
        return type;
    }

    int getNumber() {
        return number;
    }
}
