package uk.ac.ed.bikerental;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class Quote {

    private ArrayList<Bike> bikes;
    private boolean deliverable;
    private LocalDate bookingDate;
    private LocalDate returnDate;
    private long daysHired;
    private Provider provider;
    private BigDecimal total;
    private BigDecimal deposit;

    public Quote(ArrayList<Bike> bikes, Provider provider) {
        this.bikes = bikes;
        this.provider = provider;
    }

    public boolean isDeliverable(Location customerLocation, Location providerLocation) {
        return customerLocation.isNearTo(providerLocation);
    }




}
