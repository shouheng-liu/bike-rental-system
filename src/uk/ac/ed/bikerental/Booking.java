package uk.ac.ed.bikerental;

import java.util.Objects;

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
        this.deposit = quote.calculateDeposit(this.bikes);
        this.price = quote.calculatePrice(this.bikes);
        this.total = quote.calculateTotal();
        this.bookingStatus = BookingStates.LEASEONGOING;
        this.paymentInfo = new Payment(this.deposit, this.price, this.total);

        this.provider.lendBikes(this.bikes);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return getAddressDelivery() == booking.getAddressDelivery() &&
                bookingStatus == booking.bookingStatus &&
                getPaymentInfo().equals(booking.getPaymentInfo()) &&
                customer.equals(booking.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAddressDelivery(), bookingStatus, getPaymentInfo(), customer);
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
