package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ValuationPolicyTests {
    // You can add attributes here

    @BeforeEach
    void setUp() throws Exception {
        // Put setup here
        long[] depreciationRates = new long[10];
        LocalDate[] dates = new LocalDate[10];
        int i;
        BigDecimal replacementValue = BigDecimal.valueOf(900.0);
        new BikeType("mountainBike", replacementValue);
        replacementValue = BigDecimal.valueOf(1200.0);
        new BikeType("eBike", replacementValue);
        Location locationDummy = new Location("EH1 1LY", "Cowgate");

        for (i = 0; i < 10; i++) {
            depreciationRates[i] = 1 / 10;
            dates[i] = LocalDate.now().plusYears(i);
    }
    
    // TODO: Write tests for valuation policies

    @Test
    public void testDepreciation() {

            //ValuationPolicy valuation =
              //  new LinearDepreciation(BigDecimal.valueOf(depreciationRate));

        }
    }
}
