package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for BasicPricing class
 */
public class PricingPolicyTests {
    // You can add attributes here
	
    Provider provider;
    PricingPolicy pricingPolicy;
    ArrayList<Bike> bikes = new ArrayList<>();
    private DateRange dateRange1, dateRange2, dateRange3;

    @BeforeEach
    void setUp() throws Exception {
        // Put setup here
        
        this.provider = new Provider(new Location("EH1 1LY", "Cowgate"),"Deal dilly",
                BigDecimal.valueOf(0.2));
        this.pricingPolicy = new BasicPricing(this.provider);
        
        this.pricingPolicy.setDailyRentalPrice(Controller.getBikeType(BikeTypes.EBIKE), BigDecimal.valueOf(10.00));
        //this.pricingPolicy.setDailyRentalPrice(Controller.getBikeType(BikeTypes.MOUNTAINBIKE), BigDecimal.valueOf(15.00));
        //this.provider.setRentalPrice(BikeTypes.EBIKE, BigDecimal.valueOf(0.00));
        //this.provider.setRentalPrice(BikeTypes.MOUNTAINBIKE, BigDecimal.valueOf(0.00));
        this.provider.addBikes(BikeTypes.EBIKE, 3);
        //this.provider.addBikes(BikeTypes.MOUNTAINBIKE, 5);
        this.bikes.addAll(this.provider.getAvailableBikesOfType(BikeTypes.EBIKE));
        //this.bikes.addAll(this.provider.getAvailableBikesOfType(BikeTypes.MOUNTAINBIKE));
        dateRange1 = new DateRange(LocalDate.now(), LocalDate.now().plusDays(5));
        dateRange2 = new DateRange(LocalDate.now(), LocalDate.now().plusDays(5));
        dateRange3 = new DateRange(LocalDate.now(), LocalDate.now().plusDays(5));
        

    }

    // TODO: Write tests for pricing policies
    @Test
    void testCalculatePrice() {
    	assertEquals(BigDecimal.valueOf(30.00),
                this.pricingPolicy.calculatePrice(this.bikes,
                        dateRange1));
    }

    @Test
    void pricing10Days() {
        assertEquals(BigDecimal.valueOf(30.00).setScale(2),
                this.pricingPolicy.calculatePrice(this.bikes,
                        dateRange2));
    }

    @Test
    void pricing30Days() {
        assertEquals(BigDecimal.valueOf(40.00),
                this.pricingPolicy.calculatePrice(this.bikes,
                        dateRange3));
    }

//    @Test
//    void testCalculatePrice() {
//
//        assertEquals(this.price1,
//        		this.pricingPolicy.calculatePrice(this.bike2, this.date1).stripTrailingZeros());
//
//        assertEquals(this.price2,
//        		this.pricingPolicy.calculatePrice(this.bike2, this.date2).stripTrailingZeros());
//
//    }
}
