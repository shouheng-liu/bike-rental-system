package uk.ac.ed.bikerental;

import java.awt.print.Book;
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

    public static ArrayList<Provider> getProviders() {
        return providers;
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

    public static void addProvider(Provider provider) {
        providers.add(provider);
    }

    public static void removeProvider(Provider provider) {
        providers.remove(provider);
    }

    public static ArrayList<Quote> getQuotes(HashMap<BikeTypes, Integer> desiredBikes,
                                      DateRange dateRange, Location location, boolean delivery) {
        ArrayList<Quote> quotes = new ArrayList<>();
        for (Provider provider : providers) {
            Quote quote;
            if (location.isNearTo(provider.getAddress()) || !delivery)  {
                ArrayList<Bike> bikesForQuote;
                boolean flag = true;
                for (BikeTypes type : desiredBikes.keySet()) {
                    if (provider.getAvailableBikesOfType(type).size() < desiredBikes.get(type)) {
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

    public static Payment bookQuote(Quote quote, Customer customer) {
        Booking booking = new Booking(quote, customer);
        if (booking.getAddressDelivery()) {
            DeliveryService deliveryService = DeliveryServiceFactory.getDeliveryService();

            for (Deliverable bike : booking.getBikes()) {
                deliveryService.scheduleDelivery( bike, booking.provider.getAddress(),
                        customer.getLocation(), booking.bookingDate);

            }
        }
        return booking.getPaymentInfo();
    }
}
