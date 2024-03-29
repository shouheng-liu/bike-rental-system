package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

/**
 * Tests for Location class
 */
class TestLocation {
	private Location location1, location2, location3;
    @BeforeEach
    void setUp() throws Exception {
    	this.location1 = new Location("EH8 9TS", "Darroch Court, St John's Hill, Edinburgh");
    	this.location2 = new Location("EH1 1LY", "3 Robertson's Cl, Edinburgh");
    	this.location3 = new Location("SW1A 2AA", "10 Downing St, Westminster, London");
    }

    /*
    Test that a postcode needs at least six characters
     */
    @Test
    void testLocation() {

        assertThrows(AssertionError.class, () ->
        {
            new Location("NBA", "National Basketball Association");
        });
    }

    /*
    Test if we can extract first two characters
     */
    @Test
    void testFirstTwoChars() {
    	assertEquals("EH", Location.firstTwoChars(this.location1.getPostcode()));
    }


    @Test
    void testIsNearToTrue() {
        assertTrue(this.location1.isNearTo(location2));
    }

    @Test
    void testIsNearToFalse() {
        assertFalse(this.location1.isNearTo(location3));
    }
}
