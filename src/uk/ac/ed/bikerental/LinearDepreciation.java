package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

public class LinearDepreciation implements ValuationPolicy {
    private BigDecimal depreciationRate = BigDecimal.valueOf(0.0);

    public LinearDepreciation(BigDecimal depreciationRate) {
        this.depreciationRate = depreciationRate;
    }
    public BigDecimal calculateValue(Bike bike, LocalDate date){
        return BigDecimal.ZERO;

    }
}
