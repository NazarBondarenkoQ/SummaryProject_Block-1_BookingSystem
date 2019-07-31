package cursor.java.main.cinema;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


class Cinema {
    private String name;
    private String address;
    private Rating rating;
    private Hall[] halls;
    private List<Movie> movies;
    private Scanner scanner = new Scanner(System.in);

    Cinema(String name, String address, Rating rating, int numberOfHalls) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        halls = new Hall[numberOfHalls];
        movies = new ArrayList<>(numberOfHalls);
        initializeHalls();
        printCinema();
    }

    void addMovie() {
        System.out.println("Please enter the name of the movie: ");
        String name = scanner.next();
        System.out.println("Please enter the type of the movie: ");
        String type = scanner.next();
        System.out.println("Please enter the price of the movie: ");
        int price = scanner.nextInt();
        System.out.println("Please enter the category of the movie: ");
        String category = scanner.next();
        movies.add(new Movie(name, type, price, category));
    }

    private void moviesList() {
        System.out.println("Available movies: ");
        for (Movie m : movies) {
            System.out.println((movies.indexOf(m) + 1) + ") Name: " + "\"" + m.getName() + "\" " +
                    "Price: " + m.getPrice() + "$.");
        }
    }

    private void initializeHalls() {
        for (int i = 0; i < halls.length; i++) {
            System.out.println("Please set up the hall # " + (i + 1));
            halls[i] = new Hall((i + 1));
        }
    }

    private void printCinema() {
        System.out.println("Cinema: \"" + name + "\"" + " Address: \"" + address + "\" Rating:\" " + rating + "\"");
        for (int i = 0; i < halls.length; i++) {
            System.out.println("======= HALL " + (i + 1) + " =======");
            halls[i].printHall();
            System.out.println();
        }
    }

    void searchMovie() {
        int choice;
        do {
            System.out.println("Please select the search criteria: 1 - Price; 2 - Category");
            choice = scanner.nextInt();
        } while (choice > 2 || choice < 1);
        switch (choice) {
            case 1:
                System.out.println("Please enter the price: ");
                int price = scanner.nextInt();
                if (price > 0) {
                    searchByPrice(price);
                } else {
                    System.out.println("Please enter the correct price.");
                }
                break;
            case 2:
                System.out.println("Please enter the category: ");
                String category = scanner.next();
                searchByCategory(category);
                break;
        }
    }

    private void searchByPrice(int price) {
        boolean found = movies.stream().anyMatch(x -> x.getPrice() == price);
        for (Movie i : movies) {
            if (i.getPrice() <= price) {
                i.info();
            }
        }
        if (!found) {
            System.out.println("No such movies available.");
        }
    }

    private void searchByCategory(String category) {
        boolean found = movies.stream().anyMatch(x -> x.getCategory().equalsIgnoreCase(category));
        for (Movie i : movies) {
            if (i.getCategory().equals(category)) {
                i.info();
            }
            if (!found) {
                System.out.println("No such movies available.");
            }
        }
    }

    class Booking {
        private Client client;
        private ArrayList<String> bookingInfo = new ArrayList<>();
        private Map<Date, ArrayList<String>> bookingList = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Booking(Client client) {
            this.client = client;
        }

        void bookPlace() {
            System.out.println("Please select the movie from the list: ");
            moviesList();
            int temp = 0;
            boolean repeat = true;
            Date reserveDate = null;
            do {
                if (scanner.hasNextInt()) {
                    temp = scanner.nextInt();
                }
            } while (temp < 0 || temp > movies.size());
            if (client.getMoney() >= movies.get(temp - 1).getPrice()) {
                try {
                    System.out.print("Please enter the date in such format dd/MM/yy: ");
                    String date = reader.readLine();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
                    reserveDate = dateFormat.parse(date);
                    if (reserveDate.before(movies.get(temp - 1).getDateTime())) {
                        System.out.println("Please enter the correct date. Given day is in the past.");
                        System.exit(1);
                    }
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                    System.out.println("Please use correct format: dd/MM/yy");
                    System.exit(-1);
                }
                while (repeat) {
                    if ((client.getMoney() - movies.get(temp - 1).getPrice()) >= 0) {
                        System.out.println("Please select the place: ");
                        printCinema();
                        System.out.print("Please select the raw: ");
                        int raw = 0;
                        do {
                            if (scanner.hasNextInt()) {
                                raw = scanner.nextInt();
                            }
                        } while (raw < 0 || raw > halls[0].rawCount() + 1);
                        int place = 0;
                        System.out.print("Please select the place: ");
                        do {
                            if (scanner.hasNextInt()) {
                                place = scanner.nextInt();
                            }
                        } while (place < 0 || place > halls[0].rawLength());
                        if (halls[0].getPlaces()[raw - 1][place - 1] != null) {
                            bookingInfo.add(client.getName() + " " + client.getEmail() + " " +
                                    "has booked " + halls[0].getPlaces()[raw - 1][place - 1].getType()
                                    + " place");
                            bookingList.put(reserveDate, bookingInfo);
                            LocalDate afterPremier = reserveDate
                                    .toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                                    .plusDays(6);
                            Date sale = java.sql.Date.valueOf(afterPremier);
                            if (reserveDate.after(sale)) {
                                double reducedPrice = movies.get(temp - 1).getPrice() - (movies.get(temp - 1)
                                        .getPrice() / 100d) * 15d;
                                client.setMoney(client.getMoney() - reducedPrice);
                                client.setInfo(client.getName() + " " + client.getEmail() + " " +
                                        "has booked " + halls[0].getPlaces()[raw - 1][place - 1].getType()
                                        + " place. Date of booking: " + reserveDate.toString() +
                                        " Price of the ticket: " + reducedPrice);
                                halls[0].setHall(raw, place);
                                halls[0].printHall();
                            } else if (client.getStatus().equalsIgnoreCase("Student")) {
                                double reducedPrice = movies.get(temp - 1).getPrice() - (movies.get(temp - 1)
                                        .getPrice() / 100d) * 10d;
                                client.setMoney(client.getMoney() - reducedPrice);
                                client.setInfo(client.getName() + " " + client.getEmail() + " " +
                                        "has booked " + halls[0].getPlaces()[raw - 1][place - 1].getType()
                                        + " place. Date of booking: " + reserveDate.toString() +
                                        " Price of the ticket: " + reducedPrice);
                                halls[0].setHall(raw, place);
                                halls[0].printHall();
                            } else {
                                client.setMoney(client.getMoney() - movies.get(temp - 1).getPrice());
                                client.setInfo(client.getName() + " " + client.getEmail() + " " +
                                        "has booked " + halls[0].getPlaces()[raw - 1][place - 1].getType()
                                        + " place. Date of booking: " + reserveDate.toString() +
                                        " Price of the ticket: " +
                                        movies.get(temp - 1).getPrice());
                                halls[0].setHall(raw, place);
                                halls[0].printHall();
                            }
                            System.out.println("Do you want to book more places? 1--Yes, 2--No");
                            int choice = 0;
                            try {
                                String s = reader.readLine();
                                choice = Integer.parseInt(s);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (choice == 2) {
                                repeat = false;
                            } else {
                                halls[0].printHall();
                            }
                        } else {
                            System.out.println("Impossible to book that place. Please select another one");
                        }
                    } else {
                        System.out.println("Not enough money.");
                    }
                }
            }
        }

        void refreshBookings() {
            halls[0].setHall();
        }

        void printAllBookings(){
            bookingList.entrySet().clear();
            System.out.println(bookingList.entrySet());
        }
    }
}


