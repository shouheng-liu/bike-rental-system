package uk.ac.ed.bikerental;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class ReturnBikesTest {

    LocalDate bookingDate;
    Location closeLocation;
    Customer customer;
    Provider provider, provider2;
    DateRange dateRange;
    ArrayList<Quote> quotes;
    HashMap<BikeTypes, Integer> desiredBikes;
    MockDeliveryService deliveryService;

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
    public void restoreStartState() {
        Controller.getProviders().clear();
    }

    @Test
    public void testReturnOriginal() {
        this.quotes = Controller.getQuotes(desiredBikes, this.dateRange,
                this.customer.getLocation(),
                false);
        Quote chosenQuote = this.quotes.get(0);
        ArrayList<Bike> chosenBikes = chosenQuote.getBikes();
        ArrayList<Bike> availableBikesProvider = new ArrayList<>();
        Provider originalProvider = chosenQuote.provider;

        Payment orderInfo = BookingController.bookQuote(chosenQuote, this.customer);
        assertTrue(originalProvider.registerReturn(orderInfo.getOrderNumber()));
        for (BikeTypes type : originalProvider.getOwnedBikes().keySet()) {
            availableBikesProvider.addAll(originalProvider.getAvailableBikesOfType(type));
        }
        for (Bike bike : chosenBikes) {
            assertTrue(availableBikesProvider.contains(bike));
        }
    }


    // need to call drop off on MockDeliveryService to finalize
    @Test
    public void testDeliveryReturn() {
        //Set up use case
        this.quotes = Controller.getQuotes(desiredBikes, this.dateRange,
                this.customer.getLocation(),
                true);
        Quote chosenQuote = this.quotes.get(0);
        Provider originalProvider = chosenQuote.provider;
        Provider partner = this.provider2;
        partner.addPartner(originalProvider); //register partnership
        Payment orderInfo = BookingController.bookQuote(chosenQuote, this.customer);
        Booking booking = BookingController.getBooking(orderInfo.getOrderNumber());
        ArrayList<Bike> bookedBikes = booking.bikes;

        //deliver bikes to client
        deliveryService.carryOutPickups(this.bookingDate);
        deliveryService.carryOutDropoffs();

        //register return at partner provider
        assertTrue(partner.registerReturn(orderInfo.getOrderNumber()));
        deliveryService.carryOutPickups(LocalDate.now()); //carryout pickups on date when returned
        deliveryService.carryOutDropoffs(); //carry out dropoffs
        originalProvider.checkForDropoffs();  // check for dropoffs
        ArrayList<Bike> availableBikes = new ArrayList<>();
        for (BikeTypes type : originalProvider.getOwnedBikes().keySet()) {
            availableBikes.addAll(originalProvider.getAvailableBikesOfType(type));
        }
        for (Bike bike : bookedBikes) {
            assertTrue(availableBikes.contains(bike));
        }
    }

    @Test
    public void testWrongPartner() {
        /* Set up scenario. As one might see, no partnership was registered hence we throw an
        error.
         */
        this.quotes = Controller.getQuotes(desiredBikes, this.dateRange,
                this.customer.getLocation(),
                true);
        Quote chosenQuote = this.quotes.get(0);
        Provider originalProvider = chosenQuote.provider;
        Payment orderInfo = BookingController.bookQuote(chosenQuote, this.customer);
        deliveryService.carryOutPickups(this.bookingDate);
        deliveryService.carryOutDropoffs();
        assertThrows(AssertionError.class, () ->
        {
            this.provider2.registerReturn(orderInfo.getOrderNumber());
        });
    }
}
