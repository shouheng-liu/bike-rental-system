package uk.ac.ed.bikerental;

import java.time.LocalDate;

public class Customer {
    private Location location;

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
