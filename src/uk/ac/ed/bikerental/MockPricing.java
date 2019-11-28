package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MockPricing implements PricingPolicy{

    /*variable to store corresponding
    discount for each day according to the pricing policy */
    private HashMap<Integer, Integer> discountList = new HashMap<>();
    private Provider provider;

    public MockPricing(Provider provider) {
        this.provider = provider;
        this.discountList.put(1, 0);
        this.discountList.put(2, 0);
        this.discountList.put(3, 5);
        this.discountList.put(4, 5);
        this.discountList.put(5, 5);
        this.discountList.put(6, 8);
        this.discountList.put(7, 8);
        this.discountList.put(8, 8);
        this.discountList.put(9, 10);
        this.discountList.put(10, 10);
        this.discountList.put(11, 10);
        this.discountList.put(12, 10);
        this.discountList.put(13, 10);
        this.discountList.put(14, 10);
        this.discountList.put(15, 10);
        this.discountList.put(16, 10);
        this.discountList.put(17, 15);
    }
    @Override
    public void setDailyRentalPrice(BikeType bikeType, BigDecimal dailyPrice) {
        //set the value passed in as dailyPrice to the hire cost of the BikeType passed in
        for (Bike bike : this.provider.getOwnedBikesOfType(bikeType.getBikeType())) {
            bike.setDailyPrice(dailyPrice);
        }
        this.provider.setRentalPrice(bikeType.getBikeType(), dailyPrice);
    }

    @Override
    public BigDecimal calculatePrice(Collection<Bike> bikes, DateRange duration) {
        /*iterate through the bikes required by the customer and
        calculate total cost*/
        BigDecimal totalCost = new BigDecimal(0);
        for (Bike bike : bikes) {
            totalCost = totalCost.add(bike.getDailyPrice());
        }
        //fetch the last day in the HashMap as this will be the 'endless number'
        Integer lastDay = discountList.size();
        //calculate number of days duration covers
        Long numberDaysLong = duration.toDays();
        int numberDays = Math.toIntExact(numberDaysLong);
        BigDecimal hundred = new BigDecimal(100);
        BigDecimal one = new BigDecimal(1);
        /*
         *if the number of days is greater than or equal to the endless number
         *get the discount for the last day in the HashMap (the endless number)
         *and apply the discount to the total cost
         */
        if (numberDays >= lastDay) {
            BigDecimal discount = BigDecimal.valueOf(this.discountList.get(lastDay));
            discount = discount.divide(hundred);
            totalCost = totalCost.multiply(one.subtract(discount));
        }
        /*
         * otherwise, get the appropriate discount for the number of days
         * from the HashMap and apply to total cost
         */
        else {
            BigDecimal discount = BigDecimal.valueOf(this.discountList.get(numberDays));
            discount = discount.divide(hundred);
            totalCost = totalCost.multiply(one.subtract(discount));
        }
        //return total cost to 2 decimal places
        return totalCost.setScale(2);
    }
}
