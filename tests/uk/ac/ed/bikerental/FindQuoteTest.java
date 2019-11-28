package uk.ac.ed.bikerental;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    public void testFarAwayClient() {
        DateRange dateRange = new DateRange(this.bookingDate, this.bookingDate.plusDays(10));
        this.quotes = Controller.getQuotes(desiredBikes, dateRange, this.customer3.getLocation(),
                true);
        assertEquals(0, this.quotes.size());
    }

    @Test
    public void testMultiQuotes() {
        BikeType bikeType = Controller.getBikeType(BikeTypes.EBIKE);
        provider2.getPricingPolicy().setDailyRentalPrice(bikeType, BigDecimal.valueOf(20));
        this.quotes = Controller.getQuotes(desiredBikes, dateRange, this.customer.getLocation(),
                true);
        assertEquals(2, this.quotes.size());
    }

    @Test
    public void testOnlyAvailableBikes() {
        DeliveryServiceFactory.setupMockDeliveryService();
        this.quotes = Controller.getQuotes(desiredBikes, this.dateRange,
                this.customer.getLocation(),
                true);
        BookingController.bookQuote(this.quotes.get(0), this.customer);
        this.quotes = Controller.getQuotes(desiredBikes, this.dateRange,
                this.customer.getLocation(),
                true);
        assertEquals(1, this.quotes.size());
    }


}
