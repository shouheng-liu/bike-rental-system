package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DoubleBalanceDepreciation implements ValuationPolicy {
    private BigDecimal depreciationRate = BigDecimal.valueOf(0.0);

    public DoubleBalanceDepreciation(BigDecimal depreciationRate) {
        this.depreciationRate = depreciationRate;
    }
    public BigDecimal calculateValue(Bike bike, LocalDate date){
        return BigDecimal.ZERO;

    }
}
