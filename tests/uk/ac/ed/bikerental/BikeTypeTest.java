package uk.ac.ed.bikerental;


import groovy.json.JsonOutput;
import org.junit.jupiter.api.*;
import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class BikeTypeTest {
    private BigDecimal replacementValue = BigDecimal.valueOf(900.0);
    private BikeType bikeType = new BikeType("mountainBike", replacementValue);

    @Test
    public void testCreation() {
        BikeTypes type = BikeTypes.MOUNTAINBIKE;
        BigDecimal storedReplacementValue = bikeType.getReplacementValue();
        assertEquals(replacementValue.stripTrailingZeros(),
                storedReplacementValue.stripTrailingZeros());
    }


    @Test
    public void testSpelling() {
        assertThrows(RuntimeException.class, () ->
        {new BikeType("MOUTAGNAD", replacementValue); });
    }


    @Test
    public void testControllerHashmap() {
        BikeTypes type = BikeTypes.from(
                "mountainBike");
        BikeType sameBikeType = new BikeType("mountainBike", BigDecimal.valueOf(1200.0));
        BikeType hashmapBikeType = Controller.getBikeType(type);
        //Check that first registered bike type object is final one for that bike type
        assertFalse(hashmapBikeType.equals(sameBikeType));

        //check if replacement value for each bikeType can be different
        new BikeType("eBike", BigDecimal.valueOf(1230.0));
        hashmapBikeType = Controller.getBikeType(BikeTypes.EBIKE);
        assertNotEquals(hashmapBikeType.getReplacementValue(), bikeType.getReplacementValue());

    }

}
