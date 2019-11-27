package uk.ac.ed.bikerental;

public class Location {
    private String postcode;
    private String address;
    
    public Location(String postcode, String address) {
        assert postcode.length() >= 6;
        this.postcode = postcode;
        this.address = address;
    }
    
    public boolean isNearTo(Location other) {
        // TODO: Implement Location.isNearTo
        String input1 = this.postcode;
        String input2 = other.postcode;
        //String firstTwoChars1 = "";
        //String firstTwoChars2 = "";


        if ((firstTwoChars(input1)).equals(firstTwoChars(input2))) {
            return true;
        }
        else {
            return false; 
        }
        //assert false;
        //return true;
    }
    
    public static String firstTwoChars(String input) {
        String firstTwoChars = "";
        if (input.length() > 2) 
        {
            firstTwoChars = input.substring(0, 2);
        } 
        else
        {
            firstTwoChars = input;
        }
        return (firstTwoChars);
    }

    public String getPostcode() {
        return postcode;
    }

    public String getAddress() {
        return address;
    }
    
    // You can add your own methods here
}
