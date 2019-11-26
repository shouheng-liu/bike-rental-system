package uk.ac.ed.bikerental;


import java.time.LocalDate;
import java.util.ArrayList;

public class Quote {

    private ArrayList<Bike> bikes = new ArrayList<>();
    private boolean deliverable;
    private LocalDate bookingDate;
    private LocalDate returnDate;
    private long daysHired;
    private Provider provider;
    
}
