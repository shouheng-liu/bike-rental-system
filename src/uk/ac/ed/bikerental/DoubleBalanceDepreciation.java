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

    public void setDepreciationRate(BigDecimal depreciationRate) {
        this.depreciationRate = depreciationRate;
    }

    public BigDecimal getDepreciationRate() {
        return depreciationRate;
    }

    @Override
    public BigDecimal calculateValue(Bike bike, LocalDate date){
        BigDecimal age =
                BigDecimal.valueOf(new DateRange(bike.getManufacturingDate(), date).toYears());
        BigDecimal replacementValue = bike.getReplacementValue();     //
        BigDecimal depreciationFactor = BigDecimal.valueOf(2);             // replacementValue*
        depreciationFactor = depreciationFactor.multiply(this.depreciationRate);// (1 - 2 * depreciationRate)**age
        depreciationFactor = BigDecimal.ONE.subtract(depreciationFactor);
        depreciationFactor = depreciationFactor.pow(age.intValue());
        BigDecimal adjustedReplacementValue = replacementValue.multiply(depreciationFactor);

        if (adjustedReplacementValue.compareTo(BigDecimal.ZERO) > 0) {
            return adjustedReplacementValue;
        }
        else {
            throw new RuntimeException("Replacement value negative or zero. Try to set a lower " +
                    "depreciation rate or replace the bike.");
        }
    }
}
