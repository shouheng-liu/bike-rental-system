package uk.ac.ed.bikerental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DoubleBalanceTest {

    BigDecimal depreciationRate;
    LocalDate[] dates = new LocalDate[3];
    Bike bike;
    BigDecimal replacementValue;
    BikeType bikeType = new BikeType("mountainBike", replacementValue);


    @BeforeEach
    void setUp() throws Exception {
        // Put setup here
        Location locationDummy = new Location("EH1 5LY", "Cowgate");
        Provider provider = new Provider(locationDummy, "Cheap bikes", BigDecimal.valueOf(0.2));
        this.bike = new Bike(BikeTypes.MOUNTAINBIKE, provider.getAddress(), provider.getName());
        this.replacementValue = BigDecimal.valueOf(900.0);
        this.depreciationRate = BigDecimal.valueOf(1.0/10.0);
        this.dates[0] = LocalDate.now();
        this.dates[1] = LocalDate.now().plusYears(3);
        this.dates[2] = LocalDate.now().plusDays(10);
    }

    /*
    We quickly check that the depreciation rate is a percentage value.
     */
    @Test
    public void testIfPercentage() {

        assertThrows(AssertionError.class, () ->
        {new DoubleBalanceDepreciation(BigDecimal.TEN); });
        assertThrows(AssertionError.class, () ->
        {new DoubleBalanceDepreciation(BigDecimal.valueOf(-10.0)); });
    }

    /*
    Under Double balance depreciation, the replacement value of the bike in the first year should
     be equal to its initial replacement value.
     */
    @Test
    public void testDepreciationIsOriginal() {
        ValuationPolicy valuationPolicy = new DoubleBalanceDepreciation(depreciationRate);
        assertEquals(valuationPolicy.calculateValue(bike, dates[0]).stripTrailingZeros(),
                bike.getReplacementValue().stripTrailingZeros());
        assertEquals(valuationPolicy.calculateValue(bike, dates[2]).stripTrailingZeros(),
                bike.getReplacementValue().stripTrailingZeros());
    }

    /*
    After three years, the value of a bike worth 900 pounds initially is 900*(1-2*0.1)^3 = 460.8
    pounds. Demonstrated in second test.
     */
    @Test
    public void testDepreciation() {
        ValuationPolicy valuationPolicy = new DoubleBalanceDepreciation(depreciationRate);
        assertEquals(BigDecimal.valueOf(460.8).stripTrailingZeros(),
                valuationPolicy.calculateValue(bike, dates[1]).stripTrailingZeros());
    }


}
