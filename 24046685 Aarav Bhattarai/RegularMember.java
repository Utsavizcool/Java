// RegularMember class extending the GymMember abstract class
public class RegularMember extends GymMember
{
    // Additional attributes specific to regular members
    private final int attendanceLimit;
    private boolean isEligibleForUpgrade;
    private String removalReason;
    private String referralSource;
    private String plan;
    private double price;
    private double paidAmount;

    // Constructor initializing regular member attributes and calling the superclass constructor
    public RegularMember(int id, String name, String location, String phone, String email, String gender, String DOB, String membershipStartDate, String referralSource,int attendance, double loyaltyPoints, boolean activeStatus,double paidAmount) 
    {
        super(id, name, location, phone, email, gender, DOB, membershipStartDate,attendance,loyaltyPoints,activeStatus);
        this.referralSource = referralSource;
        this.attendanceLimit = 5;
        this.isEligibleForUpgrade = false;
        this.removalReason = "";
        this.plan = "basic";
        this.price = 6500;
    }

    // Getter for attendance limit
    public int getAttendanceLimit() 
    { 
        return attendanceLimit; 
    }

    // Getter for upgrade eligibility status
    public boolean getIsEligibleForUpgrade() 
    {
        return this.isEligibleForUpgrade;
    }

    // Getter for removal reason
    public String getRemovalReason() 
    { 
        return this.removalReason;
    }

    // Getter for referral source
    public String getReferralSource() 
    {
        return this.referralSource;
    }

    // Getter for current plan name
    public String getPlan() 
    {
        return this.plan; 
    }

    // Getter for current plan price
    public double getPrice() 
    { 
        return this.price;
    }

    // Getter for paid amount
    public double getPaidAmount()
    {
        return this.paidAmount;
    }

    // Method to calculate and return due amount
    public double getDueAmount() 
    {
        return this.price - this.paidAmount;
    }

    // Overridden method to mark attendance and update loyalty points
    @Override
    public void markAttendance()
    {
        attendance++;
        loyaltyPoints+=5;
    }

    // Method to get the price of a plan based on its name
    public double getPlanPrice(String plan)
    {
        switch(plan.toLowerCase())
        {
            case "basic":
                return 6500.0;
            case "standard":
                return 12500.0;
            case "deluxe":
                return 18500.0;
            default:
                return -1;
        }
    }

    // Method to upgrade to a new plan if eligible
    public String upgradePlan(String newPlan)
    {
        if(this.attendance >= getAttendanceLimit())
        {
            this.isEligibleForUpgrade = true;
            double newPrice = getPlanPrice(newPlan);
            if (newPrice != -1) 
            {
                this.plan = newPlan;
                this.price = newPrice;
                return "Plan upgraded to " + newPlan + " with price " + newPrice;
            } 
            else 
            {
                return "Invalid plan selected.";
            }
        }
        else
        {
            return "You are not eligible for upgrade!";
        }
    }

    // Method to reset regular member status and update removal reason
    public void revertRegularMember(String removalReason)
    {
        super.resetMember();
        this.isEligibleForUpgrade = false;
        this.plan = "Basic";
        this.price = 6500;
        this.removalReason = removalReason;
    }

    // Method to display regular member details including plan and financial information
    public void display()
    {
        super.display();
        System.out.println("Plan: "+plan);
        System.out.println("Price: "+price);
        System.out.println("Paid Amount: " + paidAmount);
        System.out.println("Due Amount: " + getDueAmount());
        if(removalReason != null)
        {
            System.out.println("Removal Reason: "+removalReason);
        }
    }
}
