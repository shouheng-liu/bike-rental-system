package uk.ac.ed.bikerental;

import java.util.Objects;

/**
 * Inherits from Quote class
 */
public class Booking extends Quote {

    private boolean addressDelivery;
    private BookingStates bookingStatus;
    private Payment paymentInfo;
    private Customer customer;

    public Booking(Quote quote, Customer customer) {
        super();
        this.bikes = quote.bikes;
        this.provider = quote.provider;
        this.customer = customer;
        this.addressDelivery = quote.deliverable;
        this.bookingDate = quote.bookingDate;
        this.returnDate = quote.returnDate;
        this.deposit = quote.deposit;
        this.price = quote.price;
        this.total = quote.total;
        this.bookingStatus = BookingStates.LEASEONGOING;
        this.paymentInfo = new Payment(this.deposit, this.price, this.total);

        this.provider.lendBikes(this.bikes);

    }



    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (addressDelivery ? 1231 : 1237);
		result = prime * result + ((bookingStatus == null) ? 0 : bookingStatus.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((paymentInfo == null) ? 0 : paymentInfo.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Booking other = (Booking) obj;
		if (addressDelivery != other.addressDelivery)
			return false;
		if (bookingStatus != other.bookingStatus)
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (paymentInfo == null) {
			if (other.paymentInfo != null)
				return false;
		} else if (!paymentInfo.equals(other.paymentInfo))
			return false;
		return true;
	}



	public boolean getAddressDelivery() {
        return this.addressDelivery;
    }


    public void setBookingStatus(BookingStates bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Payment getPaymentInfo() {
        return paymentInfo;
    }
}
