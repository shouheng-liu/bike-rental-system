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
        assertThrows(RuntimeException.class, () -> //check that bikeType is in enumeration
        {new BikeType("MOUTAGNAD", replacementValue); });
    }

    @Test
    public void testControllerHashmap() {
        BikeTypes type = BikeTypes.from(
                "mountainBike");
        new BikeType("mountainBike", BigDecimal.valueOf(1200.0));
        BikeType hashmapBikeType = Controller.getBikeType(type); //Check that first registered
        // bike type value is the final one
        assertEquals(hashmapBikeType.getReplacementValue(), bikeType.getReplacementValue());
        BikeTypes type2 = BikeTypes.from("eBike");
        new BikeType("eBike", BigDecimal.valueOf(1230.0)); //check if replacement value for each
        // bikeType can be different
        hashmapBikeType = Controller.getBikeType(type2);
        assertNotEquals(hashmapBikeType.getReplacementValue(), bikeType.getReplacementValue());

    }

}
