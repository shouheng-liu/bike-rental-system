package uk.ac.ed.bikerental;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class Quote {

    private ArrayList<Bike> bikes;
    private boolean deliverable;
    private LocalDate bookingDate;
    private LocalDate returnDate;
    private Provider provider;
    private BigDecimal total;
    private BigDecimal deposit;

    public Quote(ArrayList<Bike> bikes, Provider provider, LocalDate start, LocalDate end) {
        this.bikes = bikes;
        this.provider = provider;
        this.bookingDate = start;
        this.returnDate = end;
        this.deposit = calculateDeposit(bikes);
        this.total = calculateTotal(bikes);
    }

    private BigDecimal calculateDeposit(ArrayList<Bike> bikes) {
        BigDecimal deposit = BigDecimal.ZERO;
        for (Bike bike : bikes) {
            BigDecimal depositAmount = this.provider.getValuationPolicy().calculateValue(bike,
                    this.bookingDate);
            deposit = deposit.add(depositAmount);
        }
        return deposit;
    }

    private BigDecimal calculateTotal(ArrayList<Bike> bikes) {
        BigDecimal total = this.deposit;
        DateRange dateRange = new DateRange(this.bookingDate, this.returnDate);
        BigDecimal price = this.provider.getPricingPolicy().calculatePrice(bikes, dateRange);
        total = total.add(price);
        return total;
    }

    public boolean isDeliverable(Location customerLocation, Location providerLocation) {
        return customerLocation.isNearTo(providerLocation);
    }





}
