package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Implements the interface PricingPolicy
 */
public class BasicPricing implements PricingPolicy {

    private Provider provider;

    public BasicPricing(Provider provider) {
        this.provider = provider;
    }

    @Override
    public void setDailyRentalPrice(BikeType bikeType, BigDecimal dailyPrice) {
        for (Bike bike : this.provider.getOwnedBikesOfType(bikeType.getBikeType())) {
            bike.setDailyPrice(dailyPrice);
        }
        this.provider.setRentalPrice(bikeType.getBikeType(), dailyPrice);
    }

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
