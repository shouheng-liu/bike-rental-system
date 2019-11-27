package uk.ac.ed.bikerental;

import java.util.ArrayList;

public class BookingController {
    private static final ArrayList<Booking> currentBookings = new ArrayList<>();
    static int orderCount;

    public static Payment bookQuote(Quote quote, Customer customer) {
        Booking booking = new Booking(quote, customer);
        if (booking.getAddressDelivery()) {
            DeliveryService deliveryService = DeliveryServiceFactory.getDeliveryService();

            for (Deliverable bike : booking.getBikes()) {
                deliveryService.scheduleDelivery( bike, booking.provider.getAddress(),
                        customer.getLocation(), booking.bookingDate);

            }
        }
        currentBookings.add(booking);
        return booking.getPaymentInfo();
    }

    public static Booking getBooking(int orderNumber) {
        for (Booking booking : currentBookings) {
            if (booking.getPaymentInfo().getOrderNumber() == orderNumber) {
                return booking;
            }
        }
        return null;
    }

    public static int getOrderCount() {
        return orderCount;
    }

    public static void setOrderCount(int orderCount) {
        BookingController.orderCount = orderCount;
    }
}
