package cursor.java.main.cinema;

public class Main {
    public static void main(String[] args) {
        Client client = new Client("Jack","Sample","example@gmail.com","Student",100);
        Cinema cinema = new Cinema("Multiplex","Default Street",Rating.GOOD,1);
        Cinema.Booking booking = cinema.new Booking(client);
        cinema.addMovie();
        cinema.searchMovie();
        booking.bookPlace();
        client.printInfo();
        booking.refreshBookings();
        booking.printAllBookings();
    }
}
