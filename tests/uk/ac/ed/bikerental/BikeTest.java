package uk.ac.ed.bikerental;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BikeTest {

    Location locationDummy = new Location("EH1 1LY", "Cowgate");
    Provider provider = new Provider(locationDummy, "Good bikes", BigDecimal.valueOf(0.2));
    BikeType type = new BikeType("mountainBike", BigDecimal.valueOf(900.0));
    Bike mountainBike = new Bike(BikeTypes.MOUNTAINBIKE, this.provider.getAddress(),
            this.provider.getName());


    /*
    The system throws an error if someone is trying to register a bike of a not yet registered
    bike type.
     */
    @Test
    public void testExistenceType() {

        assertThrows(RuntimeException.class, () ->
        {new Bike(BikeTypes.EBIKE, this.provider.getAddress(),
                this.provider.getName());; });
    }

    @Test
    public void testUniqueID() {
        Bike otherBike = new Bike(BikeTypes.MOUNTAINBIKE, this.provider.getAddress(),
                this.provider.getName());
        assertNotEquals(mountainBike.getIdentifier(), otherBike.getIdentifier());
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