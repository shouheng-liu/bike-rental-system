package uk.ac.ed.bikerental;


import java.math.BigDecimal;
import java.time.LocalDate;

public class Bike {
    private static int identifierCount;
    private BikeState bikeState;
    private LocalDate manufacturingDate;
    private BikeTypes bikeType;
    private int identifier;
    private Location returnLocation;

    public Bike(BikeTypes bikeType, Location returnLocation) {

        if (Controller.getBikeType(bikeType) == null) {
            throw new RuntimeException("Bike type not registered. Register it first, then try " +
                    "again.");
        }
        this.bikeState = BikeState.from("inShop");
        this.bikeType = bikeType;
        this.returnLocation = returnLocation;
        this.manufacturingDate = LocalDate.now();
        identifierCount++;
        this.identifier = identifierCount;
    }

    public BikeType getType() {
        // TODO: Implement Bike.getType
        return Controller.getBikeType(this.bikeType);
    }

    public LocalDate getManufacturingDate() {
        return this.manufacturingDate;
    }

    public BigDecimal getReplacementValue() {
        return this.getType().getReplacementValue();
    }
}