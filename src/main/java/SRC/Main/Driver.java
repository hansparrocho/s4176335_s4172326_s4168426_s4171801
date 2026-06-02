package SRC.Main;

public class Driver {

    private String driverID;
    private String name;
    private int experienceYears;
    private String licenseType;
    private String address;
    private String birthdate;

    public Driver() {
        // Needed for JSON loading
    }

    public Driver(String driverID, String name, int experienceYears,
                  String licenseType, String address, String birthdate) {

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

    public String getDriverID() { return driverID; }
    public String getName() { return name; }
    public int getExperienceYears() { return experienceYears; }
    public String getLicenseType() { return licenseType; }
    public String getAddress() { return address; }
    public String getBirthdate() { return birthdate; }
}