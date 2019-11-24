package uk.ac.ed.bikerental;


import java.time.LocalDate;

public class Bike {
    private BikeState bikeState;
    private LocalDate manufacturingDate;
    private BikeTypes bikeType;

    public Bike(BikeTypes bikeType) {

        this.bikeType = bikeType;
        this.manufacturingDate = LocalDate.now();
    }

    public BikeType getType() {
        // TODO: Implement Bike.getType
        return BikeType.getBikeType(this.bikeType);
    }
}