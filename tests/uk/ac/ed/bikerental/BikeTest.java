package uk.ac.ed.bikerental;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BikeTest {

    Location locationDummy = new Location("EH1 1LY", "Cowgate");
    BikeType type = new BikeType("mountainBike", BigDecimal.valueOf(100.0));
    Bike mountainBike = new Bike(BikeTypes.from("mountainBike"), locationDummy);

    @Test
    public void testExistenceType() {
        assertThrows(RuntimeException.class, () -> //throw error because biketype wasn't registered
        {new Bike(BikeTypes.from("eBike"), locationDummy); });
    }

    @Test
    void getType() {
        type = Controller.getBikeType(BikeTypes.from("mountainBike"));
        assertEquals(type, mountainBike.getType());

    }

    @Test
    void getManufacturingDate() {
        assertEquals(LocalDate.now(), mountainBike.getManufacturingDate());
    }
}