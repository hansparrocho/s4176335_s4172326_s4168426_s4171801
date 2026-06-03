package SRC.Main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DriverValidatorTest {

    // D1 - Driver ID Rules

    @Test
    void validDriverIDIsAccepted() {
        assertDoesNotThrow(() ->
                DriverValidator.validateDriverIDFormat("23@CD#EFAB"));
    }

    @Test
    void driverIDShorterThan10IsRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                DriverValidator.validateDriverIDFormat("23@CD#EFA"));
    }

    @Test
    void driverIDWithoutUppercaseEndingIsRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                DriverValidator.validateDriverIDFormat("23@CD#EFab"));
    }

    @Test
    void driverIDWithOnlyOneSpecialCharacterIsRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                DriverValidator.validateDriverIDFormat("23@CDEFGAB"));
    }

    // D2 - Address Format

    @Test
    void validAddressIsAccepted() {
        assertDoesNotThrow(() ->
                DriverValidator.validateAddress(
                        "67|Flinders Street|Melbourne|VIC|Australia"));
    }

    @Test
    void addressWithFourSegmentsIsRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                DriverValidator.validateAddress(
                        "67|Flinders Street|Melbourne|VIC"));
    }

    @Test
    void addressWithBlankSegmentIsRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                DriverValidator.validateAddress(
                        "67|Flinders Street||VIC|Australia"));
    }

    // D3 - Birthdate Format

    @Test
    void validBirthdateIsAccepted() {
        assertDoesNotThrow(() ->
                DriverValidator.validateBirthdate("01-01-1967"));
    }

    @Test
    void birthdateWithSlashesIsRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                DriverValidator.validateBirthdate("01/01/1967"));
    }

    @Test
    void birthdateWithWrongOrderIsRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                DriverValidator.validateBirthdate("1967-01-01"));
    }

    // D4 - License Update Restriction

    @Test
    void driverWithFiveYearsCanChangeLicence() {
        DriverRepository repo =
                new DriverRepository(java.nio.file.Path.of("test-drivers.json"));

        Driver original = new Driver(
                "23@CD#EFAB",
                "John Doe",
                5,
                "Heavy",
                "67|Flinders Street|Melbourne|VIC|Australia",
                "08-12-1967"
        );

        Driver updated = new Driver(
                "23@CD#EFAB",
                "John Doe",
                5,
                "PublicTransport",
                "67|Flinders Street|Melbourne|VIC|Australia",
                "08-12-1967"
        );

        repo.add(original);

        assertDoesNotThrow(() ->
                repo.update("23@CD#EFAB", updated));
    }

    @Test
    void driverWithMoreThan10YearsCannotChangeLicence() {
        DriverRepository repo =
                new DriverRepository(java.nio.file.Path.of("test-drivers2.json"));

        Driver original = new Driver(
                "45@CD#EFAB",
                "Jane Smith",
                11,
                "Heavy",
                "67|Flinders Street|Melbourne|VIC|Australia",
                "08-12-1967"
        );

        Driver updated = new Driver(
                "45@CD#EFAB",
                "Jane Smith",
                11,
                "Light",
                "67|Flinders Street|Melbourne|VIC|Australia",
                "08-12-1967"
        );

        repo.add(original);

        assertThrows(IllegalArgumentException.class, () ->
                repo.update("45@CD#EFAB", updated));
    }

    @Test
    void driverWithExactly10YearsCanChangeLicence() {
        DriverRepository repo =
                new DriverRepository(java.nio.file.Path.of("test-drivers3.json"));

        Driver original = new Driver(
                "67@CD#EFAB",
                "Sam Lee",
                10,
                "Heavy",
                "67|Flinders Street|Melbourne|VIC|Australia",
                "08-12-1967"
        );

        Driver updated = new Driver(
                "67@CD#EFAB",
                "Sam Lee",
                10,
                "PublicTransport",
                "67|Flinders Street|Melbourne|VIC|Australia",
                "08-12-1967"
        );

        repo.add(original);

        assertDoesNotThrow(() ->
                repo.update("67@CD#EFAB", updated));
    }

    // D5 - Immutable Fields

    @Test
    void driverIDCannotBeChangedDuringUpdate() {
        DriverRepository repo =
                new DriverRepository(java.nio.file.Path.of("test-drivers4.json"));

        Driver original = new Driver(
                "78@CD#EFAB",
                "Tom Brown",
                5,
                "Heavy",
                "67|Flinders Street|Melbourne|VIC|Australia",
                "08-12-1967"
        );

        Driver updated = new Driver(
                "89@CD#EFAB",
                "Tom Brown",
                5,
                "Heavy",
                "67|Flinders Street|Melbourne|VIC|Australia",
                "08-12-1967"
        );

        repo.add(original);

        assertThrows(IllegalArgumentException.class, () ->
                repo.update("78@CD#EFAB", updated));
    }

    @Test
    void driverNameCannotBeChangedDuringUpdate() {
        DriverRepository repo =
                new DriverRepository(java.nio.file.Path.of("test-drivers5.json"));

        Driver original = new Driver(
                "79@CD#EFAB",
                "Original Name",
                5,
                "Heavy",
                "67|Flinders Street|Melbourne|VIC|Australia",
                "08-12-1967"
        );

        Driver updated = new Driver(
                "79@CD#EFAB",
                "Changed Name",
                5,
                "Heavy",
                "67|Flinders Street|Melbourne|VIC|Australia",
                "08-12-1967"
        );

        repo.add(original);

        assertThrows(IllegalArgumentException.class, () ->
                repo.update("79@CD#EFAB", updated));
    }

    @Test
    void mutableDriverFieldsCanBeUpdated() {
        DriverRepository repo =
                new DriverRepository(java.nio.file.Path.of("test-drivers6.json"));

        Driver original = new Driver(
                "89@CD#EFAB",
                "Alex Green",
                5,
                "Heavy",
                "67|Flinders Street|Melbourne|VIC|Australia",
                "08-12-1967"
        );

        Driver updated = new Driver(
                "89@CD#EFAB",
                "Alex Green",
                5,
                "Heavy",
                "69|Bourke Street|Melbourne|VIC|Australia",
                "08-12-1967"
        );

        repo.add(original);
        repo.update("89@CD#EFAB", updated);

        Driver retrieved = repo.retrieve("89@CD#EFAB").orElse(null);

        assertNotNull(retrieved);
        assertEquals("69|Bourke Street|Melbourne|VIC|Australia",
                retrieved.getAddress());
    }
}