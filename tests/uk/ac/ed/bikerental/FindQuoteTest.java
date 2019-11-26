package uk.ac.ed.bikerental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class FindQuoteTest {

    Location closeLocation, farAwayLocation;
    Customer customer, customer2,customer3;
    Provider provider;
    @BeforeEach
    void setUp() {
        this.closeLocation = new Location("EH1 1LY", "Cowgate");
        this.farAwayLocation = new Location("TW6 1EW", "Inner Ring E");
        this.customer = new Customer(closeLocation);
        this.customer2 = new Customer(closeLocation);
        this.customer3 = new Customer(farAwayLocation);
        this.provider = new Provider(closeLocation, "Dummy", BigDecimal.valueOf(0.2));
        new BikeType("eBike", BigDecimal.valueOf(1230.0));
        this.provider.addBikes(BikeTypes.EBIKE, 3);
        this.provider.addBikes(BikeTypes.MOUNTAINBIKE, 8);
        ValuationPolicy valuationPolicy = new DoubleBalanceDepreciation(BigDecimal.valueOf(0.1));
        this.provider.setValuationPolicy(valuationPolicy);
    }

    @Test
    public void testFindQuote() {
        HashMap<BikeTypes, Integer> desiredBikes = new HashMap<>();
        desiredBikes.put(BikeTypes.EBIKE, 3);
        desiredBikes.put(BikeTypes.MOUNTAINBIKE, 5);
        LocalDate bookingDate = LocalDate.now().plusYears(3);
        DateRange dateRange = new DateRange(bookingDate, bookingDate.plusDays(10));
        ArrayList<Quote> quotes = Controller.getQuotes(desiredBikes, dateRange,
                this.customer.getLocation());
        System.out.println(quotes.get(0).total);
        System.out.println(quotes.get(0).price);
        System.out.println(quotes.get(0).deposit);
        System.out.println(this.provider.getAvailableBikesOfType(BikeTypes.MOUNTAINBIKE));
        System.out.println(this.provider.getOwnedBikesOfType(BikeTypes.MOUNTAINBIKE));

    }


}
