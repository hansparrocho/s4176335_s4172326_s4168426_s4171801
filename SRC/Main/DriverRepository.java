package SRC.Main;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DriverRepository {

    private final Path filePath;
    private final ObjectMapper mapper;

    public DriverRepository(Path filePath) {
        this.filePath = filePath;
        this.mapper = new ObjectMapper();
        initialiseFile();
    }

    public void add(Driver driver) {
        DriverValidator.validateDriverIDFormat(driver.getDriverID());

        List<Driver> drivers = loadAll();

        for (Driver existing : drivers) {
            if (existing.getDriverID().equals(driver.getDriverID())) {
                throw new IllegalArgumentException("Duplicate driverID is not allowed");
            }
        }

        drivers.add(driver);
        saveAll(drivers);
    }

    public Optional<Driver> retrieve(String driverID) {
        List<Driver> drivers = loadAll();

        for (Driver driver : drivers) {
            if (driver.getDriverID().equals(driverID)) {
                return Optional.of(driver);
            }
        }

        return Optional.empty();
    }

    public int count() {
        return loadAll().size();
    }

    public void update(String driverID, Driver updatedDriver) {
        List<Driver> drivers = loadAll();

        for (int i = 0; i < drivers.size(); i++) {
            Driver existing = drivers.get(i);

            if (existing.getDriverID().equals(driverID)) {

                if (!existing.getDriverID().equals(updatedDriver.getDriverID())) {
                    throw new IllegalArgumentException("Driver ID cannot be modified");
                }

                if (!existing.getName().equals(updatedDriver.getName())) {
                    throw new IllegalArgumentException("Driver name cannot be modified");
                }

                if (existing.getExperienceYears() > 10 &&
                        !existing.getLicenseType().equals(updatedDriver.getLicenseType())) {
                    throw new IllegalArgumentException(
                            "Licence type cannot be modified for drivers with more than 10 years of experience");
                }

                DriverValidator.validateAddress(updatedDriver.getAddress());
                DriverValidator.validateBirthdate(updatedDriver.getBirthdate());

                drivers.set(i, updatedDriver);
                saveAll(drivers);
                return;
            }
        }

        throw new IllegalArgumentException("Driver not found: " + driverID);
    }

    private void initialiseFile() {
        try {
            if (filePath.getParent() != null) {
                Files.createDirectories(filePath.getParent());
            }

            if (!Files.exists(filePath)) {
                Files.writeString(filePath, "[]");
            }

        } catch (IOException e) {
            throw new IllegalStateException("Could not initialise driver file", e);
        }
    }

    private List<Driver> loadAll() {
        try {
            if (Files.size(filePath) == 0) {
                return new ArrayList<>();
            }

            return mapper.readValue(
                    filePath.toFile(),
                    new TypeReference<List<Driver>>() {}
            );

        } catch (IOException e) {
            throw new IllegalStateException("Could not read driver file", e);
        }
    }

    private void saveAll(List<Driver> drivers) {
        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(filePath.toFile(), drivers);

        } catch (IOException e) {
            throw new IllegalStateException("Could not write driver file", e);
        }
    }
}