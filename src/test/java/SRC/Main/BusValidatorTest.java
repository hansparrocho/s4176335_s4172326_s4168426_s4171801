package SRC.Main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BusValidatorTest {

    // B1 - Bus ID Rules

    @Test
    void validBusIDIsAccepted() {
        assertDoesNotThrow(() ->
                BusValidator.validateBusIDFormat("12345678"));
    }

    @Test
    void busIDWithLettersIsRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                BusValidator.validateBusIDFormat("1234ABCD"));
    }

    @Test
    void busIDShorterThan8DigitsIsRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                BusValidator.validateBusIDFormat("1234567"));
    }

    // B2 - Capacity Update Restriction

    @Test
    void capacityCanDecrease() {
        assertDoesNotThrow(() ->
                BusValidator.validateCapacityUpdate(50, 40));
    }

    @Test
    void capacityCannotIncrease() {
        assertThrows(IllegalArgumentException.class, () ->
                BusValidator.validateCapacityUpdate(40, 50));
    }

    @Test
    void capacityCannotBeZero() {
        assertThrows(IllegalArgumentException.class, () ->
                BusValidator.validateCapacityUpdate(40, 0));
    }

    // B3 - Driver Age Restriction

    @Test
    void driverOver50CannotDriveBusCapacity50OrMore() {
        Driver driver = new Driver(
                "23@CD#EFAB",
                "John Doe",
                10,
                "Heavy",
                "67|Flinders Street|Melbourne|VIC|Australia",
                "01-01-1960"
        );

        Bus bus = new Bus("12345678", 50, 80.0, "Diesel");

        assertFalse(BusValidator.canDriverOperateBus(driver, bus));
    }

    @Test
    void driverOver50CanDriveBusCapacityBelow50() {
        Driver driver = new Driver(
                "24@CD#EFAB",
                "Jane Doe",
                10,
                "Heavy",
                "67|Flinders Street|Melbourne|VIC|Australia",
                "01-01-1960"
        );

        Bus bus = new Bus("87654321", 40, 80.0, "Diesel");

        assertTrue(BusValidator.canDriverOperateBus(driver, bus));
    }

    @Test
    void driver50OrYoungerCanDriveLargeBus() {
        Driver driver = new Driver(
                "25@CD#EFAB",
                "Sam Lee",
                10,
                "Heavy",
                "67|Flinders Street|Melbourne|VIC|Australia",
                "01-01-1980"
        );

        Bus bus = new Bus("11223344", 50, 80.0, "Diesel");

        assertTrue(BusValidator.canDriverOperateBus(driver, bus));
    }

    // B4 - Electric Bus Experience Restriction

    @Test
    void electricBusRequiresAtLeast5YearsExperience() {
        Driver driver = new Driver(
                "26@CD#EFAB",
                "Alex Green",
                4,
                "Heavy",
                "67|Flinders Street|Melbourne|VIC|Australia",
                "01-01-1990"
        );

        Bus bus = new Bus("22334455", 40, 80.0, "Electricity");

        assertFalse(BusValidator.canDriverOperateBus(driver, bus));
    }

    @Test
    void electricBusAllowsDriverWith5YearsExperience() {
        Driver driver = new Driver(
                "27@CD#EFAB",
                "Chris White",
                5,
                "Heavy",
                "67|Flinders Street|Melbourne|VIC|Australia",
                "01-01-1990"
        );

        Bus bus = new Bus("33445566", 40, 80.0, "Electricity");

        assertTrue(BusValidator.canDriverOperateBus(driver, bus));
    }

    @Test
    void dieselBusDoesNotRequire5YearsExperience() {
        Driver driver = new Driver(
                "28@CD#EFAB",
                "Taylor Blue",
                2,
                "Light",
                "67|Flinders Street|Melbourne|VIC|Australia",
                "01-01-1990"
        );

        Bus bus = new Bus("44556677", 30, 80.0, "Diesel");

        assertTrue(BusValidator.canDriverOperateBus(driver, bus));
    }

    // B5 - Licence Restriction

    @Test
    void electricBusRequiresHeavyOrPublicTransportLicence() {
        Driver driver = new Driver(
                "29@CD#EFAB",
                "Morgan Red",
                6,
                "Light",
                "67|Flinders Street|Melbourne|VIC|Australia",
                "01-01-1990"
        );

        Bus bus = new Bus("55667788", 40, 80.0, "Electricity");

        assertFalse(BusValidator.canDriverOperateBus(driver, bus));
    }

    @Test
    void hybridBusAllowsHeavyLicence() {
        Driver driver = new Driver(
                "34@CD#EFAB",
                "Jamie Black",
                6,
                "Heavy",
                "67|Flinders Street|Melbourne|VIC|Australia",
                "01-01-1990"
        );

        Bus bus = new Bus("66778899", 40, 80.0, "Hybrid");

        assertTrue(BusValidator.canDriverOperateBus(driver, bus));
    }

    @Test
    void hybridBusAllowsPublicTransportLicence() {
        Driver driver = new Driver(
                "35@CD#EFAB",
                "Casey Orange",
                6,
                "PublicTransport",
                "67|Flinders Street|Melbourne|VIC|Australia",
                "01-01-1990"
        );

        Bus bus = new Bus("77889900", 40, 80.0, "Hybrid");

        assertTrue(BusValidator.canDriverOperateBus(driver, bus));
    }
}