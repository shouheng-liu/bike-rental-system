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

        //Complete formula (1 - 2 * depreciationRate)**age
        BigDecimal age = //first get age difference between bike and actual hiring date
                BigDecimal.valueOf(new DateRange(bike.getManufacturingDate(), date).toYears());
        BigDecimal replacementValue = bike.getReplacementValue();   //get replacement value
        BigDecimal depreciationFactor = BigDecimal.valueOf(2);           //   replacementValue*
        depreciationFactor = depreciationFactor.multiply(this.depreciationRate);// multiply
        // depreciationfactor by two
        depreciationFactor = BigDecimal.ONE.subtract(depreciationFactor); // 1 - depreciationFactor
        depreciationFactor = depreciationFactor.pow(age.intValue()); // take it to the power of age
        BigDecimal adjustedReplacementValue = replacementValue.multiply(depreciationFactor);
        //multiply that with the replacement value
        if (adjustedReplacementValue.compareTo(BigDecimal.ZERO) > 0) {
            return adjustedReplacementValue;
        }
        else {
            throw new RuntimeException("Replacement value negative or zero. Try to set a lower " +
                    "depreciation rate or replace the bike.");
        }
    }
}
