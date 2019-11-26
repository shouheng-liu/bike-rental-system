package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Provider {

    private HashMap<BikeTypes, ArrayList<Bike>> bikes = new HashMap<BikeTypes, ArrayList<Bike>>();
    private String name;
    private Location address;
    private BigDecimal depositRate;
    private ArrayList<Provider> partners = new ArrayList<Provider>();

    public Provider(Location shopLocation, String name, BigDecimal depositRate) {
        this.name = name;
        this.address = shopLocation;
        this.depositRate = depositRate;
    }
    private void addBikes(BikeTypes bikeType, int amount) {
        int i;
        for (i = 0; i < amount; i++) {
            Bike newBike = new Bike(bikeType, this.address);
            this.bikes.get(bikeType).add(newBike);
        }
    }

    private ArrayList<Bike> lendBikes(HashMap<BikeTypes, Integer> requestedBikes) {
        ArrayList<Bike> lendedBikes = new ArrayList<Bike>();

        for (BikeTypes type : requestedBikes.keySet()) {
            assert requestedBikes.get(type) <= this.bikes.get(type).size();
        }
        for (BikeTypes type: requestedBikes.keySet()) {
            int i;
            int numberBikes = requestedBikes.get(type);
            for (i = 0; i < numberBikes ; i++) {
                Bike addedBike = this.bikes.get(type).get(0);
                this.bikes.get(type).remove(0);
                addedBike.setBikeState(BikeState.RENTED);
                lendedBikes.add(addedBike);
            }
        }
        return lendedBikes;
        /*
        int i;
        for (i = 0; i < amount; i++) {
            bikes.add(this.bikes.get(bikeType).get(0));
            this.bikes.get(bikeType).remove(0);
        }*/
    }

    public ArrayList<Integer> recordBikeReturn(HashMap<BikeTypes, ArrayList<Bike>> returnedBikes) {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (BikeTypes type : returnedBikes.keySet()) {
            bikes.get(type).addAll(returnedBikes.get(type));
            for (Bike bike : bikes.get(type)) {
                bike.setBikeState(BikeState.INSHOP);
                ids.add(bike.getIdentifier());
            }
        }
        return ids;
    }



}
