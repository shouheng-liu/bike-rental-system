package uk.ac.ed.bikerental;


import java.math.BigDecimal;
import java.time.LocalDate;

public class Bike implements Deliverable {
    private BikeState bikeState;
    private LocalDate manufacturingDate;
    private BikeTypes bikeType;
    private int identifier;
    private Location returnLocation;
    private BigDecimal dailyPrice;

    public Bike(BikeTypes bikeType, Location returnLocation) {

        if (Controller.getBikeType(bikeType) == null) {
            throw new RuntimeException("Bike type not registered. Register it first, then try " +
                    "again.");
        }
        this.bikeState = BikeState.from("inShop");
        this.bikeType = bikeType;
        this.returnLocation = returnLocation;
        this.manufacturingDate = LocalDate.now();
        this.identifier = Controller.getIdentifierCount();
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

    public BigDecimal getReplacementValue() {
        return this.getType().getReplacementValue();
    }

    public void setDailyPrice(BigDecimal dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public BigDecimal getDailyPrice() {
        return this.dailyPrice;
    }

    public void setBikeState(BikeState bikeState) {
        this.bikeState = bikeState;
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

}