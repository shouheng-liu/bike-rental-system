package uk.ac.ed.bikerental;


import java.math.BigDecimal;
import java.time.LocalDate;

public class Bike {
    private static int identifierCount;
    private BikeState bikeState;
    private LocalDate manufacturingDate;
    private BikeTypes bikeType;
    private int condition;
    private int identifier;

    public Bike(BikeTypes bikeType, int condition, Location returnLocation,
                BigDecimal price, BigDecimal replacementValue,
                BigDecimal deposit) {
        identifierCount++;
        this.identifier = identifierCount;
        this.condition = condition;
        this.BikeState = BikeState.from("inShop");
        this.bikeType = bikeType;
        this.manufacturingDate = LocalDate.now();
    }

    public BikeType getType() {
        // TODO: Implement Bike.getType
        return BikeType.getBikeType(this.bikeType);
    }
}