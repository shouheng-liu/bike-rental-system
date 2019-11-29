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

    /*
     * Adds new bikes to inventory
     */
    public void addBikes(BikeTypes bikeType, int amount) {
        this.ownedBikes.putIfAbsent(bikeType, new ArrayList<Bike>());
        this.availableBikes.putIfAbsent(bikeType, new ArrayList<Bike>());
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

    /*
     * Returns requested quantity of bike objects if as many are available, otherwise throws an
     * assertion error.
     */
    public ArrayList<Bike> getBikes(HashMap<BikeTypes, Integer> requestedBikes) {
        ArrayList<Bike> bikes = new ArrayList<Bike>();

        for (BikeTypes type : requestedBikes.keySet()) {
            assert requestedBikes.get(type) <= this.availableBikes.get(type).size();
        }
        for (BikeTypes type : requestedBikes.keySet()) {
            int i;
            int numberBikes = requestedBikes.get(type);
            assert numberBikes <= this.getAvailableBikesOfType(type).size();
            for (i = 0; i < numberBikes; i++) {
                Bike addedBike = this.getAvailableBikesOfType(type).get(i);
                bikes.add(addedBike);
            }
        }
        return bikes;
    }

    /*
     * Removes lent bikes from available bikes.
     */
    public void lendBikes(ArrayList<Bike> requestedBikes) {

        for (Bike bike : requestedBikes) {
            bike.setBikeState(BikeState.RENTED);
            BikeTypes bikeType = bike.getType().getBikeType();
            this.availableBikes.get(bikeType).remove(bike);
        }
    }

    /*
     * Adds returned bikes into inventory.
     */
    private void recordBikeReturn(HashMap<BikeTypes, ArrayList<Bike>> returnedBikes) {
        for (BikeTypes type : returnedBikes.keySet()) {
            this.getAvailableBikesOfType(type).addAll(returnedBikes.get(type));
            for (Bike bike : this.getAvailableBikesOfType(type)) {
                bike.setBikeState(BikeState.INSHOP);
            }
        }
    }

    /*
     * Puts bikes associated with the bookingNumber into the provider's inventory if they belong to
     * him, otherwise sends them to the corresponding partner provider. Returns true if the provider
     * got successfully identified, otherwise throws an assertion error.
     */
    public boolean registerReturn(int bookingNumber) {
        Booking booking = BookingController.getBooking(bookingNumber);
        booking.setBookingStatus(BookingStates.LEASEOVER);
        String providerName = booking.provider.getName();
        if (providerName.equals(this.name)) {
            for (Bike bike : booking.getBikes()) {
                this.availableBikes.get(bike.getType().getBikeType()).add(bike);
            }
            return true;
        } else {
            for (Provider partner : this.partners) {
                if (partner.getName().equals(providerName)) {
                    DeliveryService deliveryService = DeliveryServiceFactory.getDeliveryService();
                    for (Deliverable bike : booking.getBikes()) {
                        deliveryService.scheduleDelivery(bike, this.address,
                                partner.getAddress(), LocalDate.now());
                    }
                    return true;
                }
            }
            assert false;
            return false;
        }
    }

   /*
    * Provider checks for dropoffs. If any of its bikes have been dropped of, he adds those to his
    * available bikes.
    */
    public void checkForDropoffs() {
        for (BikeTypes type : this.ownedBikes.keySet()) {
            for (Bike bike : this.ownedBikes.get(type)) {
                if (bike.getBikeState() == BikeState.DROPOFF) {
                    bike.setBikeState(BikeState.INSHOP);
                    availableBikes.get(type).add(bike);
                }
            }
        }
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((availableBikes == null) ? 0 : availableBikes.hashCode());
		result = prime * result + ((depositRate == null) ? 0 : depositRate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((ownedBikes == null) ? 0 : ownedBikes.hashCode());
		result = prime * result + ((partners == null) ? 0 : partners.hashCode());
		result = prime * result + ((rentalPrices == null) ? 0 : rentalPrices.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Provider other = (Provider) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (availableBikes == null) {
			if (other.availableBikes != null)
				return false;
		} else if (!availableBikes.equals(other.availableBikes))
			return false;
		if (depositRate == null) {
			if (other.depositRate != null)
				return false;
		} else if (!depositRate.equals(other.depositRate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ownedBikes == null) {
			if (other.ownedBikes != null)
				return false;
		} else if (!ownedBikes.equals(other.ownedBikes))
			return false;
		if (partners == null) {
			if (other.partners != null)
				return false;
		} else if (!partners.equals(other.partners))
			return false;
		if (rentalPrices == null) {
			if (other.rentalPrices != null)
				return false;
		} else if (!rentalPrices.equals(other.rentalPrices))
			return false;
		return true;
	}



}
