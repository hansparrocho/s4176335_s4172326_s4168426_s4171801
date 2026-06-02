package SRC.Main;

public class DriverValidator {

    public static void validateDriverIDFormat(String driverID) {
        if (driverID == null || driverID.length() != 10) {
            throw new IllegalArgumentException("driverID must be exactly 10 characters");
        }

        if (!driverID.substring(0, 2).matches("[2-9]{2}")) {
            throw new IllegalArgumentException("First two characters must be digits between 2 and 9");
        }

        String middle = driverID.substring(2, 8);
        int specialCount = 0;

        for (char c : middle.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                specialCount++;
            }
        }

        if (specialCount < 2) {
            throw new IllegalArgumentException("driverID must contain at least two special characters between characters 3 and 8");
        }

        if (!driverID.substring(8, 10).matches("[A-Z]{2}")) {
            throw new IllegalArgumentException("Last two characters must be uppercase letters");
        }
    }

    public static void validateAddress(String address) {
        if (address == null) {
            throw new IllegalArgumentException("Address is required");
        }

        String[] parts = address.split("\\|");

        if (parts.length != 5) {
            throw new IllegalArgumentException("Address must follow Street Number|Street Name|City|State|Country");
        }

        for (String part : parts) {
            if (part.trim().isEmpty()) {
                throw new IllegalArgumentException("Address segments cannot be blank");
            }
        }
    }

    public static void validateBirthdate(String birthdate) {
        if (birthdate == null || !birthdate.matches("\\d{2}-\\d{2}-\\d{4}")) {
            throw new IllegalArgumentException("Birthdate must be DD-MM-YYYY");
        }
    }
}