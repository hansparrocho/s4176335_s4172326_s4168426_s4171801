package SRC.Main;

/**
 * DriverValidator contains validation methods used
 * to enforce the assignment rules for Driver records.
 *
 * Assignment Requirements:
 * D1 - Driver ID format validation
 * D2 - Address format validation
 * D3 - Birthdate format validation
 */
public class DriverValidator {

    /**
     * Validates the Driver ID according to Rule D1.
     *
     * Requirements:
     * - Exactly 10 characters long
     * - First 2 characters must be digits between 2 and 9
     * - Characters 3-8 must contain at least 2 special characters
     * - Last 2 characters must be uppercase letters
     *
     * @param driverID Driver ID to validate
     */
    public static void validateDriverIDFormat(String driverID) {

        // Driver ID must contain exactly 10 characters
        if (driverID == null || driverID.length() != 10) {
            throw new IllegalArgumentException(
                    "driverID must be exactly 10 characters");
        }

        // First two characters must be digits between 2 and 9
        if (!driverID.substring(0, 2).matches("[2-9]{2}")) {
            throw new IllegalArgumentException(
                    "First two characters must be digits between 2 and 9");
        }

        // Extract characters 3 to 8
        String middle = driverID.substring(2, 8);

        int specialCount = 0;

        // Count the number of special characters
        for (char c : middle.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                specialCount++;
            }
        }

        // At least two special characters are required
        if (specialCount < 2) {
            throw new IllegalArgumentException(
                    "driverID must contain at least two special characters between characters 3 and 8");
        }

        // Last two characters must be uppercase letters
        if (!driverID.substring(8, 10).matches("[A-Z]{2}")) {
            throw new IllegalArgumentException(
                    "Last two characters must be uppercase letters");
        }
    }

    /**
     * Validates address format according to Rule D2.
     *
     * Required format:
     * Street Number|Street Name|City|State|Country
     *
     * Example:
     * 67|Flinders Street|Melbourne|VIC|Australia
     *
     * @param address Address to validate
     */
    public static void validateAddress(String address) {

        // Address cannot be null
        if (address == null) {
            throw new IllegalArgumentException(
                    "Address is required");
        }

        // Split address into components using '|'
        String[] parts = address.split("\\|");

        // Address must contain exactly 5 components
        if (parts.length != 5) {
            throw new IllegalArgumentException(
                    "Address must follow Street Number|Street Name|City|State|Country");
        }

        // Ensure no component is empty
        for (String part : parts) {
            if (part.trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "Address segments cannot be blank");
            }
        }
    }

    /**
     * Validates birthdate format according to Rule D3.
     *
     * Required format:
     * DD-MM-YYYY
     *
     * Example:
     * 08-12-1967
     *
     * @param birthdate Birthdate to validate
     */
    public static void validateBirthdate(String birthdate) {

        // Birthdate must match DD-MM-YYYY format
        if (birthdate == null ||
                !birthdate.matches("\\d{2}-\\d{2}-\\d{4}")) {

            throw new IllegalArgumentException(
                    "Birthdate must be DD-MM-YYYY");
        }
    }
}