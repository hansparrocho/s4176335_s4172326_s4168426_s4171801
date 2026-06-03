package SRC.Main;

import java.util.Objects;

/**
 * Bus class represents a bus within the Intelligent Bus Driver Guidance System.
 *
 * This class stores all information related to a bus including:
 * - Bus ID
 * - Passenger capacity
 * - Current fuel level
 * - Fuel type
 *
 * Assignment Requirement:
 * B1 - Bus IDs must be unique and exactly 8 digits.
 */
public class Bus {

    // Unique identifier for the bus
    private String busID;

    // Maximum number of passengers the bus can carry
    private int capacity;

    // Current fuel level represented as a percentage (0-100)
    private double fuelLevel;

    // Type of fuel used by the bus (Diesel, Hybrid, Electricity)
    private String fuelType;

    /**
     * Default constructor required by Jackson.
     *
     * Jackson uses this constructor when loading
     * Bus objects from JSON files.
     */
    public Bus() {
        // Default constructor
    }

    /**
     * Constructs a new Bus object.
     *
     * @param busID Unique bus identifier
     * @param capacity Passenger capacity
     * @param fuelLevel Current fuel level percentage
     * @param fuelType Type of fuel used
     */
    public Bus(String busID, int capacity, double fuelLevel, String fuelType) {

        // Validate Bus ID format before creating object
        validateBusID(busID);

        // Capacity must be greater than zero
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }

        // Fuel level must be within valid percentage range
        if (fuelLevel < 0 || fuelLevel > 100) {
            throw new IllegalArgumentException("Fuel level must be between 0 and 100");
        }

        this.busID = busID;
        this.capacity = capacity;
        this.fuelLevel = fuelLevel;

        // Fuel type cannot be null
        this.fuelType = Objects.requireNonNull(
                fuelType,
                "fuelType is required");
    }

    /**
     * Validates Bus ID according to Assignment Rule B1.
     *
     * Rule:
     * - Must contain exactly 8 digits
     * - Must not be null
     *
     * @param busID Bus ID to validate
     */
    private void validateBusID(String busID) {

        if (busID == null || !busID.matches("\\d{8}")) {
            throw new IllegalArgumentException(
                    "Bus ID must be exactly 8 digits");
        }
    }

    /**
     * Returns the bus ID.
     *
     * @return Bus ID
     */
    public String getBusID() {
        return busID;
    }

    /**
     * Returns passenger capacity.
     *
     * @return Capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Returns current fuel level.
     *
     * @return Fuel level percentage
     */
    public double getFuelLevel() {
        return fuelLevel;
    }

    /**
     * Returns fuel type.
     *
     * @return Fuel type
     */
    public String getFuelType() {
        return fuelType;
    }

    /**
     * Updates bus capacity.
     *
     * Capacity must remain positive.
     *
     * Note:
     * Assignment Rule B2 (capacity cannot increase)
     * is enforced in BusRepository during update operations.
     *
     * @param capacity New capacity value
     */
    public void setCapacity(int capacity) {

        if (capacity <= 0) {
            throw new IllegalArgumentException(
                    "Capacity must be positive");
        }

        this.capacity = capacity;
    }

    /**
     * Updates fuel level.
     *
     * Fuel level must remain between 0 and 100.
     *
     * @param fuelLevel New fuel level
     */
    public void setFuelLevel(double fuelLevel) {

        if (fuelLevel < 0 || fuelLevel > 100) {
            throw new IllegalArgumentException(
                    "Fuel level must be between 0 and 100");
        }

        this.fuelLevel = fuelLevel;
    }

    /**
     * Updates fuel type.
     *
     * Fuel type cannot be null.
     *
     * @param fuelType New fuel type
     */
    public void setFuelType(String fuelType) {

        this.fuelType = Objects.requireNonNull(
                fuelType,
                "fuelType is required");
    }

    /**
     * Internal setter used when loading data from JSON.
     *
     * Not intended for general application use.
     *
     * @param busID Bus ID loaded from file
     */
    void setBusID(String busID) {
        this.busID = busID;
    }
}