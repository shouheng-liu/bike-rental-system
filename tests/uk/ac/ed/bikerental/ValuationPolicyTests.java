package uk.ac.ed.bikerental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for BasicValuation class
 */
public class ValuationPolicyTests {
    // You can add attributes here
    private LocalDate date1, date2;
    private Bike bike1, bike2;
    private BigDecimal replacementValue1, replacementValue2;
    private ValuationPolicy valuationPolicy;

    @BeforeEach
    void setUp() throws Exception {
        // Put setup here
    	

    	this.date1 = LocalDate.of(2019, 1, 7);
    	this.date2 = LocalDate.of(2023, 1, 8);
    	
    	Location locationDummy = new Location("EH1 1LY", "Cowgate");
        BikeType type1 = new BikeType("mountainBike", BigDecimal.valueOf(900.0));
        this.replacementValue1 = type1.getReplacementValue().stripTrailingZeros();
        BikeType type2 = new BikeType("eBike", BigDecimal.valueOf(1230));
        this.replacementValue2 = type2.getReplacementValue().stripTrailingZeros();
    	
    	this.bike1 = new Bike(BikeTypes.MOUNTAINBIKE, locationDummy, "Sample name");
        this.bike2 = new Bike(BikeTypes.EBIKE, locationDummy, "Sample name");

        this.valuationPolicy = new BasicValuation();


    }


    @Test
    void testCalculateValueFirst() {

        assertEquals(this.replacementValue1,
                this.valuationPolicy.calculateValue(this.bike1, this.date1).stripTrailingZeros());

        assertEquals(this.replacementValue1,
                this.valuationPolicy.calculateValue(this.bike1, this.date2).stripTrailingZeros());

    }

    @Test
    void testCalculateValueSecond() {

        assertEquals(this.replacementValue2,
                this.valuationPolicy.calculateValue(this.bike2, this.date1).stripTrailingZeros());

        assertEquals(this.replacementValue2,
                this.valuationPolicy.calculateValue(this.bike2, this.date2).stripTrailingZeros());

    }
}
