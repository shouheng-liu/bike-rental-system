package uk.ac.ed.bikerental;

public class DeliveryServiceFactory {
    private static DeliveryService deliveryServiceInstance;

    public static DeliveryService getDeliveryService() {
        assert deliveryServiceInstance != null;
        return deliveryServiceInstance;
    }

    public static void setupMockDeliveryService() {
        // Should only be called in unit tests, not production code.
        deliveryServiceInstance = new MockDeliveryService();
    }
}
