package uk.ac.ed.bikerental;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FindQuoteTest {

    LocalDate bookingDate;
    Location closeLocation, farAwayLocation;
    Customer customer, customer2, customer3;
    Provider provider, provider2;
    DateRange dateRange;
    ArrayList<Quote> quotes;
    HashMap<BikeTypes, Integer> desiredBikes;

    @BeforeEach
    void setUp() {
    	Controller.getProviders().clear();
        this.closeLocation = new Location("EH1 1LY", "Cowgate");
        this.farAwayLocation = new Location("TW6 1EW", "Inner Ring E");
        this.customer = new Customer(closeLocation);
        this.customer2 = new Customer(closeLocation);
        this.customer3 = new Customer(farAwayLocation);
        this.provider = new Provider(closeLocation, "Dummy", BigDecimal.valueOf(0.2));
        ValuationPolicy valuationPolicy = new LinearDepreciation(BigDecimal.valueOf(0.1));
        this.provider2 = new Provider(this.closeLocation, "Best deals in Edinbraaa",
                BigDecimal.valueOf(0.2), valuationPolicy);
        new BikeType("mountainBike", BigDecimal.valueOf(900));
        new BikeType("eBike", BigDecimal.valueOf(1230.0));
        this.provider.addBikes(BikeTypes.EBIKE, 3);
        this.provider.addBikes(BikeTypes.MOUNTAINBIKE, 8);
        this.provider2.addBikes(BikeTypes.MOUNTAINBIKE, 10);
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
    public void restoreStart() {
        Controller.getProviders().clear();
    }

    /*
    5 mountainbikes, each 900 pounds, where the deposit for each, under a 20% deposit rate and a
    depreciation rate of 10% leads, for double balance depreciation (with three years of
    depreciation) to 460.8 as deposit.
     3 e-bikes, each 1230 pounds, leads with same rates to a 377.86 pound deposit. So 838.66
     pounds as deposit, and 800 pounds as price under a daily pricing of 10 pounds for each bike.
     */
    @Test
    public void testPricing() {
        this.quotes = Controller.getQuotes(desiredBikes, this.dateRange,
                this.customer.getLocation(), true);
        System.out.println(quotes.get(0).price.stripTrailingZeros());
        assertEquals(BigDecimal.valueOf(1638.66).stripTrailingZeros(),
                quotes.get(0).total.stripTrailingZeros());
        assertEquals(BigDecimal.valueOf(800.0).stripTrailingZeros(),
                quotes.get(0).price.stripTrailingZeros());
        assertEquals(BigDecimal.valueOf(838.66).stripTrailingZeros(),
                quotes.get(0).deposit.stripTrailingZeros());
    }

    /*
    Checks that if delivery is requested, but all providers are too far away, no quotes are
    returned.
     */
    @Test
    public void testFarAwayClient() {
        this.quotes = Controller.getQuotes(desiredBikes, dateRange, this.customer3.getLocation(),
                true);
        assertEquals(0, this.quotes.size());
    }

    /*
    Tests if multiple matching quotes can be received.
     */
    @Test
    public void testMultiQuotes() {
        BikeType bikeType = Controller.getBikeType(BikeTypes.EBIKE);
        this.quotes = Controller.getQuotes(desiredBikes, dateRange, this.customer.getLocation(),
                true);
        assertEquals(2, this.quotes.size());
    }

    /*
    Checks if the correct number of bikes are listed in quote
     */
    @Test
    public void testCorrectAmountBikes() {
        this.quotes = Controller.getQuotes(desiredBikes, dateRange, this.customer.getLocation(),
                true);
        ArrayList<Bike> chosenBikes = this.quotes.get(0).getBikes();
        int eBikeCount = 0;
        int mountainCount = 0;
        for (Bike bike : chosenBikes) {
            if (bike.getType().getBikeType().equals(BikeTypes.MOUNTAINBIKE)) {
                mountainCount++;
            }
            else {
                eBikeCount++;
            }
        }
        assertEquals(desiredBikes.get(BikeTypes.MOUNTAINBIKE), mountainCount);
        assertEquals(desiredBikes.get(BikeTypes.EBIKE), eBikeCount);


    }

    /*
    Checks if only available bikes are considered for each quote. To create such scenario, we
    first book a quote and then check again if one quote less is available.
     */
    @Test
    public void testOnlyAvailableBikes() {
        DeliveryServiceFactory.setupMockDeliveryService();
        this.quotes = Controller.getQuotes(desiredBikes, this.dateRange,
                this.customer.getLocation(),
                true);
        assertEquals(2, this.quotes.size());
        BookingController.bookQuote(this.quotes.get(0), this.customer);
        this.quotes = Controller.getQuotes(desiredBikes, this.dateRange,
                this.customer.getLocation(),
                true);
        assertEquals(1, this.quotes.size());
    }



}
