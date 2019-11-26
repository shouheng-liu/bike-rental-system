package uk.ac.ed.bikerental;

public enum BikeState {

    RENTED, INSHOP, RETURNED, PICKUP, DROPOFF;

    public static BikeState from(String BikeState) {
        switch(BikeState) {
            case "rented":
                return RENTED;
            case "inShop":
                return INSHOP;
            case "returned":
                return RETURNED;
            default:
                throw new RuntimeException("Unknown bike state.");
        }
    }
}
