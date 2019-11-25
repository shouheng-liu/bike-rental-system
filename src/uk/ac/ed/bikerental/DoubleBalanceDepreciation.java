package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DoubleBalanceDepreciation implements ValuationPolicy {
    private BigDecimal depreciationRate = BigDecimal.valueOf(0.0);

    public DoubleBalanceDepreciation(BigDecimal depreciationRate) {
        this.depreciationRate = depreciationRate;
    }

    public void setDepreciationRate(BigDecimal depreciationRate) {
        this.depreciationRate = depreciationRate;
    }

    public BigDecimal getDepreciationRate() {
        return depreciationRate;
    }

    public BigDecimal calculateValue(Bike bike, LocalDate date){
        BigDecimal age =
                BigDecimal.valueOf(new DateRange(bike.getManufacturingDate(), date).toYears());
        BigDecimal replacementValue = bike.getType().getReplacementValue();     //
        BigDecimal depreciationFactor = age.subtract(BigDecimal.ONE);           // replacementValue*
        depreciationFactor = depreciationFactor.multiply(this.depreciationRate);// (1 - (age - 1) *
        depreciationFactor = BigDecimal.ONE.subtract(depreciationFactor);    //depreciationRate)**age
        depreciationFactor = depreciationFactor.pow(age.intValue());
        BigDecimal adjustedReplacementValue = replacementValue.multiply(depreciationFactor);

        if (adjustedReplacementValue.compareTo(BigDecimal.ZERO) >= 0) {
            return adjustedReplacementValue;
        }
        else {
            throw new RuntimeException("Replacement value negative.");
        }
    }
}
