package SRC.Main;

import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class BusRepositoryTest {

    @Test
    void validBusIsStoredAndRetrieved() throws Exception {
        Path tempFile = Files.createTempFile("buses", ".json");
        BusRepository repo = new BusRepository(tempFile);

        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");
        repo.add(bus);

        Bus retrieved = repo.retrieve("12345678").orElse(null);

        assertNotNull(retrieved);
        assertEquals("12345678", retrieved.getBusID());
        assertEquals(40, retrieved.getCapacity());
    }

    @Test
    void invalidDuplicateBusIsRejected() throws Exception {
        Path tempFile = Files.createTempFile("buses", ".json");
        BusRepository repo = new BusRepository(tempFile);

        repo.add(new Bus("12345678", 40, 80.0, "Diesel"));

        assertThrows(IllegalArgumentException.class, () -> {
            repo.add(new Bus("12345678", 30, 60.0, "Hybrid"));
        });
    }

    @Test
    void busUpdateIsPersistedCorrectly() throws Exception {
        Path tempFile = Files.createTempFile("buses", ".json");
        BusRepository repo = new BusRepository(tempFile);

        repo.add(new Bus("12345678", 50, 80.0, "Diesel"));

        Bus updatedBus = new Bus("12345678", 40, 90.0, "Hybrid");
        repo.update("12345678", updatedBus);

        Bus retrieved = repo.retrieve("12345678").orElse(null);

        assertNotNull(retrieved);
        assertEquals(40, retrieved.getCapacity());
        assertEquals("Hybrid", retrieved.getFuelType());
    }

    @Test
    void busCountUpdatesCorrectly() throws Exception {
        Path tempFile = Files.createTempFile("buses", ".json");
        BusRepository repo = new BusRepository(tempFile);

        repo.add(new Bus("12345678", 40, 80.0, "Diesel"));
        repo.add(new Bus("87654321", 30, 70.0, "Hybrid"));

        assertEquals(2, repo.count());
    }
}