// Abstract class representing a generic gym member
abstract class GymMember
{
    // Variable declaration
    protected int id;
    protected String name;
    protected String location;
    protected String phone;
    protected String email;
    protected String gender;
    protected String DOB;
    protected String membershipStartDate;
    protected int attendance;
    protected double loyaltyPoints;
    protected boolean activeStatus;

    // Constructor initializing the member's information and default values for attendance, loyalty points, and active status
    public GymMember(int id, String name, String location, String phone, String email, String gender, String DOB, String membershipStartDate, int attendance, double loyaltyPoints, boolean activestatus) 
    {
        this.id = id;
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.DOB = DOB;
        this.membershipStartDate = membershipStartDate;
        this.attendance = 0;
        this.loyaltyPoints = 0.0;
        this.activeStatus = false;
    }

    // Getter method for member ID
    public int getId()
    {
        return this.id;
    }

    // Getter method for member name
    public String getName()
    {
        return this.name;
    }

    // Getter method for member location
    public String getLocation()
    {
        return this.location;
    }

    // Getter method for member phone number
    public String getPhone()
    {
        return this.phone;
    }

    // Getter method for member email
    public String getEmail()
    {
        return this.email;
    }

    // Getter method for member gender
    public String getGender()
    {
        return this.gender;
    }

    // Getter method for member date of birth
    public String getDOB()
    {
        return this.DOB;
    }

    // Getter method for membership start date
    public String getMembershipStartDate()
    {
        return this.membershipStartDate;
    }

    // Getter method for membership active status
    public boolean getActiveStatus() 
    {
        return this.activeStatus;
    }

    // Getter method for loyalty points
    public double getLoyaltyPoints()
    {
        return this.loyaltyPoints;
    }

    // Getter method for attendance count
    public int getAttendance()
    {
        return this.attendance;
    }

    // Abstract method for marking attendance, to be implemented by subclasses
    public abstract void markAttendance();

    // Method to activate the membership
    public void activateMembership()
    {
        activeStatus = true;
        System.out.println("Membership Activated!");
    }

    // Method to deactivate the membership
    public void deactivateMembership()
    {
        activeStatus = false;
        System.out.println("Membership Deactivated");
    }

    // Method to reset member data to default values
    public void resetMember()
    {
        activeStatus = false;
        attendance = 0;
        loyaltyPoints = 0.0;
        System.out.println("The member has been deactivated & reset!");
    }

    // Method to display all member details
    public void display()
    {
        System.out.println("===== Member Details =====");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Location: " + location);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("Gender: " + gender);
        System.out.println("DOB: " + DOB);
        System.out.println("Membership Start Date: " + membershipStartDate);
        System.out.println("Attendance: "+ attendance);
        System.out.println("Loyalty Points: "+ loyaltyPoints);
        System.out.println("Active Status: "+ activeStatus);
        System.out.println("=================================");
    }
}
