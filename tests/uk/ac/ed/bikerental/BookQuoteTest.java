package uk.ac.ed.bikerental;

import com.sun.org.apache.xpath.internal.operations.Quo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class BookQuoteTest {

    MockDeliveryService deliveryService;
    LocalDate bookingDate;
    Location closeLocation;
    Customer customer;
    Provider provider, provider2;
    DateRange dateRange;
    ArrayList<Quote> quotes;
    HashMap<BikeTypes, Integer> desiredBikes;
    Quote chosenQuote;
    Payment orderInfo;

    @BeforeEach
    void setUp() {
        DeliveryServiceFactory.setupMockDeliveryService();
        this.deliveryService =
                (MockDeliveryService) DeliveryServiceFactory.getDeliveryService();
        this.closeLocation = new Location("EH1 1LY", "Cowgate");
        this.customer = new Customer(closeLocation);
        this.provider = new Provider(closeLocation, "Dummy", BigDecimal.valueOf(0.2));
        ValuationPolicy valuationPolicy = new LinearDepreciation(BigDecimal.valueOf(0.1));
        this.provider2 = new Provider(this.closeLocation, "Best deals in Edinbraaa",
                BigDecimal.valueOf(0.2), valuationPolicy);
        BikeType bikeType = new BikeType("mountainBike", BigDecimal.valueOf(900));
        new BikeType("eBike", BigDecimal.valueOf(1230.0));
        this.provider.addBikes(BikeTypes.EBIKE, 3);
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
        this.quotes = Controller.getQuotes(desiredBikes, this.dateRange,
                this.customer.getLocation(),
                false);
        this.chosenQuote = this.quotes.get(0);
        this.orderInfo = BookingController.bookQuote(chosenQuote, this.customer);
    }

    /*
    Need to delete created providers, since otherwise we would have different amount of quotes
    when generated.
     */
    @AfterEach
    public void deleteProviders() {
        Controller.getProviders().clear();
    }

    @Test
    public void testBooking() {
        assertEquals(chosenQuote.price, orderInfo.getPrice());
        assertEquals(chosenQuote.deposit, orderInfo.getDeposit());
        assertEquals(chosenQuote.total, orderInfo.getTotal());
        Booking booking = BookingController.getBooking(orderInfo.getOrderNumber());
        assertEquals(chosenQuote, booking);
    }

    @Test
    public void correctBikesRemoved() {
        Payment orderInfo = BookingController.bookQuote(chosenQuote, this.customer);
        Booking booking = BookingController.getBooking(orderInfo.getOrderNumber());
        ArrayList<Bike> chosenBikes = booking.bikes;
        Provider originalProvider = booking.provider;
        ArrayList<Bike> allBikesProvider = new ArrayList<>();
        ArrayList<Bike> availableBikesProvider = new ArrayList<>();
        for (BikeTypes type : originalProvider.getOwnedBikes().keySet()) {
            allBikesProvider.addAll(originalProvider.getOwnedBikesOfType(type));
        }
        for (BikeTypes type : originalProvider.getOwnedBikes().keySet()) {
            availableBikesProvider.addAll(originalProvider.getAvailableBikesOfType(type));
        }
        for (Bike bike : chosenBikes) {
            assertTrue(allBikesProvider.contains(bike));
            assertFalse(availableBikesProvider.contains(bike));
        }
    }

    //check that bikes in delivery queue
    @Test
    public void testDelivery() {
        this.quotes = Controller.getQuotes(desiredBikes, this.dateRange,
                this.customer.getLocation(),
                true);
        Quote chosenQuote = this.quotes.get(0);
        Payment orderInfo = BookingController.bookQuote(chosenQuote, this.customer);
        Booking booking = BookingController.getBooking(orderInfo.getOrderNumber());
        for (Bike bike : booking.getBikes()) {
            assertTrue(deliveryService.pickups.get(this.bookingDate).contains(bike));
        }
    }

    @Test
    public void testDeliveryToday() {
        this.bookingDate = LocalDate.now();
        this.dateRange = new DateRange(this.bookingDate, this.bookingDate.plusDays(10));
        this.quotes = Controller.getQuotes(desiredBikes, this.dateRange,
                this.customer.getLocation(),
                true);
        Quote chosenQuote = this.quotes.get(0);
        Payment orderInfo = BookingController.bookQuote(chosenQuote, this.customer);
        Booking booking = BookingController.getBooking(orderInfo.getOrderNumber());
        for (Bike bike : booking.getBikes()) {
            assertTrue(deliveryService.pickups.get(this.bookingDate).contains(bike));
        }
    }
}
