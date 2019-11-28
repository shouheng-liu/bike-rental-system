package uk.ac.ed.bikerental;

import java.util.ArrayList;
import java.util.HashMap;

public class Controller {

    private static final HashMap<BikeTypes, BikeType> ExistingBikeTypes = new HashMap<BikeTypes,
            BikeType>();
    private static final ArrayList<Provider> providers = new ArrayList<>();
    private static int identifierCount;

    public static void addBikeType(BikeType type) {
        ExistingBikeTypes.putIfAbsent(type.getBikeType(), type);
    }

    public static BikeType getBikeType(BikeTypes type) {
        return ExistingBikeTypes.get(type);
    }

    public static ArrayList<Provider> getProviders() {
        return providers;
    }

    public static int getIdentifierCount() {
        return identifierCount;
    }

    public static void setIdentifierCount(int identifierCount) {
        Controller.identifierCount = identifierCount;
    }

    public static void addProvider(Provider provider) {
        providers.add(provider);
    }

    public static void removeProvider(Provider provider) {
        providers.remove(provider);
    }

    /**
     * Return an array of all matching quotes for bookings on a given date range specified by the customer
     */
    public static ArrayList<Quote> getQuotes(HashMap<BikeTypes, Integer> desiredBikes,
                                             DateRange dateRange, Location location,
                                             boolean delivery) {
        ArrayList<Quote> quotes = new ArrayList<Quote>();
        for (Provider provider : providers) {
            Quote quote;
            if (location.isNearTo(provider.getAddress()) || !delivery) {
                ArrayList<Bike> bikesForQuote;
                boolean flag = true;
                for (BikeTypes type : desiredBikes.keySet()) {
                    if (provider.getAvailableBikesOfType(type) == null) {
                        flag = false;
                        break;
                    }
                    int noAvailableBikes = provider.getAvailableBikesOfType(type).size();
                    if (noAvailableBikes < desiredBikes.get(type)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    bikesForQuote = provider.getBikes(desiredBikes);
                    quotes.add(new Quote(bikesForQuote, provider, delivery, dateRange.getStart(),
                            dateRange.getEnd()));
                }
            }
        }
        return quotes;
    }
}
