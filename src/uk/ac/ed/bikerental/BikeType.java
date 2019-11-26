package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Objects;

public class BikeType {

    private BikeTypes bikeType;
    private BigDecimal replacementValue = BigDecimal.ZERO;

    public BikeType(String bikeType, BigDecimal replacementValue) {
        BikeTypes type = BikeTypes.from(bikeType);
        this.bikeType = type;
        this.replacementValue = replacementValue;
        Controller.addBikeType(this);
    }

    public BigDecimal getReplacementValue() {
        return this.replacementValue;
    }

    public BikeTypes getBikeType() {
        return this.bikeType;
    }


}