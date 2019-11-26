package uk.ac.ed.bikerental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DoubleBalanceTest {

    BigDecimal depreciationRate;
    LocalDate[] dates = new LocalDate[4];
    Bike bike;
    BigDecimal replacementValue = BigDecimal.valueOf(900.0);


    @BeforeEach
    void setUp() throws Exception {
        // Put setup here
        new BikeType("mountainBike", replacementValue);
        Location locationDummy = new Location("EH1 1LY", "Cowgate");
        bike = new Bike(BikeTypes.from("mountainBike"), locationDummy);
        depreciationRate = BigDecimal.valueOf(1.0/10.0);
        dates[0] = LocalDate.now();
        dates[1] = LocalDate.now().plusYears(3);
        dates[2] = LocalDate.now().plusDays(10);
        dates[3] = LocalDate.now().plusYears(11);
    }

    @Test
    public void testIfPercentage() {

        assertThrows(AssertionError.class, () ->
        {new DoubleBalanceDepreciation(BigDecimal.TEN); });
        assertThrows(AssertionError.class, () ->
        {new DoubleBalanceDepreciation(BigDecimal.valueOf(-10.0)); });
    }

    @Test
    public void testDepreciation() {
        ValuationPolicy doubleBalancePolicy = new DoubleBalanceDepreciation(depreciationRate);
        assertEquals(doubleBalancePolicy.calculateValue(bike, dates[0]).stripTrailingZeros(),
                bike.getReplacementValue().stripTrailingZeros());
        assertEquals(doubleBalancePolicy.calculateValue(bike, dates[1]).stripTrailingZeros(),
                BigDecimal.valueOf(460.8).stripTrailingZeros());
        assertEquals(doubleBalancePolicy.calculateValue(bike, dates[2]).stripTrailingZeros(),
                bike.getReplacementValue().stripTrailingZeros());
        assertThrows(RuntimeException.class, () ->
        {doubleBalancePolicy.calculateValue(bike, dates[3]); });



    }
}