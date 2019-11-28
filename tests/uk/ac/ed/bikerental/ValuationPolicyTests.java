package uk.ac.ed.bikerental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ValuationPolicyTests {
    // You can add attributes here
	private LocalDate date1, date2;
	private Bike bike1, bike2;

    @BeforeEach
    void setUp() throws Exception {
        // Put setup here
    	
    	/*
    	this.date1 = LocalDate.of(2019, 1, 7);
    	this.date2 = LocalDate.of(2019, 1, 8);
    	
    	Location locationDummy = new Location("EH1 1LY", "Cowgate");
        Provider provider = new Provider(locationDummy, "Good bikes", BigDecimal.valueOf(0.2));
        BikeType type = new BikeType("mountainBike", BigDecimal.valueOf(900.0));
    	
    	this.bike1 = new Bike(BikeTypes.MOUNTAINBIKE, this.provider.getAddress(), this.provider.getName());
    	*/
    			
    }
    
    // TODO: Write tests for valuation policies
    @Test
    void testCalculateValue() {
    	/*
    	assertEquals(this.bike1.getType().getReplacementValue(), CalculateValue(this.bike1, this.date1));
    	*/    	
    }
}
