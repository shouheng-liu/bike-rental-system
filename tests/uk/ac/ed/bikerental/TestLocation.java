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
//    	this.location4 = new Location("NBA", "National Basketball Association");
    }
    
    // TODO: put some tests here
//    @Test
//    void testLocation() {
//    	Location.assertThrows(false, this.location4.Location(this.location4.getPostcode(), this.location4.getAddress()));
//    }
    
    @Test
    void testIsNearToTrue() {
        assertTrue(this.location1.isNearTo(location2));
    }

    @Test
    void testIsNearToFalse() {
        assertFalse(this.location1.isNearTo(location3));
    }
}
