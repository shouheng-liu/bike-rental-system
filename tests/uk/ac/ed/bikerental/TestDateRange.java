package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestDateRange {
    private DateRange dateRange1, dateRange2, dateRange3;

    @BeforeEach
    void setUp() throws Exception {
        // Setup resources before each test
        this.dateRange1 = new DateRange(LocalDate.of(2019, 1, 7),
                LocalDate.of(2019, 1, 10));
        this.dateRange2 = new DateRange(LocalDate.of(2019, 1, 5),
                LocalDate.of(2019, 1, 23));
        this.dateRange3 = new DateRange(LocalDate.of(2015, 1, 7),
                LocalDate.of(2018, 1, 10));
    }

    // Sample JUnit tests checking toYears works
    @Test
    void testToYears1() {
        assertEquals(0, this.dateRange1.toYears());
    }

    @Test
    void testToYears3() {
        assertEquals(3, this.dateRange3.toYears());
    }

    @Test
    void testOverlapsTrue() {
        assertEquals(true, this.dateRange1.overlaps(dateRange2));
    }

    @Test
    void testOverlapsFalse() {
        assertEquals(false, this.dateRange1.overlaps(dateRange3));
    }

<<<<<<< HEAD
=======
    // TODO: put some of your own unit tests here
    @Test
    void testToDays18() {
        assertEquals(18, this.dateRange2.toDays());
    }

    @Test
    void testToDays3() {
        assertEquals(3, this.dateRange1.toDays());
    }
>>>>>>> b7cf823a18d9f127d8dbc050f0ec32c13dec23c8
}
