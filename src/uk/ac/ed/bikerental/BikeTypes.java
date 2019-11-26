package uk.ac.ed.bikerental;

public enum BikeTypes {

    ROADBIKE, MOUNTAINBIKE, EBIKE, HYBRIDBIKE;

    public static BikeTypes from(String bikeType) {
        switch(bikeType) {
            case "roadBike":
                return ROADBIKE;
            case "mountainBike":
                return MOUNTAINBIKE;
            case "eBike":
                return EBIKE;
            case "hybridBike":
                return HYBRIDBIKE;
            default:
                throw new RuntimeException("Unknown bike type.");
        }
    }
}
