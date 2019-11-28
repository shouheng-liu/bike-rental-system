package uk.ac.ed.bikerental;

import java.util.ArrayList;

public class Customer {
    private Location location;
    private ArrayList<Payment> orderInfos = new ArrayList<>();


    public Customer(Location location) {
        this.location = location;
    }

    public ArrayList<Payment> getOrderInfos() {
        return orderInfos;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
