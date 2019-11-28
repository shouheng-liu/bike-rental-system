package uk.ac.ed.bikerental;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReturnBikesTest {

    LocalDate bookingDate;
    Location closeLocation, farAwayLocation;
    Customer customer, customer2, customer3;
    Provider provider, provider2;
    DateRange dateRange;
    ArrayList<Quote> quotes;
    HashMap<BikeTypes, Integer> desiredBikes;

    @BeforeEach
    void setUp() {
        this.closeLocation = new Location("EH1 1LY", "Cowgate");
        this.farAwayLocation = new Location("TW6 1EW", "Inner Ring E");
        this.customer = new Customer(closeLocation);
        this.customer2 = new Customer(closeLocation);
        this.customer3 = new Customer(farAwayLocation);
        this.provider = new Provider(closeLocation, "Dummy", BigDecimal.valueOf(0.2));
        ValuationPolicy valuationPolicy = new LinearDepreciation(BigDecimal.valueOf(0.1));
        this.provider2 = new Provider(this.closeLocation, "Best deals in Edinbraaa",
                BigDecimal.valueOf(0.2), valuationPolicy);
        BikeType bikeType = new BikeType("mountainBike", BigDecimal.valueOf(900));
        new BikeType("eBike", BigDecimal.valueOf(1230.0));
        this.provider.addBikes(BikeTypes.EBIKE, 3);
        for (int i = 0; i < 3; i++) {
            System.out.println(this.provider.getAvailableBikesOfType(BikeTypes.EBIKE).get(i).getIdentifier());
        }
        this.provider.addBikes(BikeTypes.MOUNTAINBIKE, 8);
        this.provider2.addBikes(BikeTypes.MOUNTAINBIKE, 10);
        this.provider2.getPricingPolicy().setDailyRentalPrice(bikeType,
                BigDecimal.valueOf(20));
        this.provider2.addBikes(BikeTypes.EBIKE, 5);
        valuationPolicy = new DoubleBalanceDepreciation(BigDecimal.valueOf(0.1));
        this.provider.setValuationPolicy(valuationPolicy);
        this.desiredBikes = new HashMap<>();
        this.desiredBikes.put(BikeTypes.EBIKE, 3);
        this.desiredBikes.put(BikeTypes.MOUNTAINBIKE, 5);
        this.bookingDate = LocalDate.now().plusYears(3);
        this.dateRange = new DateRange(this.bookingDate, this.bookingDate.plusDays(10));
    }

    @AfterEach
    public void restoreStartState() {
        Controller.getProviders().clear();
    }

    @Test
    public void testReturnOriginal() {
        this.quotes = Controller.getQuotes(desiredBikes, this.dateRange,
                this.customer.getLocation(),
                false);
        Quote chosenQuote = this.quotes.get(0);
        Provider originalProvider = chosenQuote.provider;
        Payment orderInfo = BookingController.bookQuote(chosenQuote, this.customer);
        originalProvider.registerReturn(orderInfo.getOrderNumber());
        for (BikeTypes type : this.desiredBikes.keySet()) {
            assertEquals(originalProvider.getOwnedBikesOfType(type).size(),
                    originalProvider.getAvailableBikesOfType(type).size());
        }
    }

    // need to call drop off on MockDeliveryService to finalize
    @Test
    public void testDeliveryReturn() {
        DeliveryServiceFactory.setupMockDeliveryService();
        this.quotes = Controller.getQuotes(desiredBikes, this.dateRange,
                this.customer.getLocation(),
                true);
        Quote chosenQuote = this.quotes.get(0);
        Payment orderInfo = BookingController.bookQuote(chosenQuote, this.customer);

    }
}
