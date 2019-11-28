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
    private OnDelivery delivery;

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
        this.delivery = OnDelivery.IDLE;
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

    public void setDailyPrice(BigDecimal dailyPrice) {
        this.dailyPrice = dailyPrice.setScale(2, RoundingMode.CEILING);
    }

    public BigDecimal getDailyPrice() {
        return this.dailyPrice;
    }

    public void setBikeState(BikeState bikeState) {
        this.bikeState = bikeState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bike bike = (Bike) o;
        return getIdentifier() == bike.getIdentifier() &&
                getBikeState() == bike.getBikeState() &&
                getManufacturingDate().equals(bike.getManufacturingDate()) &&
                bikeType == bike.bikeType &&
                returnLocation.equals(bike.returnLocation) &&
                Objects.equals(getDailyPrice(), bike.getDailyPrice()) &&
                getProviderName().equals(bike.getProviderName()) &&
                getDelivery() == bike.getDelivery();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBikeState(), getManufacturingDate(), bikeType, getIdentifier(),
                 returnLocation, getDailyPrice(), getProviderName(), getDelivery());
    }

    public OnDelivery getDelivery() {
        return this.delivery;
    }

    public void setDelivery(OnDelivery delivery) {
        this.delivery = delivery;
    }

    @Override
    public void onPickup() {
        this.delivery = OnDelivery.PICKUP;
    }

    @Override
    public void onDropoff() {
        this.delivery = OnDelivery.DROPOFF;
    }
    public BikeState getBikeState() {
        return this.bikeState;
    }

}