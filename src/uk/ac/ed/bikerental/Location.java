package uk.ac.ed.bikerental;

/**
 * Date: Nov 28-2019
 * Represents the location of a provider or a customer.
 * 
 * @author shouheng
 * @version 1.2
 * @since 1.0
 *
 */
public class Location {
    /**
     * String value for the postcode at the location.
     */
    private String postcode;
    /**
     * String value for the address at the location.
     */
    private String address;
    
    /**
     * Creates a location with specified postcode and address and sets them.
     * 
     * @param postcode	The postcode at the location.
     * @param address	The address at the location.
     * @throws Exception if the length of String postcode is less than 6.
     */
    public Location(String postcode, String address) {
        assert postcode.length() >= 6;
        this.postcode = postcode;
        this.address = address;
    }
    
    /**
     * Takes in a String and return only the first two chars.
     * 
     * @param input	A string.
     * @return A String of length 2 representing the first two chars of the input String.
     */
    public static String firstTwoChars(String input) {
        String firstTwoChars = "";
        if (input.length() > 2) {
            firstTwoChars = input.substring(0, 2);
        } else {
            firstTwoChars = input;
        }
        return (firstTwoChars);
    }
    
    /**
     * Checks whether the location is in the same postal area as another location,
     * other, to allow for collection/delivery of bikes.
     * 
     * @param other The location that's being compared against.
     * @return A Boolean of whether first two chars of the postcode at the two location are the same
     */
    public boolean isNearTo(Location other) {
        // TODO: Implement Location.isNearTo
        String input1 = this.postcode;
        String input2 = other.postcode;
        
        return (firstTwoChars(input1)).equals(firstTwoChars(input2));
    }
    

    /**
     * Gets the location's postcode.
     * 
     * @return A String representing the location's postcode.
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * Gets the location's address.
     * 
     * @return A String representing the location's address.
     */
    public String getAddress() {
        return address;
    }
    
    // You can add your own methods here
}
