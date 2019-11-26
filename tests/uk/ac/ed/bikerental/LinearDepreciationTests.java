package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import javax.sound.sampled.Line;
import java.math.BigDecimal;
import java.time.LocalDate;

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
        Location locationDummy = new Location("EH1 1LY", "Cowgate");
        bike = new Bike(BikeTypes.from("mountainBike"), locationDummy);
        depreciationRate = BigDecimal.valueOf(1.0/10.0);
        dates[0] = LocalDate.now();
        dates[1] = LocalDate.now().plusYears(3);
        dates[2] = LocalDate.now().plusDays(10);
        dates[3] = LocalDate.now().plusYears(11);
    }
    
    // TODO: Write tests for valuation policies

    @Test
    public void testIfPercentage() {

        assertThrows(AssertionError.class, () ->
        {new LinearDepreciation(BigDecimal.TEN); });
        assertThrows(AssertionError.class, () ->
        {new LinearDepreciation(BigDecimal.valueOf(-10.0)); });
    }

    @Test
    public void testDepreciation() {
        ValuationPolicy linearPolicy = new LinearDepreciation(depreciationRate);
        assertEquals(linearPolicy.calculateValue(bike, dates[0]).stripTrailingZeros(),
                bike.getReplacementValue().stripTrailingZeros());
        assertEquals(linearPolicy.calculateValue(bike, dates[1]).stripTrailingZeros(),
                BigDecimal.valueOf(630.0).stripTrailingZeros());
        assertEquals(linearPolicy.calculateValue(bike, dates[2]).stripTrailingZeros(),
                bike.getReplacementValue().stripTrailingZeros());
        assertThrows(RuntimeException.class, () ->
        {linearPolicy.calculateValue(bike, dates[3]); });

    }
}
