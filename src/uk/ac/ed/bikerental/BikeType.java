package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Objects;

public class BikeType {

    private static final HashMap<String, BikeType> ExistingBikeTypes = new HashMap<String, BikeType>();
    private String bikeType = "";
    private BigDecimal price = BigDecimal.ZERO;
    private BigDecimal replacementValue = BigDecimal.ZERO;

    public BikeType(String type, BigDecimal p, BigDecimal replacement) {
        bikeType = type;
        price = p;
        replacementValue = replacement;
        ExistingBikeTypes.put(type, this);
    }

    public BigDecimal getReplacementValue() {
        return this.replacementValue;
    }

    public static BigDecimal getReplacementValue(String name) {
        return getBikeType(name).getReplacementValue();
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public static BigDecimal getPrice(String name) {
        return getBikeType(name).getPrice();
    }

    public String getBikeType() {
        return bikeType;
    }

    public static HashMap<String, BikeType> getExistingBikeTypes() {
        return ExistingBikeTypes;
    }

    public static BikeType getBikeType(String name) {
        return ExistingBikeTypes.get(name);
    }
}