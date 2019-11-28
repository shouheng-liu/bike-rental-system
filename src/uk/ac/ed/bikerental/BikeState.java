package uk.ac.ed.bikerental;

/**
 * Enumeration of possible bike states
 * Throws an exception if a bike state that's not included is called
 */
public enum BikeState {

    RENTED, INSHOP, RETURNED;

    public static BikeState from(String BikeState) {
        switch (BikeState) {
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
