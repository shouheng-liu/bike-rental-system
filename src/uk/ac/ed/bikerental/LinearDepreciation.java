package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

public class LinearDepreciation implements ValuationPolicy {

    private BigDecimal depreciationRate = BigDecimal.valueOf(0.0);

    public LinearDepreciation(BigDecimal depreciationRate) {
        assert depreciationRate.compareTo(BigDecimal.ONE) <= 0;
        assert depreciationRate.compareTo(BigDecimal.ZERO) >= 0;
        this.depreciationRate = depreciationRate;
    }

    public void setDepreciationRate(BigDecimal depreciationRate) {
        this.depreciationRate = depreciationRate;
    }

    public BigDecimal getDepreciationRate() {
        return this.depreciationRate;
    }

    @Override
    public BigDecimal calculateValue(Bike bike, LocalDate date){
        // Complete formula: replacementValue - age*depreciationRate*replacementValue

        // first we get the age
        BigDecimal age =
                BigDecimal.valueOf(new DateRange(bike.getManufacturingDate(), date).toYears());
        // get replacementValue
        BigDecimal replacementValue = bike.getReplacementValue();
        // multiply age*depreciationRate*replacementValue
        BigDecimal depreciation =
                replacementValue.multiply(age).multiply(this.depreciationRate);
        //Subtract result from initial replacement value
        BigDecimal adjustedReplacementValue = replacementValue.subtract(depreciation);

        if (adjustedReplacementValue.compareTo(BigDecimal.ZERO) >= 0) {
            return adjustedReplacementValue;
        }
        else {
            throw new RuntimeException("Replacement value negative. Try to set a lower " +
                    "depreciation rate or replace the bike.");
        }
    }
}
