package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Provider {

    private HashMap<BikeTypes, ArrayList<Bike>> ownedBikes = new HashMap<BikeTypes,
            ArrayList<Bike>>();
    private HashMap<BikeTypes, ArrayList<Bike>> availableBikes = new HashMap<BikeTypes,
            ArrayList<Bike>>();
    private String name;
    private Location address;
    private BigDecimal depositRate;
    private ArrayList<Provider> partners = new ArrayList<Provider>();
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

    public void setValuationPolicy(ValuationPolicy valuationPolicy) {
        this.valuationPolicy = valuationPolicy;
    }

    public ValuationPolicy getValuationPolicy() {
        return this.valuationPolicy;
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

    public Location getAddress() {
        return this.address;
    }

    public ArrayList<Bike> getOwnedBikesOfType(BikeTypes type) {
        return this.ownedBikes.get(type);
    }

    public ArrayList<Bike> getAvailableBikesOfType(BikeTypes type) {
        return this.availableBikes.get(type);
    }

    public String getName() {
        return this.name;
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
        this.pricingPolicy.setDailyRentalPrice(Controller.getBikeType(bikeType),
                BigDecimal.valueOf(10));
    }


    public ArrayList<Bike> getBikes(HashMap<BikeTypes, Integer> requestedBikes) {
        ArrayList<Bike> bikes = new ArrayList<Bike>();

        for (BikeTypes type : requestedBikes.keySet()) {
            assert requestedBikes.get(type) <= this.availableBikes.get(type).size();
        }
        for (BikeTypes type: requestedBikes.keySet()) {
            int i;
            int numberBikes = requestedBikes.get(type);
            for (i = 0; i < numberBikes ; i++) {
                Bike addedBike = this.getAvailableBikesOfType(type).get(0);
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

    public void registerReturn(ArrayList<Bike> returnedBikes) {
        String providerName = returnedBikes.get(0).getProviderName();
        if (providerName.equals(this.name)) {
            for (Bike bike : returnedBikes) {
                this.availableBikes.get(bike.getType().getBikeType()).add(bike);
            }
        } else {
            for (Provider partner : this.partners) {
                if (partner.getName().equals(providerName)) {
                    DeliveryService deliveryService = DeliveryServiceFactory.getDeliveryService();
                    for (Deliverable bike : returnedBikes) {
                        deliveryService.scheduleDelivery(bike, this.address,
                                partner.getAddress(), LocalDate.now());
                    }
                }
            }
        }
    }
}
