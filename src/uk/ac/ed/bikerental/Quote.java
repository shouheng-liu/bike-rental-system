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
    public BigDecimal total;
    public BigDecimal price;
    public BigDecimal deposit;

    public Quote(ArrayList<Bike> bikes, Provider provider, LocalDate start, LocalDate end) {
        this.bikes = bikes;
        this.provider = provider;
        this.bookingDate = start;
        this.returnDate = end;
        this.deposit = this.calculateDeposit(this.bikes);
        this.price = this.calculatePrice(this.bikes);
        this.total = this.calculateTotal();
    }

    private BigDecimal calculateDeposit(ArrayList<Bike> bikes) {
        BigDecimal deposit = BigDecimal.ZERO;
        for (Bike bike : bikes) {
            BigDecimal depositAmount = this.provider.getValuationPolicy().calculateValue(bike,
                    this.bookingDate);
            depositAmount = depositAmount.multiply(this.provider.getDepositRate());
            deposit = deposit.add(depositAmount);
        }
        return deposit;
    }

    private BigDecimal calculatePrice(ArrayList<Bike> bikes) {
        DateRange dateRange = new DateRange(this.bookingDate, this.returnDate);
        BigDecimal price = this.provider.getPricingPolicy().calculatePrice(bikes, dateRange);
        return price;
    }

    private BigDecimal calculateTotal() {
        BigDecimal total = this.deposit;
        total = total.add(this.price);
        return total;
    }

    public boolean isDeliverable(Location customerLocation, Location providerLocation) {
        return customerLocation.isNearTo(providerLocation);
    }





}
