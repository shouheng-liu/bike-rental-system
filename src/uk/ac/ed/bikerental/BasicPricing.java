package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Implements the interface PricingPolicy
 */
public class BasicPricing implements PricingPolicy {

    private Provider provider;

    public BasicPricing(Provider provider) {
        this.provider = provider;
    }

    /**
     * Set the daily price for a specified bike type at specified provider
     */
    @Override
    public void setDailyRentalPrice(BikeType bikeType, BigDecimal dailyPrice) {
        this.provider.setRentalPrice(bikeType.getBikeType(), dailyPrice);
        ArrayList<Bike> bikes = this.provider.getOwnedBikesOfType(bikeType.getBikeType());
        for (Bike bike : bikes) {
            bike.setDailyPrice(dailyPrice);
        }
    }

    /**
     * Calculates the rent price for specified number of bikes rented our over a specified date range
     */
    @Override
    public BigDecimal calculatePrice(Collection<Bike> bikes, DateRange duration) {
        long days = duration.toDays();
        BigDecimal price = BigDecimal.ZERO;
        for (Bike bike : bikes) {
            price = price.add(bike.getDailyPrice());
        }
        return price.multiply(BigDecimal.valueOf(days));
    }
}
