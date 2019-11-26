package uk.ac.ed.bikerental;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;

public class Controller {

    private static final HashMap<BikeTypes, BikeType> ExistingBikeTypes = new HashMap<BikeTypes, BikeType>();
    private static final ArrayList<Quote> matchingQuotes = new ArrayList<>();
    private static final ArrayList<Provider> providers = new ArrayList<>();
    private static int identifierCount;
    private static int orderCount;

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

    public static int getOrderCount() {
        return orderCount;
    }

    public static void setOrderCount(int orderCount) {
        Controller.orderCount = orderCount;
    }

    public void addProvider(Provider provider) {
        providers.add(provider);
    }

    public void removeProvider(Provider provider) {
        providers.remove(provider);
    }

    public ArrayList<Quote> getQuotes(HashMap<BikeTypes, Integer> desiredBikes,
                                      DateRange dateRange, Location location) {
        ArrayList<Quote> quotes = new ArrayList<>();
        for (Provider provider : providers) {
            Quote quote;
            if (location.isNearTo(provider.getAddress())) {
                ArrayList<Bike> bikesForQuote;
                boolean flag = true;
                for (BikeTypes type : desiredBikes.keySet()) {
                    if (provider.getAvailableBikesOfType(type).size() >= desiredBikes.get(type)) {
                        continue;
                    }
                    else {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    bikesForQuote = provider.getBikes(desiredBikes);
                    quotes.add(new Quote(bikesForQuote, provider, dateRange.getStart(),
                            dateRange.getEnd()));
                }
            }
        }
        return quotes;
    }
}
