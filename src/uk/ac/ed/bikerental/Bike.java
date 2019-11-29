package uk.ac.ed.bikerental;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

public class Bike implements Deliverable {
    private BikeState bikeState;
    private LocalDate manufacturingDate;
    private BikeTypes bikeType;
    private int identifier;
    private Location returnLocation;
    private BigDecimal dailyPrice;
    private String providerName;

    public Bike(BikeTypes bikeType, Location returnLocation, String providerName) {

        if (Controller.getBikeType(bikeType) == null) {
            throw new RuntimeException("Bike type not registered. Register it first, then try " +
                    "again.");
        }
        this.bikeState = BikeState.from("inShop");
        this.bikeType = bikeType;
        this.returnLocation = returnLocation;
        this.manufacturingDate = LocalDate.now();
        this.identifier = Controller.getIdentifierCount();
        this.providerName = providerName;
        Controller.setIdentifierCount(this.identifier + 1);
    }

    public BikeType getType() {
        return Controller.getBikeType(this.bikeType);
    }

    public LocalDate getManufacturingDate() {
        return this.manufacturingDate;
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getProviderName() {
        return this.providerName;
    }

    public BigDecimal getReplacementValue() {
        return this.getType().getReplacementValue();
    }

    public BigDecimal getDailyPrice() {
        return this.dailyPrice;
    }

    public void setDailyPrice(BigDecimal dailyPrice) {
        this.dailyPrice = dailyPrice.setScale(2, RoundingMode.CEILING);
    }



    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bikeState == null) ? 0 : bikeState.hashCode());
		result = prime * result + ((bikeType == null) ? 0 : bikeType.hashCode());
		result = prime * result + ((dailyPrice == null) ? 0 : dailyPrice.hashCode());
		result = prime * result + identifier;
		result = prime * result + ((manufacturingDate == null) ? 0 : manufacturingDate.hashCode());
		result = prime * result + ((providerName == null) ? 0 : providerName.hashCode());
		result = prime * result + ((returnLocation == null) ? 0 : returnLocation.hashCode());
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
		Bike other = (Bike) obj;
		if (bikeState != other.bikeState)
			return false;
		if (bikeType != other.bikeType)
			return false;
		if (dailyPrice == null) {
			if (other.dailyPrice != null)
				return false;
		} else if (!dailyPrice.equals(other.dailyPrice))
			return false;
		if (identifier != other.identifier)
			return false;
		if (manufacturingDate == null) {
			if (other.manufacturingDate != null)
				return false;
		} else if (!manufacturingDate.equals(other.manufacturingDate))
			return false;
		if (providerName == null) {
			if (other.providerName != null)
				return false;
		} else if (!providerName.equals(other.providerName))
			return false;
		if (returnLocation == null) {
			if (other.returnLocation != null)
				return false;
		} else if (!returnLocation.equals(other.returnLocation))
			return false;
		return true;
	}

	@Override
    public void onPickup() {
        this.bikeState = BikeState.PICKUP;
    }

    @Override
    public void onDropoff() {
        this.bikeState = BikeState.DROPOFF;
    }

    public BikeState getBikeState() {
        return this.bikeState;
    }

    public void setBikeState(BikeState bikeState) {
        this.bikeState = bikeState;
    }

}