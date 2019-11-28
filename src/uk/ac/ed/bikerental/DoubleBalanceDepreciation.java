package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DoubleBalanceDepreciation implements ValuationPolicy {
    private BigDecimal depreciationRate;

    public DoubleBalanceDepreciation(BigDecimal depreciationRate) {
        assert depreciationRate.compareTo(BigDecimal.ONE) <= 0;
        assert depreciationRate.compareTo(BigDecimal.ZERO) >= 0;
        this.depreciationRate = depreciationRate;
    }

    public BigDecimal getDepreciationRate() {
        return depreciationRate;
    }

    public void setDepreciationRate(BigDecimal depreciationRate) {
        this.depreciationRate = depreciationRate;
    }

    @Override
    public BigDecimal calculateValue(Bike bike, LocalDate date) {

        //Complete formula (1 - 2 * depreciationRate)**age

        //first get age difference between bike and actual hiring date
        BigDecimal age =
                BigDecimal.valueOf(new DateRange(bike.getManufacturingDate(), date).toYears());
        BigDecimal replacementValue = bike.getReplacementValue();
        BigDecimal depreciationFactor = BigDecimal.valueOf(2);
        // depreciationfactor by two
        depreciationFactor = depreciationFactor.multiply(this.depreciationRate);
        // 1 - depreciationFactor
        depreciationFactor = BigDecimal.ONE.subtract(depreciationFactor);
        // take it to the power of age
        depreciationFactor = depreciationFactor.pow(age.intValue());
        //multiply result with the replacement value
        BigDecimal adjustedReplacementValue = replacementValue.multiply(depreciationFactor);
        if (adjustedReplacementValue.compareTo(BigDecimal.ZERO) > 0) {
            return adjustedReplacementValue;
        } else {
            throw new RuntimeException("Replacement value negative or zero. Try to set a lower " +
                    "depreciation rate or replace the bike.");
        }
    }
}
