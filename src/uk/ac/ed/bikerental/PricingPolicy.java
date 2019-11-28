package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Collection;

public interface PricingPolicy {
    void setDailyRentalPrice(BikeType bikeType, BigDecimal dailyPrice);

    BigDecimal calculatePrice(Collection<Bike> bikes, DateRange duration);
}
