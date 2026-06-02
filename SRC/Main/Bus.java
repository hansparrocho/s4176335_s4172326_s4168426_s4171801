package SRC.Main;

import java.util.Objects;

/**
 * Bus class represents a bus in the Intelligent Bus Driver Guidance System.
 */
public class Bus {

    private String busID;
    private int capacity;
    private double fuelLevel;
    private String fuelType;

    public Bus() {
        // Default constructor
    }

    public Bus(String busID, int capacity, double fuelLevel, String fuelType) {
        validateBusID(busID);

        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }

        if (fuelLevel < 0 || fuelLevel > 100) {
            throw new IllegalArgumentException("Fuel level must be between 0 and 100");
        }

        this.busID = busID;
        this.capacity = capacity;
        this.fuelLevel = fuelLevel;
        this.fuelType = Objects.requireNonNull(fuelType, "fuelType is required");
    }

    private void validateBusID(String busID) {
        if (busID == null || !busID.matches("\\d{8}")) {
            throw new IllegalArgumentException("Bus ID must be exactly 8 digits");
        }
    }

    public String getBusID() {
        return busID;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getFuelLevel() {
        return fuelLevel;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setCapacity(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }

        this.capacity = capacity;
    }

    public void setFuelLevel(double fuelLevel) {
        if (fuelLevel < 0 || fuelLevel > 100) {
            throw new IllegalArgumentException("Fuel level must be between 0 and 100");
        }

        this.fuelLevel = fuelLevel;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = Objects.requireNonNull(fuelType, "fuelType is required");
    }

    void setBusID(String busID) {
        this.busID = busID;
    }
}