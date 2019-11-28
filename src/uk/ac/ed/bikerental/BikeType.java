package uk.ac.ed.bikerental;

import java.math.BigDecimal;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BikeType bikeType1 = (BikeType) o;
        return getBikeType() == bikeType1.getBikeType() &&
                getReplacementValue().equals(bikeType1.getReplacementValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBikeType(), getReplacementValue());
    }

    public BigDecimal getReplacementValue() {
        return this.replacementValue;
    }

    public BikeTypes getBikeType() {
        return this.bikeType;
    }


}