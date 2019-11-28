package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class Provider {

    private HashMap<BikeTypes, ArrayList<Bike>> ownedBikes = new HashMap<BikeTypes,
            ArrayList<Bike>>();
    private HashMap<BikeTypes, ArrayList<Bike>> availableBikes = new HashMap<BikeTypes,
            ArrayList<Bike>>();
    private String name;
    private Location address;
    private BigDecimal depositRate;
    private HashMap<BikeTypes, BigDecimal> rentalPrices = new HashMap<>();
    private HashSet<Provider> partners = new HashSet<>();
    private ValuationPolicy valuationPolicy;
    private PricingPolicy pricingPolicy = new BasicPricing(this);


    public Provider(Location shopLocation, String name, BigDecimal depositRate) {
        this.name = name;
        this.address = shopLocation;
        this.depositRate = depositRate;
        this.valuationPolicy = new BasicValuation();
        Controller.addProvider(this);
    }

    public Provider(Location shopLocation, String name, BigDecimal depositRate,
                    ValuationPolicy valuationPolicy) {
        this.name = name;
        this.address = shopLocation;
        this.depositRate = depositRate;
        this.valuationPolicy = valuationPolicy;
        Controller.addProvider(this);
    }

    public ValuationPolicy getValuationPolicy() {
        return this.valuationPolicy;
    }

    public void setValuationPolicy(ValuationPolicy valuationPolicy) {
        this.valuationPolicy = valuationPolicy;
    }

    public PricingPolicy getPricingPolicy() {
        return this.pricingPolicy;
    }

    public BigDecimal getDepositRate() {
        return depositRate;
    }

    public void setDepositRate(BigDecimal depositRate) {
        this.depositRate = depositRate;
    }

    public void setRentalPrice(BikeTypes type, BigDecimal price) {
            this.rentalPrices.put(type, price);
        }
    public Location getAddress() {
        return this.address;
    }

    public ArrayList<Bike> getOwnedBikesOfType(BikeTypes type) {
        return this.ownedBikes.get(type);
    }

    public ArrayList<Bike> getAvailableBikesOfType(BikeTypes type) {
        return this.availableBikes.get(type);
    }

    public HashMap<BikeTypes, ArrayList<Bike>> getAvailableBikes() {
        return availableBikes;
    }

    public HashMap<BikeTypes, ArrayList<Bike>> getOwnedBikes() {
        return ownedBikes;
    }

    public String getName() {
        return this.name;
    }

    public void addPartner(Provider partner) {
        this.partners.add(partner);
    }

    public void addBikes(BikeTypes bikeType, int amount) {
        if (!this.ownedBikes.containsKey(bikeType)) {
            this.ownedBikes.put(bikeType, new ArrayList<Bike>());
            this.availableBikes.put(bikeType, new ArrayList<Bike>());
        }
        int i;
        for (i = 0; i < amount; i++) {
            Bike newBike = new Bike(bikeType, this.address, this.name);
            this.getOwnedBikesOfType(bikeType).add(newBike);
            this.getAvailableBikesOfType(bikeType).add(newBike);
        }
        rentalPrices.putIfAbsent(bikeType, BigDecimal.valueOf(10));
        BikeType type = Controller.getBikeType(bikeType);
        this.pricingPolicy.setDailyRentalPrice(type, rentalPrices.get(bikeType));
    }


    public ArrayList<Bike> getBikes(HashMap<BikeTypes, Integer> requestedBikes) {
        ArrayList<Bike> bikes = new ArrayList<Bike>();

        for (BikeTypes type : requestedBikes.keySet()) {
            assert requestedBikes.get(type) <= this.availableBikes.get(type).size();
        }
        for (BikeTypes type : requestedBikes.keySet()) {
            int i;
            int numberBikes = requestedBikes.get(type);
            for (i = 0; i < numberBikes; i++) {
                Bike addedBike = this.getAvailableBikesOfType(type).get(i);
                bikes.add(addedBike);
            }
        }
        return bikes;
    }

    public void lendBikes(ArrayList<Bike> requestedBikes) {

        for (Bike bike : requestedBikes) {
            bike.setBikeState(BikeState.RENTED);
            BikeTypes bikeType = bike.getType().getBikeType();
            this.availableBikes.get(bikeType).remove(bike);
        }
    }

    private void recordBikeReturn(HashMap<BikeTypes, ArrayList<Bike>> returnedBikes) {
        //ArrayList<Integer> ids = new ArrayList<Integer>();
        for (BikeTypes type : returnedBikes.keySet()) {
            this.getAvailableBikesOfType(type).addAll(returnedBikes.get(type));
            for (Bike bike : this.getAvailableBikesOfType(type)) {
                bike.setBikeState(BikeState.INSHOP);
                //ids.add(bike.getIdentifier());
            }
        }
    }

    public void registerReturn(int bookingNumber) { //ArrayList<Bike> returnedBikes
        Booking booking = BookingController.getBooking(bookingNumber);
        String providerName = booking.provider.getName();
        if (providerName.equals(this.name)) {
            for (Bike bike : booking.getBikes()) {
                this.availableBikes.get(bike.getType().getBikeType()).add(bike);
            }
        } else {
            for (Provider partner : this.partners) {
                if (partner.getName().equals(providerName)) {
                    DeliveryService deliveryService = DeliveryServiceFactory.getDeliveryService();
                    for (Deliverable bike : booking.getBikes()) {
                        deliveryService.scheduleDelivery(bike, this.address,
                                partner.getAddress(), LocalDate.now());
                    }
                }
            }
        }
    }

    //fully implement that, don't forget to send money back to original provider
    public void checkForDropoffs() {
        for (BikeTypes type : this.ownedBikes.keySet()) {
            for (Bike bike : this.ownedBikes.get(type)) {
                if (bike.getDelivery() == OnDelivery.DROPOFF) {
                    bike.setDelivery(OnDelivery.IDLE);
                    bike.setBikeState(BikeState.INSHOP);
                    availableBikes.get(type).add(bike);
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Provider provider = (Provider) o;
        return Objects.equals(ownedBikes, provider.ownedBikes) &&
                Objects.equals(availableBikes, provider.availableBikes) &&
                getName().equals(provider.getName()) &&
                getAddress().equals(provider.getAddress()) &&
                getDepositRate().equals(provider.getDepositRate()) &&
                Objects.equals(partners, provider.partners) &&
                getValuationPolicy().equals(provider.getValuationPolicy()) &&
                getPricingPolicy().equals(provider.getPricingPolicy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownedBikes, availableBikes, getName(), getAddress(), getDepositRate()
                , partners, getValuationPolicy(), getPricingPolicy());
    }

}
