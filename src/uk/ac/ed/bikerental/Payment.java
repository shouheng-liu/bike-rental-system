package uk.ac.ed.bikerental;

import java.math.BigDecimal;

public class Payment {

    private int orderNumber;
    private BigDecimal deposit;
    private BigDecimal price;
    private BigDecimal total;
    private boolean depositReturned;

    public Payment(BigDecimal deposit, BigDecimal price, BigDecimal total) {
        this.deposit = deposit;
        this.price = price;
        this.total = total;
        this.depositReturned = false;
        this.orderNumber = BookingController.getOrderCount();
        BookingController.setOrderCount(this.orderNumber + 1);
    }

    public BigDecimal getTotal() {
        return total;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deposit == null) ? 0 : deposit.hashCode());
		result = prime * result + (depositReturned ? 1231 : 1237);
		result = prime * result + orderNumber;
		result = prime * result + ((price == null) ? 0 : price.hashCode());
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
		Payment other = (Payment) obj;
		if (deposit == null) {
			if (other.deposit != null)
				return false;
		} else if (!deposit.equals(other.deposit))
			return false;
		if (depositReturned != other.depositReturned)
			return false;
		if (orderNumber != other.orderNumber)
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		return true;
	}
    
}
