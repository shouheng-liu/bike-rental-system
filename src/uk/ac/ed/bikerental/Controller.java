package uk.ac.ed.bikerental;

import java.util.HashMap;
import java.util.Collection;

public class Controller {

    private static final HashMap<BikeTypes, BikeType> ExistingBikeTypes = new HashMap<BikeTypes, BikeType>();
    private static final

    public static void addBikeType(BikeType type) {
        ExistingBikeTypes.putIfAbsent(type.getBikeType(), type);
    }

    public static BikeType getBikeType(BikeTypes type) {
        return ExistingBikeTypes.get(type);
    }
}
