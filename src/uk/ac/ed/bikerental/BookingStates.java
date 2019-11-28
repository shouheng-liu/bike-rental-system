package uk.ac.ed.bikerental;

public enum BookingStates {

    LEASEOVER, LEASEONGOING, RESERVED, ONDELIVERY, RETURNDELIVERY;

    public static BookingStates from(String bookingState) {
        switch (bookingState) {
            case "leaseOver":
                return LEASEOVER;
            case "leaseOngoing":
                return LEASEONGOING;
            case "reserved":
                return RESERVED;
            default:
                throw new RuntimeException("Unknown booking state.");
        }
    }
}
