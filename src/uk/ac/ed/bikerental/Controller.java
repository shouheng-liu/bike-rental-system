package uk.ac.ed.bikerental;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;

public class Controller {

    private static final HashMap<BikeTypes, BikeType> ExistingBikeTypes = new HashMap<BikeTypes, BikeType>();
    private static final ArrayList<Quote> matchingQuotes = new ArrayList<>();
    private static int identifierCount;

    public static void addBikeType(BikeType type) {
        ExistingBikeTypes.putIfAbsent(type.getBikeType(), type);
    }

    public static BikeType getBikeType(BikeTypes type) {
        return ExistingBikeTypes.get(type);
    }

    public static void setIdentifierCount(int identifierCount) {
        Controller.identifierCount = identifierCount;
    }

    public static int getIdentifierCount() {
        return identifierCount;
    }
}
