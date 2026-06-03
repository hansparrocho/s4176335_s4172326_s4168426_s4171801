package SRC.Main;

/**
 * Driver class represents a bus driver within the
 * Intelligent Bus Driver Guidance System.
 *
 * This class stores all information related to a driver including:
 * - Driver ID
 * - Driver name
 * - Years of driving experience
 * - Licence type
 * - Address
 * - Birthdate
 *
 * Assignment Requirements:
 * D1 - Driver ID must follow the required format.
 * D2 - Address must follow the specified structure.
 * D3 - Birthdate must be in DD-MM-YYYY format.
 */
public class Driver {

    // Unique identifier for the driver
    private String driverID;

    // Driver's full name
    private String name;

    // Number of years of driving experience
    private int experienceYears;

    // Type of driving licence held by the driver
    private String licenseType;

    // Driver's residential address
    private String address;

    // Driver's date of birth
    private String birthdate;

    /**
     * Default constructor required by Jackson.
     *
     * Jackson uses this constructor when loading
     * Driver objects from JSON files.
     */
    public Driver() {
        // Needed for JSON loading
    }

    /**
     * Constructs a new Driver object.
     *
     * Validates:
     * - Driver ID format (D1)
     * - Address format (D2)
     * - Birthdate format (D3)
     *
     * @param driverID Unique driver identifier
     * @param name Driver's name
     * @param experienceYears Years of driving experience
     * @param licenseType Driver's licence type
     * @param address Driver's address
     * @param birthdate Driver's date of birth
     */
    public Driver(String driverID,
                  String name,
                  int experienceYears,
                  String licenseType,
                  String address,
                  String birthdate) {

        // Validate assignment requirements
        DriverValidator.validateDriverIDFormat(driverID);
        DriverValidator.validateAddress(address);
        DriverValidator.validateBirthdate(birthdate);

        this.driverID = driverID;
        this.name = name;
        this.experienceYears = experienceYears;
        this.licenseType = licenseType;
        this.address = address;
        this.birthdate = birthdate;
    }

    /**
     * Returns the driver's ID.
     *
     * @return Driver ID
     */
    public String getDriverID() {
        return driverID;
    }

    /**
     * Returns the driver's name.
     *
     * @return Driver name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the driver's years of experience.
     *
     * @return Experience years
     */
    public int getExperienceYears() {
        return experienceYears;
    }

    /**
     * Returns the driver's licence type.
     *
     * @return Licence type
     */
    public String getLicenseType() {
        return licenseType;
    }

    /**
     * Returns the driver's address.
     *
     * @return Address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns the driver's birthdate.
     *
     * @return Birthdate
     */
    public String getBirthdate() {
        return birthdate;
    }
}