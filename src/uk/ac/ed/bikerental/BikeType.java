package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Objects;

public class BikeType {

    private static final HashMap<BikeTypes, BikeType> ExistingBikeTypes = new HashMap<BikeTypes, BikeType>();
    private BikeTypes bikeType;
    private BigDecimal replacementValue = BigDecimal.ZERO;

    public BikeType(String bikeType, BigDecimal replacementValue) {
        BikeTypes type = BikeTypes.from(bikeType);
        this.bikeType = type;
        this.replacementValue = replacementValue;
        ExistingBikeTypes.put(type, this);
    }

    public BigDecimal getReplacementValue() {
        return this.replacementValue;
    }

    public static BigDecimal getReplacementValue(BikeTypes bikeType) {
        return getBikeType(bikeType).getReplacementValue();
    }

    public BikeTypes getBikeType() {
        return this.bikeType;
    }

    public static HashMap<BikeTypes, BikeType> getExistingBikeTypes() {
        return ExistingBikeTypes;
    }

    public static BikeType getBikeType(BikeTypes bikeType) {
        return ExistingBikeTypes.get(bikeType);
    }
}