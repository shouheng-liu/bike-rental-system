package uk.ac.ed.bikerental;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Quote {

    public ArrayList<Bike> bikes;
    public boolean deliverable;
    public LocalDate bookingDate;
    public LocalDate returnDate;
    public Provider provider;
    public BigDecimal total;
    public BigDecimal price;
    public BigDecimal deposit;

    public Quote() {

    }

    public Quote(ArrayList<Bike> bikes, Provider provider, boolean deliverable, LocalDate start,
                 LocalDate end) {
        this.bikes = bikes;
        this.provider = provider;
        this.deliverable = deliverable;
        this.bookingDate = start;
        this.returnDate = end;
        this.deposit = this.calculateDeposit(this.bikes);
        this.price = this.calculatePrice(this.bikes);
        this.total = this.calculateTotal();
    }

    public ArrayList<Bike> getBikes() {
        return this.bikes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quote)) return false;
        Quote quote = (Quote) o;
        return deliverable == quote.deliverable &&
                Objects.equals(getBikes(), quote.getBikes()) &&
                bookingDate.equals(quote.bookingDate) &&
                returnDate.equals(quote.returnDate) &&
                provider.equals(quote.provider) &&
                total.equals(quote.total) &&
                price.equals(quote.price) &&
                deposit.equals(quote.deposit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBikes(), deliverable, bookingDate, returnDate, provider, total,
                price, deposit);
    }

    public BigDecimal calculateDeposit(ArrayList<Bike> bikes) {
        BigDecimal deposit = BigDecimal.ZERO;
        for (Bike bike : bikes) {
            BigDecimal depositAmount = this.provider.getValuationPolicy().calculateValue(bike,
                    this.bookingDate);
            depositAmount = depositAmount.multiply(this.provider.getDepositRate());
            deposit = deposit.add(depositAmount);
        }
        return deposit.setScale(2, RoundingMode.CEILING);
    }

    public BigDecimal calculatePrice(ArrayList<Bike> bikes) {
        DateRange dateRange = new DateRange(this.bookingDate, this.returnDate);
        BigDecimal price = this.provider.getPricingPolicy().calculatePrice(bikes, dateRange);
        return price.setScale(2, RoundingMode.CEILING);
    }

    public BigDecimal calculateTotal() {
        BigDecimal total = this.deposit;
        total = total.add(this.price);
        return total.setScale(2, RoundingMode.CEILING);
    }

    public boolean isDeliverable(Location customerLocation, Location providerLocation) {
        return customerLocation.isNearTo(providerLocation);
    }


}
