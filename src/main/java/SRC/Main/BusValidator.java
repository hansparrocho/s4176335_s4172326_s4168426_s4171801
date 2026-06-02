package SRC.Main;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class BusValidator {

    /**
     * B1
     * Bus ID must be exactly 8 digits.
     */
    public static void validateBusIDFormat(String busID) {

        if (busID == null || !busID.matches("\\d{8}")) {
            throw new IllegalArgumentException(
                    "Bus ID must be exactly 8 digits");
        }
    }

    /**
     * B2
     * Capacity can decrease but cannot increase.
     */
    public static void validateCapacityUpdate(
            int oldCapacity,
            int newCapacity) {

        if (newCapacity > oldCapacity) {
            throw new IllegalArgumentException(
                    "Bus capacity cannot increase");
        }

        if (newCapacity <= 0) {
            throw new IllegalArgumentException(
                    "Capacity must be positive");
        }
    }

    /**
     * Converts birthdate string (DD-MM-YYYY)
     * into an age.
     */
    private static int calculateAge(String birthdate) {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate birth =
                LocalDate.parse(birthdate, formatter);

        return Period.between(
                birth,
                LocalDate.now()).getYears();
    }

    /**
     * B3, B4, B5
     */
    public static boolean canDriverOperateBus(
            Driver driver,
            Bus bus) {

        int age = calculateAge(driver.getBirthdate());

        // B3
        if (age > 50 && bus.getCapacity() >= 50) {
            return false;
        }

        // B4
        if (bus.getFuelType().equalsIgnoreCase("Electricity")
                && driver.getExperienceYears() < 5) {
            return false;
        }

        // B5
        if (bus.getFuelType().equalsIgnoreCase("Electricity")
                || bus.getFuelType().equalsIgnoreCase("Hybrid")) {

            String licence = driver.getLicenseType();

            if (!licence.equalsIgnoreCase("Heavy")
                    && !licence.equalsIgnoreCase("PublicTransport")) {

                return false;
            }
        }

        return true;
    }

    /**
     * Throws an exception if the driver
     * is not permitted to operate the bus.
     */
    public static void validateDriverCanOperateBus(
            Driver driver,
            Bus bus) {

        if (!canDriverOperateBus(driver, bus)) {

            throw new IllegalArgumentException(
                    "Driver is not permitted to operate this bus");
        }
    }
}