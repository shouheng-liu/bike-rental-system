package uk.ac.ed.bikerental;


import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class BikeTypeTest {

    @Test
    public void testCreation() {
        BigDecimal price = BigDecimal.valueOf(20.0);
        BigDecimal replacementValue = BigDecimal.valueOf(100.0);
        BikeType bikeType = new BikeType("mountainBike", replacementValue);
        System.out.println(BikeType.getReplacementValue(BikeTypes.from("mountainBike")));
    }
}
