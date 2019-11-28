package uk.ac.ed.bikerental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LinearDepreciationTests {
    // You can add attributes here
    BigDecimal depreciationRate;
    LocalDate[] dates = new LocalDate[4];
    Bike bike;
    BigDecimal replacementValue = BigDecimal.valueOf(900.0);

    @BeforeEach
    void setUp() throws Exception {
        // Put setup here
        new BikeType("mountainBike", replacementValue);
        Location locationDummy = new Location("EH1 9LY", "London");
        Provider provider = new Provider(locationDummy, "Cheap bikes", BigDecimal.valueOf(0.2));
        bike = new Bike(BikeTypes.MOUNTAINBIKE, provider.getAddress(), provider.getName());
        depreciationRate = BigDecimal.valueOf(1.0 / 10.0);
        dates[0] = LocalDate.now();
        dates[1] = LocalDate.now().plusYears(3);
        dates[2] = LocalDate.now().plusDays(10);
        dates[3] = LocalDate.now().plusYears(11);
    }


    /*
    We quickly check that the depreciation rate is a percentage value.
     */
    @Test
    public void testIfPercentage() {

        assertThrows(AssertionError.class, () ->
        {
            new LinearDepreciation(BigDecimal.TEN);
        });
        assertThrows(AssertionError.class, () ->
        {
            new LinearDepreciation(BigDecimal.valueOf(-10.0));
        });
    }

    /*
    Under linear depreciation, the replacement value of the bike in the first year should
     be equal to its initial replacement value. Demonstrated in first and third test.

     After three years, the value of a bike worth 900 pounds initially is 900 - 3*0.1*900 = 630
      pounds. Demonstrated in second test.

     Under linear depreciation, we can have negative replacement values when more than ten years
     have past. Therefore, our system throws an exception if the replacement value is negative.
     Last test.
     */
    @Test
    public void testDepreciation() {
        ValuationPolicy linearPolicy = new LinearDepreciation(depreciationRate);
        assertEquals(linearPolicy.calculateValue(bike, dates[0]).stripTrailingZeros(),
                bike.getReplacementValue().stripTrailingZeros());
        assertEquals(BigDecimal.valueOf(630.0).stripTrailingZeros(),
                linearPolicy.calculateValue(bike, dates[1]).stripTrailingZeros());
        assertEquals(linearPolicy.calculateValue(bike, dates[2]).stripTrailingZeros(),
                bike.getReplacementValue().stripTrailingZeros());
        assertThrows(RuntimeException.class, () ->
        {
            linearPolicy.calculateValue(bike, dates[3]);
        });

    }
}
