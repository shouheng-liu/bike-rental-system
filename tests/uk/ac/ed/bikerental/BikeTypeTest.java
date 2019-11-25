package uk.ac.ed.bikerental;


import groovy.json.JsonOutput;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class BikeTypeTest {
    private BigDecimal replacementValue = BigDecimal.valueOf(100.0);
    private BikeType bikeType = new BikeType("mountainBike", replacementValue);

    @Test
    public void testCreation() {
        BikeTypes type = BikeTypes.from(
                "mountainBike");
        BigDecimal storedReplacementValue = BikeType.getReplacementValue(type);
        assertEquals(storedReplacementValue.stripTrailingZeros(),
                replacementValue.stripTrailingZeros());
        System.out.println(BikeType.getExistingBikeTypes());

    }
    @Test
    public void testHashMap() {
        HashMap<BikeTypes, BikeType> ExistingBikeTypes = new HashMap<BikeTypes, BikeType>();
        ExistingBikeTypes.putIfAbsent(BikeTypes.from("mountainBike"), bikeType);
        System.out.println(ExistingBikeTypes);
        assertEquals(ExistingBikeTypes, BikeType.getExistingBikeTypes());
    }




}
