package SRC.Main;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DriverRepository manages all Driver records stored
 * in the JSON database file.
 *
 * Responsibilities:
 * - Add drivers
 * - Retrieve drivers
 * - Update drivers
 * - Count drivers
 * - Read and write JSON data
 *
 * Assignment Requirements:
 * D1 - Driver IDs must be unique.
 * D4 - Licence type cannot be modified if
 *      experience exceeds 10 years.
 * D5 - Driver ID and Name cannot be modified.
 */
public class DriverRepository {

    // Location of the JSON database file
    private final Path filePath;

    // Jackson object used for JSON serialization/deserialization
    private final ObjectMapper mapper;

    /**
     * Creates a DriverRepository object and ensures
     * the JSON file exists before use.
     *
     * @param filePath Path to the JSON database file
     */
    public DriverRepository(Path filePath) {
        this.filePath = filePath;
        this.mapper = new ObjectMapper();
        initialiseFile();
    }

    /**
     * Adds a new driver to the repository.
     *
     * Validation:
     * - Driver ID must be valid
     * - Driver ID must be unique
     *
     * @param driver Driver object to add
     */
    public void add(Driver driver) {

        // Validate Driver ID format
        DriverValidator.validateDriverIDFormat(driver.getDriverID());

        List<Driver> drivers = loadAll();

        // Ensure Driver ID is unique (D1)
        for (Driver existing : drivers) {
            if (existing.getDriverID().equals(driver.getDriverID())) {
                throw new IllegalArgumentException(
                        "Duplicate driverID is not allowed");
            }
        }

        drivers.add(driver);
        saveAll(drivers);
    }

    /**
     * Retrieves a driver using the Driver ID.
     *
     * @param driverID Driver ID to search for
     * @return Driver if found, otherwise empty
     */
    public Optional<Driver> retrieve(String driverID) {

        List<Driver> drivers = loadAll();

        for (Driver driver : drivers) {
            if (driver.getDriverID().equals(driverID)) {
                return Optional.of(driver);
            }
        }

        return Optional.empty();
    }

    /**
     * Returns the total number of drivers
     * currently stored in the repository.
     *
     * @return Number of driver records
     */
    public int count() {
        return loadAll().size();
    }

    /**
     * Updates an existing driver record.
     *
     * Assignment Rules:
     * D4 - Drivers with more than 10 years of
     *      experience cannot change licence type.
     *
     * D5 - Driver ID and Name are immutable
     *      and cannot be modified.
     *
     * @param driverID Driver to update
     * @param updatedDriver Updated driver information
     */
    public void update(String driverID, Driver updatedDriver) {

        List<Driver> drivers = loadAll();

        for (int i = 0; i < drivers.size(); i++) {

            Driver existing = drivers.get(i);

            if (existing.getDriverID().equals(driverID)) {

                // D5 - Driver ID cannot change
                if (!existing.getDriverID()
                        .equals(updatedDriver.getDriverID())) {

                    throw new IllegalArgumentException(
                            "Driver ID cannot be modified");
                }

                // D5 - Driver name cannot change
                if (!existing.getName()
                        .equals(updatedDriver.getName())) {

                    throw new IllegalArgumentException(
                            "Driver name cannot be modified");
                }

                // D4 - Licence type cannot change if
                // experience is greater than 10 years
                if (existing.getExperienceYears() > 10 &&
                        !existing.getLicenseType()
                                .equals(updatedDriver.getLicenseType())) {

                    throw new IllegalArgumentException(
                            "Licence type cannot be modified for drivers with more than 10 years of experience");
                }

                // Validate updated data
                DriverValidator.validateAddress(
                        updatedDriver.getAddress());

                DriverValidator.validateBirthdate(
                        updatedDriver.getBirthdate());

                // Save updated driver record
                drivers.set(i, updatedDriver);
                saveAll(drivers);

                return;
            }
        }

        throw new IllegalArgumentException(
                "Driver not found: " + driverID);
    }

    /**
     * Creates the JSON file if it does not exist.
     *
     * Also creates any required directories.
     */
    private void initialiseFile() {

        try {

            if (filePath.getParent() != null) {
                Files.createDirectories(
                        filePath.getParent());
            }

            // Create empty JSON array if file does not exist
            if (!Files.exists(filePath)) {
                Files.writeString(filePath, "[]");
            }

        } catch (IOException e) {

            throw new IllegalStateException(
                    "Could not initialise driver file",
                    e);
        }
    }

    /**
     * Loads all driver records from the JSON file.
     *
     * @return List of drivers
     */
    private List<Driver> loadAll() {

        try {

            // Empty file returns empty list
            if (Files.size(filePath) == 0) {
                return new ArrayList<>();
            }

            return mapper.readValue(
                    filePath.toFile(),
                    new TypeReference<List<Driver>>() {
                    });

        } catch (IOException e) {

            throw new IllegalStateException(
                    "Could not read driver file",
                    e);
        }
    }

    /**
     * Saves all driver records to the JSON file.
     *
     * @param drivers List of drivers to save
     */
    private void saveAll(List<Driver> drivers) {

        try {

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(
                            filePath.toFile(),
                            drivers);

        } catch (IOException e) {

            throw new IllegalStateException(
                    "Could not write driver file",
                    e);
        }
    }
}