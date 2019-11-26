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

    public Quote(ArrayList<Bike> bikes, Provider provider, LocalDate start, LocalDate end) {
        this.bikes = bikes;
        this.provider = provider;
        this.bookingDate = start;
        this.returnDate = end;
    }

    public boolean isDeliverable(Location customerLocation, Location providerLocation) {
        return customerLocation.isNearTo(providerLocation);
    }





}
