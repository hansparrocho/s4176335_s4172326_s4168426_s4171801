package SRC.Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * BusRepository handles storing, retrieving, updating, and counting Bus records.
 *
 * This class uses a human-readable text file as the database.
 * Each bus is stored on one line using this format:
 *
 * busID,capacity,fuelLevel,fuelType
 */
public class BusRepository {

    // Path to the text file used as the bus database
    private final Path filePath;

    /**
     * Constructor receives the file path and makes sure the file exists.
     */
    public BusRepository(Path filePath) {
        this.filePath = filePath;
        initialiseFile();
    }

    /**
     * Adds a new bus to the file.
     *
     * B1 rule:
     * - busID must be unique
     * - busID must be exactly 8 digits
     */
    public void add(Bus bus) {
        validateBusID(bus.getBusID());

        List<Bus> buses = loadAll();

        for (Bus existingBus : buses) {
            if (existingBus.getBusID().equals(bus.getBusID())) {
                throw new IllegalArgumentException("Duplicate busID is not allowed");
            }
        }

        buses.add(bus);
        saveAll(buses);
    }

    /**
     * Retrieves a bus by busID.
     */
    public Optional<Bus> retrieve(String busID) {
        List<Bus> buses = loadAll();

        for (Bus bus : buses) {
            if (bus.getBusID().equals(busID)) {
                return Optional.of(bus);
            }
        }

        return Optional.empty();
    }

    /**
     * Returns the number of stored buses.
     */
    public int count() {
        return loadAll().size();
    }

    /**
     * Updates an existing bus.
     *
     * B2 rule:
     * - bus capacity cannot increase during update
     * - capacity can decrease
     *
     * busID is treated as immutable.
     */
    public void update(String busID, Bus updatedBus) {
        List<Bus> buses = loadAll();

        for (int i = 0; i < buses.size(); i++) {
            Bus existingBus = buses.get(i);

            if (existingBus.getBusID().equals(busID)) {

                if (!existingBus.getBusID().equals(updatedBus.getBusID())) {
                    throw new IllegalArgumentException("busID is immutable and cannot be modified");
                }

                if (updatedBus.getCapacity() > existingBus.getCapacity()) {
                    throw new IllegalArgumentException("Bus capacity cannot increase during update");
                }

                buses.set(i, updatedBus);
                saveAll(buses);
                return;
            }
        }

        throw new IllegalArgumentException("Bus not found: " + busID);
    }

    /**
     * Creates the data file if it does not already exist.
     */
    private void initialiseFile() {
        try {
            if (filePath.getParent() != null) {
                Files.createDirectories(filePath.getParent());
            }

            if (!Files.exists(filePath)) {
                Files.writeString(filePath, "");
            }

        } catch (IOException e) {
            throw new IllegalStateException("Could not initialise bus data file", e);
        }
    }

    /**
     * Loads all bus records from the text file.
     */
    private List<Bus> loadAll() {
        List<Bus> buses = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(filePath);

            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");

                String busID = parts[0];
                int capacity = Integer.parseInt(parts[1]);
                double fuelLevel = Double.parseDouble(parts[2]);
                String fuelType = parts[3];

                buses.add(new Bus(busID, capacity, fuelLevel, fuelType));
            }

            return buses;

        } catch (IOException e) {
            throw new IllegalStateException("Could not read bus data file", e);
        }
    }

    /**
     * Saves all bus records to the text file.
     */
    private void saveAll(List<Bus> buses) {
        List<String> lines = new ArrayList<>();

        for (Bus bus : buses) {
            String line = bus.getBusID() + ","
                    + bus.getCapacity() + ","
                    + bus.getFuelLevel() + ","
                    + bus.getFuelType();

            lines.add(line);
        }

        try {
            Files.write(filePath, lines);
        } catch (IOException e) {
            throw new IllegalStateException("Could not write bus data file", e);
        }
    }

    /**
     * Validates Bus ID according to assignment rule B1.
     *
     * B1:
     * - busID must be exactly 8 characters
     * - all characters must be digits
     */
    private void validateBusID(String busID) {
        if (busID == null || !busID.matches("\\d{8}")) {
            throw new IllegalArgumentException("Bus ID must be exactly 8 digits");
        }
    }
}