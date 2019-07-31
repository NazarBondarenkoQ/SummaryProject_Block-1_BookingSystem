package cursor.java.main.cinema;

import java.util.Scanner;

class Hall {
    private int number;
    private Place[][] places;

    Hall(int number) {
        System.out.println("Please enter the number of raws: ");
        Scanner scanner = new Scanner(System.in);
        int raw = scanner.nextInt();
        System.out.println("Please enter the number of seats: ");
        int place = scanner.nextInt();
        places = new Place[raw][place];
        setHall();
    }

    void setHall() {
        for (int i = 0; i < places.length; i++) {
            for (int j = 0; j < places[i].length; j++) {
                places[i][j] = new Place("Basic", j + 1);
                if (i == places.length - 2) {
                    places[i][j] = new Place("Premium", j + 1);
                }
                if (i == places.length - 1) {
                    places[i][j] = new Place("VIP", j + 1);
                }
            }
        }
    }

    int rawCount(){
        return places.length;
    }

    int rawLength(){
        return places[0].length;
    }

    void setHall(int raw, int place) {
        for (int i = 0; i < places.length; i++) {
            for (int j = 0; j < places[i].length; j++) {
                if (i == (raw - 1) && j == (place - 1)) {
                    places[i][j] = null;
                }
            }
        }
    }

    void printHall() {
        for (int i = 0; i < places.length; i++) {
            for (int j = 0; j < places[i].length; j++) {
                if (places[i][j] != null) {
                    System.out.print("[ " + places[i][j].getNumber() + " " + places[i][j].getType() + " " + "]");
                }
                else {
                    System.out.print("[ Reserved ]");
                }
            }
            System.out.println(" Raw: " + (i + 1));
        }
    }

    Place[][] getPlaces() {
        return places;
    }

}

