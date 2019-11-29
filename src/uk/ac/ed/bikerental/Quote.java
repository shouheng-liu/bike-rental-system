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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bikes == null) ? 0 : bikes.hashCode());
		result = prime * result + ((bookingDate == null) ? 0 : bookingDate.hashCode());
		result = prime * result + (deliverable ? 1231 : 1237);
		result = prime * result + ((deposit == null) ? 0 : deposit.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((provider == null) ? 0 : provider.hashCode());
		result = prime * result + ((returnDate == null) ? 0 : returnDate.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Quote other = (Quote) obj;
		if (bikes == null) {
			if (other.bikes != null)
				return false;
		} else if (!bikes.equals(other.bikes))
			return false;
		if (bookingDate == null) {
			if (other.bookingDate != null)
				return false;
		} else if (!bookingDate.equals(other.bookingDate))
			return false;
		if (deliverable != other.deliverable)
			return false;
		if (deposit == null) {
			if (other.deposit != null)
				return false;
		} else if (!deposit.equals(other.deposit))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider))
			return false;
		if (returnDate == null) {
			if (other.returnDate != null)
				return false;
		} else if (!returnDate.equals(other.returnDate))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		return true;
	}

	/**
     * Calculate the deposit amount for bikes in an array
     */
    private BigDecimal calculateDeposit(ArrayList<Bike> bikes) {
        BigDecimal deposit = BigDecimal.ZERO;
        for (Bike bike : bikes) {
            BigDecimal depositAmount = this.provider.getValuationPolicy().calculateValue(bike,
                    this.bookingDate);
            depositAmount = depositAmount.multiply(this.provider.getDepositRate());
            deposit = deposit.add(depositAmount);
        }
        return deposit.setScale(2, RoundingMode.CEILING);
    }
    
    /**
     * Calculate the rent amount for bikes in an array
     */
    private BigDecimal calculatePrice(ArrayList<Bike> bikes) {
        DateRange dateRange = new DateRange(this.bookingDate, this.returnDate);
        BigDecimal price = this.provider.getPricingPolicy().calculatePrice(bikes, dateRange);
        return price.setScale(2, RoundingMode.CEILING);
    }
    
    /**
     * Calculate the total price which includes the deposit amount and the rent amount
     */
    private BigDecimal calculateTotal() {
        BigDecimal total = this.deposit;
        total = total.add(this.price);
        return total.setScale(2, RoundingMode.CEILING);
    }
    
    /**
     * Checks if customer and provider are in the same postal area
     */
    public boolean isDeliverable(Location customerLocation, Location providerLocation) {
        return customerLocation.isNearTo(providerLocation);
    }


}
