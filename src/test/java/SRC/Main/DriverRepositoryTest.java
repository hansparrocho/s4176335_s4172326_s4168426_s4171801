package SRC.Main;

import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class DriverRepositoryTest {

    @Test
    void validDriverIsStoredAndRetrieved() throws Exception {
        Path tempFile = Files.createTempFile("drivers", ".json");
        DriverRepository repo = new DriverRepository(tempFile);

        Driver driver = new Driver(
                "23@CD#EFAB",
                "John Doe",
                5,
                "Heavy",
                "67|Flinders Street|Melbourne|VIC|Australia",
                "08-12-1967"
        );

        repo.add(driver);

        Driver retrieved = repo.retrieve("23@CD#EFAB").orElse(null);

        assertNotNull(retrieved);
        assertEquals("23@CD#EFAB", retrieved.getDriverID());
        assertEquals("John Doe", retrieved.getName());
    }

    @Test
    void invalidDuplicateDriverIsRejected() throws Exception {
        Path tempFile = Files.createTempFile("drivers", ".json");
        DriverRepository repo = new DriverRepository(tempFile);

        Driver driver = new Driver(
                "23@CD#EFAB",
                "John Doe",
                5,
                "Heavy",
                "67|Flinders Street|Melbourne|VIC|Australia",
                "08-12-1967"
        );

        repo.add(driver);

        assertThrows(IllegalArgumentException.class, () -> {
            repo.add(driver);
        });
    }

    @Test
    void driverUpdateIsPersistedCorrectly() throws Exception {
        Path tempFile = Files.createTempFile("drivers", ".json");
        DriverRepository repo = new DriverRepository(tempFile);

        Driver original = new Driver(
                "23@CD#EFAB",
                "John Doe",
                5,
                "Heavy",
                "67|Flinders Street|Melbourne|VIC|Australia",
                "08-12-1967"
        );

        repo.add(original);

        Driver updated = new Driver(
                "23@CD#EFAB",
                "John Doe",
                5,
                "PublicTransport",
                "69|Bourke Street|Melbourne|VIC|Australia",
                "08-12-1967"
        );

        repo.update("23@CD#EFAB", updated);

        Driver retrieved = repo.retrieve("23@CD#EFAB").orElse(null);

        assertNotNull(retrieved);
        assertEquals("69|Bourke Street|Melbourne|VIC|Australia", retrieved.getAddress());
        assertEquals("PublicTransport", retrieved.getLicenseType());
    }

    @Test
    void driverCountUpdatesCorrectly() throws Exception {
        Path tempFile = Files.createTempFile("drivers", ".json");
        DriverRepository repo = new DriverRepository(tempFile);

        repo.add(new Driver(
                "23@CD#EFAB",
                "John Doe",
                5,
                "Heavy",
                "67|Flinders Street|Melbourne|VIC|Australia",
                "08-12-1967"
        ));

        repo.add(new Driver(
                "45@!Z#GHAB",
                "Jane Smith",
                6,
                "PublicTransport",
                "10|King Street|Melbourne|VIC|Australia",
                "01-01-1980"
        ));

        assertEquals(2, repo.count());
    }
}
