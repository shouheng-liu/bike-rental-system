package uk.ac.ed.bikerental;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Customer {
    private Location location;
    private ArrayList<Booking> bookings = new ArrayList<>();


    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public Customer(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
