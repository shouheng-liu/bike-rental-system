package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TestLocation {
	private Location location1, location2, location3;
    @BeforeEach
    void setUp() throws Exception {
        // TODO: setup some resources before each test
    	this.location1 = new Location("EH8 9TS", "Darroch Court, St John's Hill, Edinburgh");
    	this.location2 = new Location("EH1 1LY", "3 Robertson's Cl, Edinburgh");
    	this.location3 = new Location("SW1A 2AA", "10 Downing St, Westminster, London");
    }
    
    // TODO: put some tests here
    @Test
    void testIsNearToTrue() {
        // TODO: check we can see when two date ranges overlap
    	assertEquals(true, this.location1.isNearTo(location2));
    }

    @Test
    void testIsNearToFalse() {
        // TODO: check we can see when two date ranges  don't overlap
    	assertEquals(false, this.location1.isNearTo(location3));
    }
}
