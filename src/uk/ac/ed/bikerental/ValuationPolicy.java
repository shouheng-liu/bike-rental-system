package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ValuationPolicy {
    BigDecimal calculateValue(Bike bike, LocalDate date);
}
