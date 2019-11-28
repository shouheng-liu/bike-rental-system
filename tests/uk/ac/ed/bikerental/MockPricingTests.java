package uk.ac.ed.bikerental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for MockPricing class
 */
public class MockPricingTests {

    Provider provider;
    PricingPolicy pricingPolicy;
    ArrayList<Bike> bikes = new ArrayList<>();
    DateRange dateRange1, dateRange2, dateRange3;

    @BeforeEach
    void setUp() {

        this.provider = new Provider(new Location("EH1 1LY", "Cowgate"),"Deal dilly",
                BigDecimal.valueOf(0.2));
        this.provider.addBikes(BikeTypes.EBIKE, 3);
        this.provider.addBikes(BikeTypes.MOUNTAINBIKE, 5);
        this.bikes.addAll(this.provider.getAvailableBikesOfType(BikeTypes.EBIKE));
        this.bikes.addAll(this.provider.getAvailableBikesOfType(BikeTypes.MOUNTAINBIKE));
        dateRange1 = new DateRange(LocalDate.now(), LocalDate.now().plusDays(5));
        dateRange2 = new DateRange(LocalDate.now(), LocalDate.now().plusDays(10));
        dateRange3 = new DateRange(LocalDate.now(), LocalDate.now().plusDays(30));
        this.pricingPolicy = new MockPricing(this.provider);
    }
    @Test
    void pricing5Days() {
        assertEquals(BigDecimal.valueOf(76.00).setScale(2),
                this.pricingPolicy.calculatePrice(this.bikes,
                        dateRange1));
    }

    @Test
    void pricing10Days() {
        assertEquals(BigDecimal.valueOf(72.00).setScale(2),
                this.pricingPolicy.calculatePrice(this.bikes,
                        dateRange2));
    }

    @Test
    void pricing30Days() {
        assertEquals(BigDecimal.valueOf(68.00).setScale(2),
                this.pricingPolicy.calculatePrice(this.bikes,
                        dateRange3));
    }
}
