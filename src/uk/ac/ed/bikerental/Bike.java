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
    private BigDecimal price;

    public Bike(BikeTypes bikeType, Location returnLocation,
                BigDecimal price) {
        identifierCount++;
        this.identifier = identifierCount;
        this.bikeState = BikeState.from("inShop");
        this.bikeType = bikeType;
        this.returnLocation = returnLocation;
        this.price = price;

        this.manufacturingDate = LocalDate.now();
    }

    public BikeType getType() {
        // TODO: Implement Bike.getType
        return BikeType.getBikeType(this.bikeType);
    }

    public LocalDate getManufacturingDate() {
        return manufacturingDate;
    }
}