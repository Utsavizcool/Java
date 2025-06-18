// PremiumMember class extending the GymMember abstract class
public class PremiumMember extends GymMember 
{
    // Additional attributes specific to premium members
    private final double premiumCharge;
    private String personalTrainer;
    private boolean isFullPayment;
    private double paidAmount;
    private double discountAmount;

    // Constructor initializing premium member attributes and calling the superclass constructor
    public PremiumMember(int id, String name, String location, String phone, String email, String gender, String DOB, String membershipStartDate,int attendance, double loyaltyPoints, boolean activeStatus,double premiumCharge,String personalTrainer,boolean isFullPayment,double paidAmount,double discountAmount) 
    {
        super(id,  name, location, phone, email, gender, DOB, membershipStartDate,attendance,loyaltyPoints,activeStatus);
        this.premiumCharge = premiumCharge;
        this.personalTrainer = personalTrainer;
        this.isFullPayment = isFullPayment;
        this.paidAmount = paidAmount;
        this.discountAmount = discountAmount;   
    }

    // Getter for premium charge
    public double getPremiumCharge()
    {
        return premiumCharge;
    }

    // Getter for personal trainer
    public String getPersonalTrainer()
    {
        return this.personalTrainer;
    }

    // Getter for payment status
    public boolean getisFullpayment()
    {
        return this.isFullPayment;
    }

    // Getter for paid amount
    public double getPaidAmount()
    {
        return this.paidAmount;
    }

    // Getter for discount amount
    public double getDiscountAmount()
    {
        return this.discountAmount;
    }

    // Method to pay due amount, updates paid amount and payment status
    public String payDueAmount(double paidAmount)
    {
        if (this.isFullPayment == true) 
        {
            return "Full payment has already been made. No due amount remaining.";
        }
        if (paidAmount > premiumCharge) 
        {
            return "Payment exceeds the premium charge. Please check the amount.";
        }
        this.paidAmount += paidAmount;
        if (this.paidAmount == premiumCharge) 
        {
            this.isFullPayment = true;
            this.discountAmount = premiumCharge * 0.10;
            return "Payment successful! Full payment made. Discount of " + discountAmount + " has been applied!";
        } 
        else 
        {
            this.isFullPayment = false;
        }
        double remainingAmount = premiumCharge - this.paidAmount;
        return "Payment successful! Remaining amount to be paid: " + remainingAmount;
    }

    // Method to calculate discount if full payment is made
    public String calculateDiscount()
    {
        if (this.isFullPayment) 
        {
            this.discountAmount = premiumCharge * 0.10;
            return "Discount of " + discountAmount + " has been applied!";
        } 
        else 
        {
            return "Discount cannot be applied. Full payment has not still been made for the user.";
        }
    }

    // Method to calculate and return due amount
    public double getDueAmount() 
    {
        return premiumCharge - paidAmount;
    }

    // Method to return current discount amount
    public double getDiscount() 
    {
        return paidAmount - discountAmount;
    }

    // Method to reset premium member status and financial info
    public void revertPremiumMember(String removalReason)
    {
        super.resetMember();
        this.personalTrainer = "";
        this.isFullPayment = false;
        this.paidAmount = 0;
        this.discountAmount = 0;
    }

    // Method to display premium member details, including financial and trainer info
    public void display()
    {
        super.display();
        System.out.println("Personal Trainer: "+personalTrainer);
        System.out.println("Paid Amount: "+paidAmount);
        System.out.println("Full Paid Amount: "+isFullPayment);
        System.out.println("Reamining Amount: "+getDueAmount());
        
        if(isFullPayment == true)
        {
            System.out.println("Discount Amount: "+discountAmount);
        }
    }

    // Overridden method to mark attendance and add loyalty points, only if membership is active
    @Override
    public void markAttendance() 
    {
        if (activeStatus)
        {
            attendance++;
            loyaltyPoints += 10;
            System.out.println("Attendance marked. Total Attendance: " + attendance + ", Loyalty Points: " + loyaltyPoints);
        } 
        else 
        {
            System.out.println("Cannot mark attendance. Membership is inactive.");
        }
    }
}
