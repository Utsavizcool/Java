import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import java.io.*;

public class GymGUI implements ActionListener 
{
    // === Main Data Structure ===
    ArrayList<GymMember> arrayList = new ArrayList<>(); // Stores all member records
    //generic array list 

    // === Main GUI Frames ===
    private JFrame frame, frameRegular, framePremium;

    // === Tab Container ===
    private JTabbedPane pane;

    // === Main Panels ===
    private JPanel regularMemberPanel, premiumMemberPanel, ActionsPanel;
    private JPanel inputPanel, inputPanelPremium, inputPanelActions;

    // === Labels (Regular & Shared) ===
    private JLabel headlabel, idLabel, nameLabel, locationLabel, phoneLabel, emailLabel;
    private JLabel genderLabel, dobLabel, membershipStartDateLabel, referralLabel, planLabel;
    private JLabel RegistrationPriceLabel, PremiumPriceLabel, DiscountLabel, reasonLabel;
    private JLabel regularIdLabel, premiumIdLabel, idLabelRight, nameLabelRight;
    private JLabel removalReasonLabel;

    // === Labels (Premium Only) ===
    private JLabel idLabelPremium, nameLabelPremium, locationLabelPremium, phoneLabelPremium, emailLabelPremium;
    private JLabel genderLabelPremium, dobLabelPremium, membershipStartDateLabelPremium, trainerLabel;

    // === Text Fields (Regular & Shared) ===
    private JTextField idField, nameField, locationField, phoneField, emailField;
    private JTextField referralField, RegPrice;

    // === Text Fields (Premium) ===
    private JTextField idFieldPremium, nameFieldPremium, locationFieldPremium, phoneFieldPremium, emailFieldPremium;
    private JTextField trainerField, paidAmountFieldPremium, PremPrice, Discount;

    // === Text Fields (Actions Tab) ===
    private JTextField regularIdField, premiumIdField, idTextFieldRight, nameTextFieldRight;
    private JTextField payAmountField;
    private JTextField removalReasonTextField;

    // === Combo Boxes (Regular) ===
    private JComboBox<String> dobYear, dobMonth, dobDay;                    // DOB for Regular
    private JComboBox<String> memberStartYear, memberStartMonth, memberStartDay; // Membership Start for Regular
    private JComboBox<String> plan; // Plan selection

    // === Combo Boxes (Premium) ===
    private JComboBox<String> dobYearPremium, dobMonthPremium, dobDayPremium; // DOB for Premium
    private JComboBox<String> memberStartYearPremium, memberStartMonthPremium, memberStartDayPremium; // Membership Start for Premium

    // === Radio Buttons (Gender Selection) ===
    private JRadioButton male, female;                 // Regular
    private JRadioButton malePremium, femalePremium;   // Premium

    // === Buttons (Regular Member Tab) ===
    private JButton addRegular, revertRegular, Clear, displayRegular;

    // === Buttons (Premium Member Tab) ===
    private JButton addPremium, revertPremium, ClearPremium, displayPremium;

    // === Buttons (Actions Tab) ===
    private JButton activateMembershipActions, revertRegularActions, revertPremiumActions;
    private JButton markAttendanceActions, clearActions, displayActions;
    private JButton upgradePlanActions, revertRegularPanelButton;

    // === Buttons (Extended Functionality) ===
    private JButton saveToFileButton, readFromFileButton;
    private JButton calculateDiscountButton;
    private JButton payDueButton;

    // === Other Variables ===
    private double paidAmount;
    private double discount;
    public GymGUI()
    {
        // === Frame Setup ===
        frame = new JFrame("Gym Management System"); // Set frame title
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close app on exit
        frame.setSize(900, 600); // Frame size
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setLayout(null); // Absolute positioning
        frame.getContentPane().setBackground(new Color(204, 255, 229)); // Light green background
    
        // === Title Label Setup ===
        JLabel headLabel = new JLabel("GYM MEMBERSHIP DATA ENTRY"); // Main header
        headLabel.setBounds(280, 10, 900, 50); // Position and size
        Font font = new Font("Arial", Font.BOLD, 28); // Set font style and size
        headLabel.setFont(font);
        headLabel.setForeground(new Color(0, 102, 204)); // Dark blue color
        frame.add(headLabel);
    
        // === Tabbed Pane Setup ===
        pane = new JTabbedPane(); // Create tabbed pane
        pane.setBounds(10, 80, 860, 480); // Position and size
        frame.add(pane); // Add tabbed pane to frame
    
        // === Initialize Tabs ===
        setupRegularMemberTab();  // Sets up Regular Member tab panel and its UI
        setupPremiumMemberTab();  // Sets up Premium Member tab panel and its UI
        setupActionsPanel();      // Sets up Action tab panel and its UI
    
        // === Add Tabs to Tabbed Pane ===
        pane.addTab("Regular Member", regularMemberPanel);
        pane.addTab("Premium Member", premiumMemberPanel);
        pane.addTab("Action", ActionsPanel);
    
        // === Add Action Listeners ===
        // Regular Tab buttons
        addRegular.addActionListener(this);      
        Clear.addActionListener(this);          
        displayRegular.addActionListener(this); 
    
        // Premium Tab buttons
        ClearPremium.addActionListener(this);
        addPremium.addActionListener(this);
        revertPremium.addActionListener(this);
        displayPremium.addActionListener(this);
    
        // Actions Tab buttons
        activateMembershipActions.addActionListener(this);
        revertPremiumActions.addActionListener(this);
        markAttendanceActions.addActionListener(this);
        clearActions.addActionListener(this);
        displayActions.addActionListener(this);
        revertRegular.addActionListener(this);
        
        // === Display the GUI ===
        frame.setVisible(true);
    }


    public void setupRegularMemberTab()
    {
        // === Main Regular Member Panel ===
        regularMemberPanel = new JPanel();
        regularMemberPanel.setLayout(null);
        regularMemberPanel.setBackground(new Color(204, 255, 255));  // Light blue background
    
        // === Inner Input Panel for Regular Member Tab ===
        inputPanel = new JPanel();
        inputPanel.setLayout(null);
        inputPanel.setBounds(10, 10, 833, 430); // Slightly smaller panel inside main panel
        inputPanel.setBackground(new Color(255, 255, 204)); // Light yellow background
        regularMemberPanel.add(inputPanel);
    
        // === Name Label and Text Field ===
        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 30, 150, 30);
        inputPanel.add(nameLabel);
    
        nameField = new JTextField();
        nameField.setBounds(220, 30, 250, 30);
        inputPanel.add(nameField);
    
        // === Phone Label and Text Field ===
        phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(50, 70, 150, 30);
        inputPanel.add(phoneLabel);
    
        phoneField = new JTextField();
        phoneField.setBounds(220, 70, 250, 30);
        inputPanel.add(phoneField);
    
        // === Gender Label and Radio Buttons ===
        genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(500, 30, 150, 30);
        inputPanel.add(genderLabel);
    
        male = new JRadioButton("Male");
        male.setBounds(660, 30, 80, 30);
        female = new JRadioButton("Female");
        female.setBounds(750, 30, 80, 30);
    
        inputPanel.add(male);
        inputPanel.add(female);
    
        // Group gender radio buttons so only one can be selected at a time
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);
    
        // === Referral Source Label and Text Field ===
        referralLabel = new JLabel("Referral Source:");
        referralLabel.setBounds(500, 70, 150, 30);
        inputPanel.add(referralLabel);
    
        referralField = new JTextField();
        referralField.setBounds(660, 70, 150, 30);
        inputPanel.add(referralField);
    
        // === ID Label and Text Field ===
        idLabel = new JLabel("ID:");
        idLabel.setBounds(50, 110, 150, 30);
        inputPanel.add(idLabel);
    
        idField = new JTextField();
        idField.setBounds(220, 110, 250, 30);
        inputPanel.add(idField);
    
        // === Plan Label and Combo Box ===
        planLabel = new JLabel("Plan:");
        planLabel.setBounds(50, 150, 150, 30);
        inputPanel.add(planLabel);
    
        plan = new JComboBox<>(new String[]{"Basic", "Standard", "Deluxe"});
        plan.setBounds(220, 150, 250, 30);
        inputPanel.add(plan);
    
        // === Email Label and Text Field ===
        emailLabel = new JLabel("Email:");
        emailLabel.setBounds(500, 120, 150, 30);
        inputPanel.add(emailLabel);
    
        emailField = new JTextField();
        emailField.setBounds(660, 120, 150, 30);
        inputPanel.add(emailField);
    
        // === Location Label and Text Field ===
        locationLabel = new JLabel("Location:");
        locationLabel.setBounds(500, 170, 150, 30);
        inputPanel.add(locationLabel);
    
        locationField = new JTextField();
        locationField.setBounds(660, 170, 150, 30);
        inputPanel.add(locationField);
    
        // === Date of Birth Label and Combo Boxes (Year, Month, Day) ===
        dobLabel = new JLabel("Date of Birth:");
        dobLabel.setBounds(50, 190, 150, 30);
        inputPanel.add(dobLabel);
    
        dobYear = new JComboBox<>(new String[]{
            "2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015", "2014",
            "2013", "2012", "2011", "2010", "2009", "2008", "2007", "2006", "2005", "2004",
            "2003", "2002", "2001", "2000", "1999"
        });
        dobYear.setBounds(220, 190, 80, 30);
        inputPanel.add(dobYear);
    
        dobMonth = new JComboBox<>(new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        });
        dobMonth.setBounds(310, 190, 80, 30);
        inputPanel.add(dobMonth);
    
        dobDay = new JComboBox<>(new String[]{
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28",
            "29", "30", "31"
        });
        dobDay.setBounds(400, 190, 80, 30);
        inputPanel.add(dobDay);
    
        // === Membership Start Date Label and Combo Boxes (Year, Month, Day) ===
        membershipStartDateLabel = new JLabel("Membership Start:");
        membershipStartDateLabel.setBounds(50, 230, 150, 30);
        inputPanel.add(membershipStartDateLabel);
    
        memberStartYear = new JComboBox<>(new String[]{
            "2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015", "2014",
            "2013", "2012", "2011", "2010", "2009", "2008", "2007", "2006", "2005", "2004",
            "2003", "2002", "2001", "2000", "1999"
        });
        memberStartYear.setBounds(220, 230, 80, 30);
        inputPanel.add(memberStartYear);
    
        memberStartMonth = new JComboBox<>(new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        });
        memberStartMonth.setBounds(310, 230, 80, 30);
        inputPanel.add(memberStartMonth);
    
        memberStartDay = new JComboBox<>(new String[]{
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28",
            "29", "30", "31"
        });
        memberStartDay.setBounds(400, 230, 80, 30);
        inputPanel.add(memberStartDay);
    
        // === Regular Plan Price Label and Non-Editable Text Field ===
        RegistrationPriceLabel = new JLabel("Regular Plan Price:");
        RegistrationPriceLabel.setBounds(50, 270, 150, 30);
        inputPanel.add(RegistrationPriceLabel);
    
        RegPrice = new JTextField("6500");
        RegPrice.setBounds(220, 270, 100, 30);
        RegPrice.setEditable(false);
        inputPanel.add(RegPrice);
    
        // === Buttons: Clear, Add Regular Member, Revert Regular Member, Display Member ===
        Clear = new JButton("Clear");
        Clear.setBounds(570, 250, 200, 30);
        inputPanel.add(Clear);
    
        addRegular = new JButton("Add Regular Member");
        addRegular.setBounds(210, 350, 200, 30);
        inputPanel.add(addRegular);
    
        revertRegular = new JButton("Revert Regular Member");
        revertRegular.setBounds(570, 310, 200, 30);
        inputPanel.add(revertRegular);
    
        displayRegular = new JButton("Display Member");
        displayRegular.setBounds(570, 360, 200, 30);
        inputPanel.add(displayRegular);
    }


    public void setupPremiumMemberTab()
    {
        // === Main Premium Member Panel ===
        premiumMemberPanel = new JPanel();
        premiumMemberPanel.setLayout(null);  // Using absolute layout
        premiumMemberPanel.setBackground(new Color(204, 255, 204));  // Light green background
    
        // === Inner Input Panel for Premium Member Tab ===
        inputPanelPremium = new JPanel();
        inputPanelPremium.setLayout(null);  // Absolute layout for form-style design
        inputPanelPremium.setBounds(10, 10, 833, 430);  // Positioned within main panel
        inputPanelPremium.setBackground(new Color(255, 235, 204));  // Light orange/yellow background
        premiumMemberPanel.add(inputPanelPremium);
    
        // === Name Label and Text Field ===
        nameLabelPremium = new JLabel("Name:");
        nameLabelPremium.setBounds(50, 30, 150, 30);
        inputPanelPremium.add(nameLabelPremium);
        
        nameFieldPremium = new JTextField();
        nameFieldPremium.setBounds(220, 30, 250, 30);
        inputPanelPremium.add(nameFieldPremium);
    
        // === Phone Label and Text Field ===
        phoneLabelPremium = new JLabel("Phone:");
        phoneLabelPremium.setBounds(50, 70, 150, 30);
        inputPanelPremium.add(phoneLabelPremium);
    
        phoneFieldPremium = new JTextField();
        phoneFieldPremium.setBounds(220, 70, 250, 30);
        inputPanelPremium.add(phoneFieldPremium);
    
        // === Gender Label and Radio Buttons ===
        genderLabelPremium = new JLabel("Gender:");
        genderLabelPremium.setBounds(500, 30, 150, 30);
        inputPanelPremium.add(genderLabelPremium);
    
        malePremium = new JRadioButton("Male");
        malePremium.setBounds(660, 30, 80, 30);
        femalePremium = new JRadioButton("Female");
        femalePremium.setBounds(750, 30, 80, 30);
        inputPanelPremium.add(malePremium);
        inputPanelPremium.add(femalePremium);
    
        // Grouping gender radio buttons
        ButtonGroup genderGroupPremium = new ButtonGroup();
        genderGroupPremium.add(malePremium);
        genderGroupPremium.add(femalePremium);
    
        // === Trainer Label and Text Field ===
        trainerLabel = new JLabel("Trainer:");
        trainerLabel.setBounds(500, 90, 150, 30);
        inputPanelPremium.add(trainerLabel);
    
        trainerField = new JTextField();
        trainerField.setBounds(660, 90, 150, 30);
        inputPanelPremium.add(trainerField);
    
        // === ID Label and Text Field ===
        idLabelPremium = new JLabel("ID:");
        idLabelPremium.setBounds(50, 110, 150, 30);
        inputPanelPremium.add(idLabelPremium);
    
        idFieldPremium = new JTextField();
        idFieldPremium.setBounds(220, 110, 250, 30);
        inputPanelPremium.add(idFieldPremium);
    
        // === Membership Start Date (Year, Month, Day) ===
        membershipStartDateLabelPremium = new JLabel("Membership Start:");
        membershipStartDateLabelPremium.setBounds(50, 150, 150, 30);
        inputPanelPremium.add(membershipStartDateLabelPremium);
    
        memberStartYearPremium = new JComboBox<>(new String[]{
            "2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015", "2014",
            "2013", "2012", "2011", "2010", "2009", "2008", "2007", "2006", "2005", "2004",
            "2003", "2002", "2001", "2000", "1999"
        });
        memberStartYearPremium.setBounds(220, 150, 80, 30);
        inputPanelPremium.add(memberStartYearPremium);
    
        memberStartMonthPremium = new JComboBox<>(new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        });
        memberStartMonthPremium.setBounds(310, 150, 80, 30);
        inputPanelPremium.add(memberStartMonthPremium);
    
        memberStartDayPremium = new JComboBox<>(new String[]{
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", 
            "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", 
            "29", "30", "31"
        });
        memberStartDayPremium.setBounds(400, 150, 80, 30);
        inputPanelPremium.add(memberStartDayPremium);
    
        // === Email Label and Text Field ===
        emailLabelPremium = new JLabel("Email:");
        emailLabelPremium.setBounds(500, 140, 150, 30);
        inputPanelPremium.add(emailLabelPremium);
    
        emailFieldPremium = new JTextField();
        emailFieldPremium.setBounds(660, 140, 150, 30);
        inputPanelPremium.add(emailFieldPremium);
    
        // === Location Label and Text Field ===
        locationLabelPremium = new JLabel("Location:");
        locationLabelPremium.setBounds(500, 190, 150, 30);
        inputPanelPremium.add(locationLabelPremium);
    
        locationFieldPremium = new JTextField();
        locationFieldPremium.setBounds(660, 190, 150, 30);
        inputPanelPremium.add(locationFieldPremium);
    
        // === Date of Birth (Year, Month, Day) ===
        dobLabelPremium = new JLabel("Date of Birth:");
        dobLabelPremium.setBounds(50, 190, 150, 30);
        inputPanelPremium.add(dobLabelPremium);
    
        dobYearPremium = new JComboBox<>(new String[]{
            "2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015", "2014",
            "2013", "2012", "2011", "2010", "2009", "2008", "2007", "2006", "2005", "2004",
            "2003", "2002", "2001", "2000", "1999"
        });
        dobYearPremium.setBounds(220, 190, 80, 30);
        inputPanelPremium.add(dobYearPremium);
    
        dobMonthPremium = new JComboBox<>(new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        });
        dobMonthPremium.setBounds(310, 190, 80, 30);
        inputPanelPremium.add(dobMonthPremium);
    
        dobDayPremium = new JComboBox<>(new String[]{
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28",
            "29", "30", "31"
        });
        dobDayPremium.setBounds(400, 190, 80, 30);
        inputPanelPremium.add(dobDayPremium);
    
        // === Premium Plan Charge (non-editable) ===
        PremiumPriceLabel = new JLabel("Premium Plan Charge:");
        PremiumPriceLabel.setBounds(50, 230, 150, 30);
        inputPanelPremium.add(PremiumPriceLabel);
    
        PremPrice = new JTextField("50000");  // Fixed amount
        PremPrice.setBounds(220, 230, 100, 30);
        PremPrice.setEditable(false);  // Not editable
        inputPanelPremium.add(PremPrice);
    
        // === Discount (non-editable) ===
        DiscountLabel = new JLabel("Discount:");
        DiscountLabel.setBounds(50, 270, 150, 30);
        inputPanelPremium.add(DiscountLabel);
    
        Discount = new JTextField("10%");  // Fixed discount
        Discount.setBounds(220, 270, 100, 30);
        Discount.setEditable(false);  // Not editable
        inputPanelPremium.add(Discount);
    
        // === Paid Amount Text Field ===
        JLabel paidAmountLabelPremium = new JLabel("Paid Amount:");
        paidAmountLabelPremium.setBounds(50, 310, 150, 30);
        inputPanelPremium.add(paidAmountLabelPremium);
    
        paidAmountFieldPremium = new JTextField();
        paidAmountFieldPremium.setBounds(220, 310, 100, 30);
        inputPanelPremium.add(paidAmountFieldPremium);
    
        // === Action Buttons ===
        ClearPremium = new JButton("Clear");
        ClearPremium.setBounds(570, 250, 200, 30);
        inputPanelPremium.add(ClearPremium);
    
        addPremium = new JButton("Add Premium Member");
        addPremium.setBounds(220, 350, 200, 30);
        inputPanelPremium.add(addPremium);
    
        revertPremium = new JButton("Revert Premium Member");
        revertPremium.setBounds(570, 310, 200, 30);
        inputPanelPremium.add(revertPremium);
    
        displayPremium = new JButton("Display Member");
        displayPremium.setBounds(570, 360, 200, 30);
        inputPanelPremium.add(displayPremium);
    }




    public void setupActionsPanel() 
    {
        // === Outer Panel ===
        ActionsPanel = new JPanel();
        ActionsPanel.setLayout(null);
        ActionsPanel.setBackground(new Color(255, 235, 234));  // Soft pink background
        ActionsPanel.setBounds(0, 0, 860, 500); // Position and size of main panel
    
        // === Inner Content Panel ===
        inputPanelActions = new JPanel();
        inputPanelActions.setLayout(null);
        inputPanelActions.setBounds(10, 10, 833, 460); // Slightly smaller panel inside outer panel
        inputPanelActions.setBackground(new Color(255, 235, 234)); // Same background color
    
        // === Left Bordered Panel ===
        JPanel leftBox = new JPanel();
        leftBox.setLayout(null);
        leftBox.setBounds(30, 20, 370, 400); // Position and size of left box panel
        leftBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Black border with thickness 2
        leftBox.setBackground(new Color(255, 225, 204)); // Light orange background for left box
    
        // Label and text field for Regular Member ID input
        regularIdLabel = new JLabel("Regular ID:");
        regularIdLabel.setBounds(20, 30, 80, 25);
        leftBox.add(regularIdLabel);
    
        regularIdField = new JTextField();
        regularIdField.setBounds(110, 30, 220, 25);
        leftBox.add(regularIdField);
    
        // Label and text field for Premium Member ID input
        premiumIdLabel = new JLabel("Premuim ID:");
        premiumIdLabel.setBounds(20, 80, 80, 25);
        leftBox.add(premiumIdLabel);
    
        premiumIdField = new JTextField();
        premiumIdField.setBounds(110, 80, 220, 25);
        leftBox.add(premiumIdField);
    
        // Label and text field for entering removal reason
        removalReasonLabel = new JLabel("Removal Reason:");
        removalReasonLabel.setBounds(20, 360, 120, 25);
        leftBox.add(removalReasonLabel);
    
        removalReasonTextField = new JTextField();
        removalReasonTextField.setBounds(150, 360, 180, 25);
        leftBox.add(removalReasonTextField);
    
        // Button to activate membership
        activateMembershipActions = new JButton("Activate Membership");
        activateMembershipActions.setBounds(20, 160, 160, 35);
        leftBox.add(activateMembershipActions);
    
        // Button to deactivate membership (comment indicates this)
        revertRegularActions = new JButton("Deactivate Membership");
        revertRegularActions.setBounds(190, 160, 170, 35);
        leftBox.add(revertRegularActions);
    
        // Button to revert premium member status
        revertPremiumActions = new JButton("Revert Premium");
        revertPremiumActions.setBounds(20, 220, 160, 35);
        leftBox.add(revertPremiumActions);
    
        // Button to revert regular member status (comment indicates this)
        revertRegularPanelButton = new JButton("Revert Regular");
        revertRegularPanelButton.setBounds(190, 220, 160, 35);
        leftBox.add(revertRegularPanelButton);
    
        // Button to display member details
        displayActions = new JButton("Display");
        displayActions.setBounds(100, 320, 150, 35);
        leftBox.add(displayActions);
    
        // Button to upgrade membership plan
        upgradePlanActions = new JButton("Upgrade Plan");
        upgradePlanActions.setBounds(40, 270, 140, 35);
        leftBox.add(upgradePlanActions);
    
        // Button to calculate discount
        calculateDiscountButton = new JButton("Calculate Discount");
        calculateDiscountButton.setBounds(190, 270, 160, 35); 
        leftBox.add(calculateDiscountButton);
    
        // === Right Bordered Panel ===
        JPanel rightBox = new JPanel();
        rightBox.setLayout(null);
        rightBox.setBounds(430, 20, 370, 400); // Position and size of right box panel
        rightBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Black border
        rightBox.setBackground(new Color(255, 225, 204)); // Light orange background
    
        // Label and text field for ID input on the right side
        idLabelRight = new JLabel("ID:");
        idLabelRight.setBounds(20, 30, 80, 25);
        rightBox.add(idLabelRight);
    
        idTextFieldRight = new JTextField();
        idTextFieldRight.setBounds(110, 30, 220, 25);
        rightBox.add(idTextFieldRight);
    
        // Label and text field for Name input on the right side
        nameLabelRight = new JLabel("Name:");
        nameLabelRight.setBounds(20, 80, 80, 25);
        rightBox.add(nameLabelRight);
    
        nameTextFieldRight = new JTextField();
        nameTextFieldRight.setBounds(110, 80, 220, 25);
        rightBox.add(nameTextFieldRight);
    
        // Button to mark attendance for member
        markAttendanceActions = new JButton("Mark Attendance");
        markAttendanceActions.setBounds(60, 180, 250, 45);
        rightBox.add(markAttendanceActions);
    
        // Button to clear input fields in the right panel
        clearActions = new JButton("CLEAR");
        clearActions.setBounds(140, 250, 80, 35);
        rightBox.add(clearActions);
    
        // Button to save data to file
        saveToFileButton = new JButton("Save to File");
        saveToFileButton.setBounds(60, 300, 120, 35);
        rightBox.add(saveToFileButton);
    
        // Button to read data from file
        readFromFileButton = new JButton("Read from File");
        readFromFileButton.setBounds(190, 300, 120, 35);
        rightBox.add(readFromFileButton);
    
        // Label and text field for payment amount input
        JLabel payAmountLabel = new JLabel("Pay Amount:");
        payAmountLabel.setBounds(20, 360, 100, 25);
        rightBox.add(payAmountLabel);
    
        payAmountField = new JTextField();
        payAmountField.setBounds(120, 360, 100, 25);
        rightBox.add(payAmountField);
    
        // Button to pay due amount
        payDueButton = new JButton("Pay Due");
        payDueButton.setBounds(230, 360, 110, 25);
        rightBox.add(payDueButton);
    
        // Add left and right boxes to the input panel
        inputPanelActions.add(leftBox);
        inputPanelActions.add(rightBox);
    
        // Add input panel to the main actions panel
        ActionsPanel.add(inputPanelActions);
    
        // Add action listeners for buttons
        revertRegularPanelButton.addActionListener(this);
        revertRegularActions.addActionListener(this);
        clearActions.addActionListener(this);
        upgradePlanActions.addActionListener(this);
        saveToFileButton.addActionListener(this);
        readFromFileButton.addActionListener(this);
        calculateDiscountButton.addActionListener(this);
        payDueButton.addActionListener(this);
    }


    private void clearRegularFields()  
    { 
        // Clears all input fields related to Regular Member details
        // Resets text fields, combo boxes, and radio buttons to their default or empty states
        idField.setText("");
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        referralField.setText("");
        locationField.setText("");
        plan.setSelectedIndex(0);
        memberStartYear.setSelectedIndex(0);
        memberStartMonth.setSelectedIndex(0);
        memberStartDay.setSelectedIndex(0);
        dobYear.setSelectedIndex(0);
        dobMonth.setSelectedIndex(0);
        dobDay.setSelectedIndex(0);
        male.setSelected(false);
        female.setSelected(false);
        
    }

    private void clearPremiumFields() 
    {
        // Clears all input fields related to Premium Member details
        // Resets text fields, combo boxes, radio buttons, and payment amount to default or empty values
        idFieldPremium.setText("");
        nameFieldPremium.setText("");
        phoneFieldPremium.setText("");
        emailFieldPremium.setText("");
        trainerField.setText("");
        locationFieldPremium.setText("");
        plan.setSelectedIndex(0);
        memberStartYear.setSelectedIndex(0);
        memberStartMonth.setSelectedIndex(0);
        memberStartDay.setSelectedIndex(0);
        dobYear.setSelectedIndex(0);
        dobMonth.setSelectedIndex(0);
        dobDay.setSelectedIndex(0);
        malePremium.setSelected(false);
        femalePremium.setSelected(false);
        paidAmountFieldPremium.setText("0"); 
    }
    
    private void clearActionsFields() 
    {
        /*
         * Clears all input fields related to member actions.
         * This method resets the text of all relevant JTextFields to empty strings,
         * effectively clearing any previous user input.
         */
        regularIdField.setText("");
        premiumIdField.setText("");
        idTextFieldRight.setText("");
        nameTextFieldRight.setText("");
        removalReasonTextField.setText(""); 
    }
    
    @Override
    public void actionPerformed(ActionEvent actions) 
    {
        /*
         * This method is invoked automatically when an action event occurs.
         * The 'actions' parameter provides details about the event,
         * such as which UI component triggered it.
         * 
         * You can use this method to handle different button clicks or other actions
         * by checking the source of the event and performing corresponding logic.
         */
        if (actions.getSource() == addRegular) 
        {
            // Getting ID from text field
            String idValue = idField.getText();
            // Constructing date of birth and membership start date from combo box selections
            String dob = dobDay.getSelectedItem() + "-" + dobMonth.getSelectedItem() + "-" + dobYear.getSelectedItem();
            String msd = memberStartDay.getSelectedItem() + "-" + memberStartMonth.getSelectedItem() + "-" + memberStartYear.getSelectedItem();
        
            // Getting selected plan
            String selectedPlan = (String) plan.getSelectedItem();
        
            // Initializing default values
            int attendance = 0;
            double loyaltyPoints = 0.0;
            boolean activeStatus = true;
        
            try 
            {
                // Checking if ID field is empty
                if (idValue.isEmpty()) 
                {
                    JOptionPane.showMessageDialog(frame, "Member ID cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Parsing ID to integer
                int idRegular = Integer.parseInt(idValue);
        
                // Getting input field values
                String name = nameField.getText().trim();
                String location = locationField.getText().trim();
                String phone = phoneField.getText().trim();
                String email = emailField.getText().trim();
                String referral = referralField.getText().trim();
                String gender = "";
        
                // Getting gender selection
                if (male.isSelected())
                {
                    gender = "Male";
                }
                else if (female.isSelected()) 
                {
                    gender = "Female";
                } 
                else 
                {
                    JOptionPane.showMessageDialog(frame, "Please select a gender", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Validating phone number
                if (phone.isEmpty() || phone.length() != 10 || phone.length() > 10) 
                {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid phone number", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Validating email format
                if (email.isEmpty() && !email.endsWith("@gmail.com")) 
                {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid email address", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Validating required fields
                if (name.isEmpty() || phone.isEmpty() || gender.isEmpty() || dob.isEmpty() || msd.isEmpty()) 
                {
                    JOptionPane.showMessageDialog(frame, "Important Fields are Empty!", "Failed", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Creating RegularMember object
                RegularMember regular = new RegularMember(idRegular, name, location, phone, email, gender, dob, msd, referral, attendance, loyaltyPoints, activeStatus, paidAmount);
        
                // Check for duplicate ID in the arrayList
                boolean duplicateFound = false;
                for (GymMember gym : arrayList) 
                {
                    if (gym.getId() == idRegular) 
                    {
                        JOptionPane.showMessageDialog(frame, "The Membership ID Already Exists", "Error", JOptionPane.ERROR_MESSAGE);
                        duplicateFound = true;
                        break;
                    }
                }
        
                // If no duplicate is found, add member to the list
                if (!duplicateFound) 
                {
                    //RegularMember regular = new RegularMember(idRegular, name, location, phone, email, gender, dob, msd, referral, attendance, loyaltyPoints, activeStatus,paidAmount);
                    arrayList.add(regular);
                    JOptionPane.showMessageDialog(frame, "Member has been Added!!!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearRegularFields(); // Clear form fields after successful addition
                }
            } 
            catch (NumberFormatException ex) 
            {
                // Handle invalid ID input (non-numeric)
                JOptionPane.showMessageDialog(frame, "Please Enter a Number for ID", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        else if (actions.getSource() == Clear) 
        {
            // Calling method to clear all input fields related to Regular Member form
            clearRegularFields();
        }
        else if(actions.getSource() == revertRegular)
        {
            // Getting input values from text fields
            String memberIdText = idField.getText().trim();
            String name = nameField.getText().trim();
            // Check if the Member ID field is empty
            if (memberIdText.isEmpty()) 
            {
                JOptionPane.showMessageDialog(frame, "Please enter a Member ID", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
                
            try 
            {
                // Try parsing the Member ID into an integer
                int memberId = Integer.parseInt(memberIdText);
        
                // Find the member in the system
                GymMember member = findMemberById(memberId);
        
                // If member not found, show error
                if (member == null) 
                {
                    JOptionPane.showMessageDialog(frame, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Check if the member is a RegularMember
                if (!(member instanceof RegularMember)) 
                {
                    JOptionPane.showMessageDialog(frame, "The entered ID does not belong to a Regular Member!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Cast the member to RegularMember type
                RegularMember regular = (RegularMember) member;
        
                // Check if the member is already active; if so, it can't be reverted
                if (!regular.getActiveStatus()) 
                {
                    JOptionPane.showMessageDialog(
                        frame,
                        "Cannot revert. Member ID " + memberId + " is not inactive.",
                        "Revert Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
        
                // Ask for confirmation before reverting the member
                int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Are you sure you want to revert Regular Member ID " + memberId + "?\nName: " + name,
                    "Confirm Revert",
                    JOptionPane.YES_NO_OPTION
                );
        
                // If confirmed, perform the revert operation
                if (confirm == JOptionPane.YES_OPTION) 
                {
                    // Call the revert method on the member with the reason
                    regular.revertRegularMember(name);
        
                    // Show success message after reverting
                    JOptionPane.showMessageDialog(
                        frame,
                        "Regular Member ID " + memberId + " has been reverted.\nName: " + name,
                        "Revert Successful",
                        JOptionPane.INFORMATION_MESSAGE
                    );
        
                    // Clear the input fields after successful revert
                    regularIdField.setText("");
                    premiumIdField.setText("");
                    removalReasonTextField.setText("");
                }
            } 
            catch (NumberFormatException ex) 
            {
                // Handle invalid (non-numeric) Member ID input
                JOptionPane.showMessageDialog(frame, "Invalid Member ID format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        else if (actions.getSource() == displayRegular) 
        {
            // Get the ID from the input field and trim any whitespace
            String id = idField.getText().trim();
        
            // Check if the ID field is empty
            if (id.isEmpty()) 
            {
                JOptionPane.showMessageDialog(frame, "Please enter a Member ID", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            try {
                // Parse the entered ID string into an integer
                int memberId = Integer.parseInt(id);
        
                // Try to find the GymMember by the provided ID
                GymMember member = findMemberById(memberId);
        
                // If no member is found, show an error message
                if (member == null) 
                {
                    JOptionPane.showMessageDialog(frame, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Check if the found member is not a RegularMember
                if (!(member instanceof RegularMember)) 
                {
                    JOptionPane.showMessageDialog(frame, "Entered ID does not belong to a Regular Member.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Display member details via the member's own display method
                member.display();
        
                // Also display simple details using a separate GUI method
                showSimpleDetails(member); 
            } 
            catch (NumberFormatException ex) 
            {
                // Handle the case when the ID is not a valid number
                JOptionPane.showMessageDialog(frame, "Invalid ID format! Please enter numbers only.", "Error", JOptionPane.ERROR_MESSAGE);
            } 
            catch (Exception ex) 
            {
                // Catch any other unexpected exception and show its message
                JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        else if (actions.getSource() == addPremium) 
        {
            // Get the entered ID from the field
            String IDValue = idFieldPremium.getText();
            try 
            {
                // Check if the ID is empty
                if (IDValue.isEmpty()) 
                {
                    JOptionPane.showMessageDialog(frame, "Member ID cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Convert the ID to an integer
                int idPremium = Integer.parseInt(IDValue);
        
                // Get other input fields for the premium member
                String namePremium = nameFieldPremium.getText().trim();
                String locationPremium = locationFieldPremium.getText().trim();
                String phonePremium = phoneFieldPremium.getText().trim();
                String emailPremium = emailFieldPremium.getText().trim();
                String genderPremium = "";
                String paidAmountstrPremium = paidAmountFieldPremium.getText().trim();
        
                // Set gender based on selected radio button
                if (malePremium.isSelected())
                {
                    genderPremium = "Male";
                } 
                else if (femalePremium.isSelected()) 
                {
                    genderPremium = "Female";
                }
                else
                {
                    JOptionPane.showMessageDialog(frame, "Please select a gender", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Validate phone number
                if (phonePremium.isEmpty() || phonePremium.length() < 10) 
                {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid phone number", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Validate email
                if (emailPremium.isEmpty() && !emailPremium.contains("@gmail.com")) 
                {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid email address", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Check if paid amount is entered
                if(paidAmountstrPremium.isEmpty())
                {
                    JOptionPane.showMessageDialog(frame, "Please enter the paid amount", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Set default values for attendance, loyalty points and status
                int attendance = 0;
                double loyaltyPoints = 0;
                boolean activeStatus = true;
        
                // Premium charge is fixed
                double premiumCharge = 50000;
        
                // Convert paid amount to double
                double paidAmount = Double.parseDouble(paidAmountstrPremium);
        
                // Check if payment is full
                boolean isFullPayment = paidAmount >= premiumCharge;
                double discountAmount = 0;
                
                // Apply 10% discount if full payment
                if (isFullPayment) 
                {
                    discountAmount = premiumCharge * 0.10;
                    paidAmount -= discountAmount;
                }
        
                // Check for required fields
                if (namePremium.isEmpty() || phonePremium.isEmpty() || locationPremium.isEmpty())
                {
                    JOptionPane.showMessageDialog(frame, "Important Fields are Empty!", "Failed", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Format DOB and Membership Start date
                String dobPremium = dobDayPremium.getSelectedItem() + "-" + dobMonthPremium.getSelectedItem() + "-" + dobYearPremium.getSelectedItem();
                String msPremium = memberStartDayPremium.getSelectedItem() + "-" + memberStartMonthPremium.getSelectedItem() + "-" + memberStartYearPremium.getSelectedItem();
        
                // Get trainer name
                String trainer = trainerField.getText().trim();
        
                // Check again for critical fields
                if (namePremium.isEmpty() || phonePremium.isEmpty() || genderPremium.isEmpty() || trainer.isEmpty()) 
                {
                    JOptionPane.showMessageDialog(frame, "Important fields are empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Validate paid amount is a valid positive number
                double paidAmountPremuim;
                try 
                {
                    paidAmount = Double.parseDouble(paidAmountFieldPremium.getText().trim());                    
                    if (paidAmount <= 0) 
                    {
                        JOptionPane.showMessageDialog(frame, "Paid amount must be positive", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } 
                catch (NumberFormatException ex) 
                {
                    JOptionPane.showMessageDialog(null, "Invalid paid amount", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Create new PremiumMember object
                PremiumMember premiumMember = new PremiumMember(
                    idPremium, namePremium, locationPremium, phonePremium, emailPremium, genderPremium,
                    dobPremium, msPremium, attendance, loyaltyPoints, activeStatus,
                    premiumCharge, trainer, isFullPayment, paidAmount, discountAmount
                );
        
                // Check for duplicate ID
                boolean duplicateFound = false;
                for (GymMember gym : arrayList) 
                {
                    if (gym.getId() == idPremium) 
                     {
                        JOptionPane.showMessageDialog(frame, "The Membership ID Already Exists", "Error", JOptionPane.ERROR_MESSAGE);
                        duplicateFound = true;
                        break;
                    }
                }
        
                // If no duplicate, add to array list and show success
                if (!duplicateFound) 
                {
                    //PremiumMember premiumMember = new PremiumMember(...);
                    arrayList.add(premiumMember);
                    JOptionPane.showMessageDialog(frame, "Member has been Added!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearPremiumFields();
                }
            } 
            catch (NumberFormatException ex)
            {
                // Handle error if ID is not a valid number
                JOptionPane.showMessageDialog(frame, "Please Enter a Number for ID", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        else if (actions.getSource() == ClearPremium) 
        {
            // Calls the method to clear all input fields related to the Premium Member form
            clearPremiumFields();
        }

        if (actions.getSource() == displayPremium) 
        {
            // Retrieve and trim the ID input from the text field
            String id = idFieldPremium.getText().trim();
        
            // Check if the ID field is empty
            if (id.isEmpty()) 
            {
                JOptionPane.showMessageDialog(frame, "Please enter a Member ID", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            try {
                // Try converting the input to an integer
                int memberId = Integer.parseInt(id);
        
                // Find the member by their ID
                GymMember member = findMemberById(memberId);
        
                // If member is not found, show an error
                if (member == null) 
                {
                    JOptionPane.showMessageDialog(frame, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Check if the found member is a PremiumMember
                if (!(member instanceof PremiumMember)) 
                {
                    JOptionPane.showMessageDialog(frame, "Entered ID does not belong to a Premium Member.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Display the member's information and show a simplified version
                member.display();
                showSimpleDetails(member); 
            } 
            catch (NumberFormatException ex) 
            {
                // Handle the case when ID is not a valid integer
                JOptionPane.showMessageDialog(frame, "Invalid ID format! Please enter numbers only.", "Error", JOptionPane.ERROR_MESSAGE);
            } 
            catch (Exception ex) 
            {
                // Catch any unexpected errors and display the message
                JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        else if (actions.getSource() == activateMembershipActions) 
        {
            // Get input from both regular and premium ID fields
            String regularIdText = regularIdField.getText().trim();
            String premiumIdText = premiumIdField.getText().trim(); 
        
            // Variables to store the found member and their ID
            GymMember member = null;
            int memberId = -1;
        
            // Check if the Regular Member ID field is filled
            if (!regularIdText.isEmpty()) 
            {
                try 
                {
                    // Parse the Regular ID and find the member
                    memberId = Integer.parseInt(regularIdText);
                    member = findMemberById(memberId);
        
                    // Verify that the member is a RegularMember
                    if (!(member instanceof RegularMember)) 
                    {
                        JOptionPane.showMessageDialog(frame, "Entered ID does not belong to a Regular Member.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } 
                catch (NumberFormatException ex)
                {
                    // Handle invalid number format for Regular ID
                    JOptionPane.showMessageDialog(frame, "Invalid Regular Member ID format!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } 
            // Check if the Premium Member ID field is filled (if regular was empty)
            else if (!premiumIdText.isEmpty()) 
            {
                try 
                {
                    // Parse the Premium ID and find the member
                    memberId = Integer.parseInt(premiumIdText);
                    member = findMemberById(memberId);
        
                    // Verify that the member is a PremiumMember
                    if (!(member instanceof PremiumMember)) 
                    {
                        JOptionPane.showMessageDialog(frame, "Entered ID does not belong to a Premium Member.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                catch (NumberFormatException ex) 
                {
                    // Handle invalid number format for Premium ID
                    JOptionPane.showMessageDialog(frame, "Invalid Premium Member ID format!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            else 
            {
                // If both fields are empty, prompt user to enter an ID
                JOptionPane.showMessageDialog(frame, "Please enter either a Regular or Premium Member ID", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            // If no member was found, show error
            if (member == null) 
            {
                JOptionPane.showMessageDialog(frame, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
            } 
            // If the member is already active, notify the user
            else if (member.getActiveStatus()) 
            {
                JOptionPane.showMessageDialog(frame, "Membership is already active!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            else 
            {
                // Activate the membership and show success message
                member.activateMembership();
                JOptionPane.showMessageDialog(frame, "Membership activated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        // Yo deactivate membership ko lagi ho
        else if (actions.getSource() == revertRegularActions || actions.getSource() == revertRegular)
        {
            // Get text input from regular and premium ID fields
            String regularIdText = regularIdField.getText().trim();
            String premiumIdText = premiumIdField.getText().trim();
        
            // Variables to store member instance and parsed ID
            GymMember member = null;
            int memberId = -1;
        
            // Check if Regular Member ID is provided
            if (!regularIdText.isEmpty()) 
            {
                try 
                {
                    // Parse Regular Member ID and find the member object
                    memberId = Integer.parseInt(regularIdText);
                    member = findMemberById(memberId);
        
                    // Verify the member is actually a RegularMember
                    if (!(member instanceof RegularMember)) 
                    {
                        JOptionPane.showMessageDialog(frame, "Entered ID does not belong to a Regular Member.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) 
                {
                    // Handle invalid Regular Member ID format (non-numeric)
                    JOptionPane.showMessageDialog(frame, "Invalid Regular Member ID format!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } 
            // If Regular ID is empty, check if Premium Member ID is provided
            else if (!premiumIdText.isEmpty()) 
            {
                try 
                {
                    // Parse Premium Member ID and find the member object
                    memberId = Integer.parseInt(premiumIdText);
                    member = findMemberById(memberId);
        
                    // Verify the member is actually a PremiumMember
                    if (!(member instanceof PremiumMember)) 
                    {
                        JOptionPane.showMessageDialog(frame, "Entered ID does not belong to a Premium Member.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                catch (NumberFormatException ex) 
                {
                    // Handle invalid Premium Member ID format (non-numeric)
                    JOptionPane.showMessageDialog(frame, "Invalid Premium Member ID format!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } 
            // Neither ID field provided, prompt user to enter one
            else 
            {
                JOptionPane.showMessageDialog(frame, "Please enter either a Regular or Premium Member ID", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            // Check if the member is already inactive (deactivated)
            if (!member.getActiveStatus()) 
            {
                JOptionPane.showMessageDialog(frame, "Membership is already inactive.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
        
            // Deactivate the membership and inform user of success
            member.deactivateMembership();
            JOptionPane.showMessageDialog(frame, "Membership deactivated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
        
        else if (actions.getSource() == revertPremium || actions.getSource() == revertPremiumActions) 
        {
            // Get the member ID and other input values from the text fields
            String memberIdText = premiumIdField.getText().trim();
            String reason = removalReasonTextField.getText().trim();
        
            // Check if Member ID is empty
            if (memberIdText.isEmpty()) 
            {
                JOptionPane.showMessageDialog(frame, "Please enter a Member ID", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            // Check if the reason for reverting is empty
            if (reason.isEmpty()) 
            {
                JOptionPane.showMessageDialog(frame, "Please provide a reason for reverting", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            try 
            {
                // Convert the member ID to integer
                int memberId = Integer.parseInt(memberIdText);
        
                // Find the member by ID
                GymMember member = findMemberById(memberId);
        
                // Check if member exists
                if (member == null) 
                {
                    JOptionPane.showMessageDialog(frame, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Check if the member is a PremiumMember
                if (!(member instanceof PremiumMember)) 
                {
                    JOptionPane.showMessageDialog(frame, "The entered ID does not belong to a Premium Member!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Ask for confirmation to revert the Premium Member
                int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Are you sure you want to revert Premium Member ID " + memberId + "?\nID: " + memberIdText,
                    "Confirm Revert",
                    JOptionPane.YES_NO_OPTION
                );
        
                // If user confirms, perform the revert operation
                if (confirm == JOptionPane.YES_OPTION) 
                {
                    PremiumMember premium = (PremiumMember) member;
                    premium.revertPremiumMember(reason);
        
                    // Show confirmation dialog
                    JOptionPane.showMessageDialog(
                        frame,
                        "Premium Member ID " + memberId + " has been reverted.\nID: " + memberIdText + "\nReason: " + reason,
                        "Revert Successful",
                        JOptionPane.INFORMATION_MESSAGE
                    );
        
                    // Clear input fields
                    regularIdField.setText("");
                    premiumIdField.setText("");
                    removalReasonTextField.setText("");
                }
            } 
            catch (NumberFormatException ex) 
            {
                // Handle invalid number input for ID
                JOptionPane.showMessageDialog(frame, "Invalid Member ID format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        
        else if(actions.getSource() == revertRegularPanelButton)
        {
            // Getting input values from text fields
            String memberIdText = regularIdField.getText().trim();
            String name = premiumIdField.getText().trim(); // This seems to store the member's name temporarily
            String reason = removalReasonTextField.getText().trim(); // Reason for reverting the member
        
            // Check if the Member ID field is empty
            if (memberIdText.isEmpty()) 
            {
                JOptionPane.showMessageDialog(frame, "Please enter a Member ID", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            // Check if the reason for reverting is empty
            if (reason.isEmpty()) 
            {
                JOptionPane.showMessageDialog(frame, "Please provide a reason for reverting", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            try 
            {
                // Try parsing the Member ID into an integer
                int memberId = Integer.parseInt(memberIdText);
        
                // Find the member in the system
                GymMember member = findMemberById(memberId);
        
                // If member not found, show error
                if (member == null) 
                {
                    JOptionPane.showMessageDialog(frame, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Check if the member is a RegularMember
                if (!(member instanceof RegularMember)) 
                {
                    JOptionPane.showMessageDialog(frame, "The entered ID does not belong to a Regular Member!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Cast the member to RegularMember type
                RegularMember regular = (RegularMember) member;
        
                // Check if the member is already active; if so, it can't be reverted
                if (!regular.getActiveStatus()) 
                {
                    JOptionPane.showMessageDialog(
                        frame,
                        "Cannot revert. Member ID " + memberId + " is not inactive.",
                        "Revert Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
        
                // Ask for confirmation before reverting the member
                int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Are you sure you want to revert Regular Member ID " + memberId + "?\nName: " + name,
                    "Confirm Revert",
                    JOptionPane.YES_NO_OPTION
                );
        
                // If confirmed, perform the revert operation
                if (confirm == JOptionPane.YES_OPTION) 
                {
                    // Call the revert method on the member with the reason
                    regular.revertRegularMember(reason);
        
                    // Show success message after reverting
                    JOptionPane.showMessageDialog(
                        frame,
                        "Regular Member ID " + memberId + " has been reverted.\nName: " + name + "\nReason: " + reason,
                        "Revert Successful",
                        JOptionPane.INFORMATION_MESSAGE
                    );
        
                    // Clear the input fields after successful revert
                    regularIdField.setText("");
                    premiumIdField.setText("");
                    removalReasonTextField.setText("");
                }
            } 
            catch (NumberFormatException ex) 
            {
                // Handle invalid (non-numeric) Member ID input
                JOptionPane.showMessageDialog(frame, "Invalid Member ID format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        else if(actions.getSource() == upgradePlanActions)
        {
            // Get entered Regular Member ID and Premium Member ID from input fields
            String regularIdText = regularIdField.getText().trim();
            String premiumIdText = premiumIdField.getText().trim();
            GymMember member = null;
            int memberId = -1;
        
            // Check if Regular Member ID field is not empty
            if (!regularIdText.isEmpty()) 
            {
                try
                {
                    // Parse Regular Member ID to integer
                    memberId = Integer.parseInt(regularIdText);
                    // Find the member object by ID
                    member = findMemberById(memberId);
        
                    // Verify that the found member is a RegularMember
                    if (!(member instanceof RegularMember)) 
                    {
                        JOptionPane.showMessageDialog(frame, "Entered ID does not belong to a Regular Member.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
        
                    // Check if the member is active, else show error and return
                    if (!member.getActiveStatus()) 
                    {
                        JOptionPane.showMessageDialog(frame, "Cannot upgrade plan. Member is inactive.", "Upgrade Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } 
                catch (NumberFormatException ex) 
                {
                    // Handle invalid ID format error
                    JOptionPane.showMessageDialog(frame, "Invalid Regular Member ID format!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } 
            else 
            {
                // Show error if no Regular Member ID was entered (Premium members cannot upgrade plan here)
                JOptionPane.showMessageDialog(frame, "Only Regular Members can upgrade plans. Please enter a Regular Member ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            // Get the current plan of the Regular Member
            String currentPlan = ((RegularMember) member).getPlan();
        
            // Show input dialog for the user to select a new plan
            String newPlan = (String) JOptionPane.showInputDialog(
                frame, "Select new plan:", "Upgrade", 
                JOptionPane.PLAIN_MESSAGE, null,
                new String[]{"Select", "Basic", "Standard", "Deluxe"}, currentPlan
            );
        
            // Validate the selected new plan
            if (newPlan == null || newPlan.equals("Select") || newPlan.equals(currentPlan)) {
                JOptionPane.showMessageDialog(frame, "No valid new plan selected.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            // Perform the plan upgrade and get the result message
            String result = ((RegularMember) member).upgradePlan(newPlan);
        
            // Show the result of the upgrade operation
            JOptionPane.showMessageDialog(frame, result);
        }
        
        else if (actions.getSource() == calculateDiscountButton) 
        {
            String premiumIdText = premiumIdField.getText().trim();
            GymMember member = null;
            int memberId = -1;
        
            if (!premiumIdText.isEmpty()) 
            {
                try 
                {
                    memberId = Integer.parseInt(premiumIdText);
                    member = findMemberById(memberId);
        
                    // Check if the member exists and is a Premium Member
                    if (!(member instanceof PremiumMember)) 
                    {
                        JOptionPane.showMessageDialog(frame, "Entered ID does not belong to a Premium Member.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
        
                    // Check if the member is active before calculating discount
                    if (!member.getActiveStatus()) 
                    {
                        JOptionPane.showMessageDialog(frame, "Cannot calculate Discount. Member is inactive.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } 
                catch (NumberFormatException ex) 
                {
                    JOptionPane.showMessageDialog(frame, "Invalid Premium Member ID format!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            else 
            {
                JOptionPane.showMessageDialog(frame, "Please enter a Premium Member ID to calculate discount", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            // Cast to PremiumMember and get discount
            PremiumMember prem = (PremiumMember) member;
            double discount = prem.getDiscountAmount();
        
            // Show discount info
            JOptionPane.showMessageDialog(frame, "Discount for Premium Member ID " + memberId + ": Rs. " + discount, "Discount Info", JOptionPane.INFORMATION_MESSAGE);
        }

        else if (actions.getSource() == displayActions) 
        {
            // Get input from both Regular and Premium member ID fields
            String regularIdText = regularIdField.getText().trim();
            String premiumIdText = premiumIdField.getText().trim();
            GymMember member = null;
            int memberId = -1;
        
            // If Regular Member ID field is not empty, try to find that member
            if (!regularIdText.isEmpty())
            {
                try 
                {
                    memberId = Integer.parseInt(regularIdText);       // Parse the input to an integer
                    member = findMemberById(memberId);                // Retrieve member object by ID
        
                    // Check if the member is indeed a RegularMember
                    if (!(member instanceof RegularMember)) 
                    {
                        JOptionPane.showMessageDialog(frame, "Entered ID does not belong to a Regular Member.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;  // Exit if not a Regular Member
                    }
                }
                catch (NumberFormatException ex) 
                {
                    // Handle invalid number format for Regular Member ID input
                    JOptionPane.showMessageDialog(frame, "Invalid Regular Member ID format!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } 
            // Else if Premium Member ID field is not empty, try to find that member
            else if (!premiumIdText.isEmpty()) 
            {
                try 
                {
                    memberId = Integer.parseInt(premiumIdText);       // Parse input to integer
                    member = findMemberById(memberId);                // Retrieve member object by ID
        
                    // Check if the member is indeed a PremiumMember
                    if (!(member instanceof PremiumMember)) 
                    {
                        JOptionPane.showMessageDialog(frame, "Entered ID does not belong to a Premium Member.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;  // Exit if not a Premium Member
                    }
                } 
                catch (NumberFormatException ex) 
                {
                    // Handle invalid number format for Premium Member ID input
                    JOptionPane.showMessageDialog(frame, "Invalid Premium Member ID format!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            else 
            {
                // If neither ID field is filled, prompt user to enter one
                JOptionPane.showMessageDialog(frame, "Please enter either a Regular or Premium Member ID", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            // Display the member details using the member's display method
            member.display();
        
            // Show simplified details on UI (likely custom UI update method)
            showSimpleMemberDetails(member);
        }

        else if (actions.getSource() == markAttendanceActions) 
        {
            // Get the member ID entered in the text field and trim whitespace
            String id = idTextFieldRight.getText().trim();
        
            // Check if the ID field is empty and show error if true
            if (id.isEmpty()) 
            {
                JOptionPane.showMessageDialog(frame, "Please enter a Member ID", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            try 
            {
                // Parse the entered ID string to an integer
                int memberId = Integer.parseInt(id);
        
                // Find the GymMember object by the parsed member ID
                GymMember member = findMemberById(memberId);
        
                // If no member is found with the given ID, display error message
                if (member == null) 
                {
                    JOptionPane.showMessageDialog(frame, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Check if the member's membership is active
                if (member.getActiveStatus()) 
                {
                    // Mark attendance for the active member
                    member.markAttendance();
        
                    // Inform user of successful attendance marking
                    JOptionPane.showMessageDialog(frame, "Attendance marked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } 
                else 
                {
                    // Show error if member is inactive
                    JOptionPane.showMessageDialog(frame, "Member is not active!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } 
            catch (NumberFormatException ex) 
            {
                // Handle case where the entered ID is not a valid number
                JOptionPane.showMessageDialog(frame, "Invalid ID. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        else if (actions.getSource() == clearActions)
        {
            // Call method to clear all input fields related to the actions panel
            clearActionsFields();
        }

        else if (actions.getSource() == saveToFileButton) 
        {
            if (arrayList == null || arrayList.isEmpty()) 
            {
                JOptionPane.showMessageDialog(frame, "No members to save.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            File file = new File("MemberDetails.txt");
        
            try (FileWriter writer = new FileWriter(file, false)) // overwrite mode
            {
                // Write header
                writer.write("========================================================================================================================================================================================================================================================================================================================\n");
                writer.write(String.format(
                    "%-5s %-15s %-15s %-12s %-25s %-8s %-12s %-23s %-12s %-16s %-16s %-20s %-12s %-12s %-20s %-14s %-16s %-14s %-14s %-10s\n",
                    "ID", "Name", "Location", "Phone", "Email", "Gender", "DOB", "Membership Start Date", "Attendance", "Loyalty Points", "Active Status",
                    "Referral Source", "Plan", "Price", "Removal Reason", "Full Payment", "Discount Amount", "Paid Amount", "Net Amount", "Type"
                ));
                writer.write("========================================================================================================================================================================================================================================================================================================================\n");
                for (GymMember member : arrayList) 
                {
                    // Common fields
                    String id = String.valueOf(member.getId());
                    String name = member.getName();
                    String location = member.getLocation();
                    String phone = member.getPhone();
                    String email = member.getEmail();
                    String gender = member.getGender();
                    String dob = member.getDOB();
                    String start = member.getMembershipStartDate();
                    int attendance = member.getAttendance();
                    double loyaltyPoints = member.getLoyaltyPoints();
                    String activeStatus = member.getActiveStatus() ? "Active" : "Inactive";
        
                    String referal, plan, removal, total, type;
                    double price, discount, paid, net;
        
                    if (member instanceof RegularMember) 
                    {
                        RegularMember reg = (RegularMember) member;
                        referal = reg.getReferralSource();
                        plan = reg.getPlan();
                        price = reg.getPrice();
                        removal = reg.getRemovalReason();
                        total = "N/A";
                        discount = 0.0;
                        paid = 0.0;
                        net = 0.0;
                        type = "Regular";
                    } 
                    
                    else 
                    {
                        PremiumMember prem = (PremiumMember) member;
                        referal = "N/A";
                        plan = "N/A";
                        price = prem.getPremiumCharge();
                        removal = "N/A";
                        total = prem.getisFullpayment() ? "Yes" : "No";
                        discount = prem.getDiscount();
                        paid = prem.getPaidAmount();
                        net = price - discount;
                        type = "Premium";
                    }
                    
                    writer.write(String.format(
                        "%-5s %-15s %-15s %-12s %-25s %-8s %-12s %-23s %-12d %-16.2f %-16s %-20s %-12s %-12.2f %-20s %-14s %-16.2f %-14.2f %-14.2f %-10s\n",
                        id, name, location, phone, email, gender, dob, start, attendance, loyaltyPoints, activeStatus,
                        referal, plan, price, removal, total, discount, paid, net, type
                    ));
                    writer.write("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                }
                
                writer.write("========================================================================================================================================================================================================================================================================================================================\n");
        
                JOptionPane.showMessageDialog(frame, "All member details saved to file: MemberDetails.txt", "Success", JOptionPane.INFORMATION_MESSAGE);
            } 
            catch (IOException ex) 
            {
                JOptionPane.showMessageDialog(frame, "Error saving to file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
         else if (actions.getSource() == readFromFileButton) 
        {
            // Create a File object for the expected file
            File file = new File("MemberDetails.txt");
        
            // Check if the file exists before attempting to read
            if (!file.exists()) 
            {
                JOptionPane.showMessageDialog(frame, "File not found: members.txt", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try 
            {
                // Create a FileReader to read from the file
                FileReader fileReader = new FileReader(file);
                int character;
                char ch;
                String fullContent = "";
        
                // Read the file one character at a time until EOF
                while ((character = fileReader.read()) != -1) 
                {
                    ch = (char) character;
                    fullContent += ch;  // Append character to the content string
                }
        
                // Create a JTextArea to display the file content with monospaced font for alignment
                JTextArea textArea = new JTextArea(fullContent);
                textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                textArea.setEditable(false);  // Make the text area read-only
        
                // Put the text area inside a scroll pane to handle large content
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(800, 150));  // Set preferred size for the scroll pane
        
                // Show the scroll pane inside a message dialog
                JOptionPane.showMessageDialog(frame, scrollPane, "Member Data", JOptionPane.INFORMATION_MESSAGE);
        
                // Close the FileReader resource
                fileReader.close(); 
            } 
            catch (Exception ex) 
            {
                // Print the exception to the console and notify user via dialog
                System.out.println("Exception Occurred: " + ex);
                JOptionPane.showMessageDialog(frame, "Error reading member details!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        else if (actions.getSource() == payDueButton)
        {
            // Get the ID inputs for Regular and Premium members from text fields
            String regularIdText = regularIdField.getText().trim();
            String premiumIdText = premiumIdField.getText().trim();
            GymMember member = null;
            int memberId = -1;
        
            // Only Premium Members can pay dues, so check the premiumIdText input
            if (!premiumIdText.isEmpty()) 
            {
                try 
                {
                    // Parse the entered Premium Member ID
                    memberId = Integer.parseInt(premiumIdText);
                    member = findMemberById(memberId);
        
                    // Validate that the member found is a PremiumMember
                    if (!(member instanceof PremiumMember)) 
                    {
                        JOptionPane.showMessageDialog(frame, "Entered ID does not belong to a Premium Member.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
        
                    // Check if the member is active
                    if (!member.getActiveStatus()) 
                    {
                        JOptionPane.showMessageDialog(frame, "Cannot pay the Due!. Member is inactive.", "Upgrade Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } 
                catch (NumberFormatException ex) 
                {
                    // Handle invalid number format for Premium Member ID
                    JOptionPane.showMessageDialog(frame, "Invalid Premium Member ID format!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } 
            else 
            {
                // If Premium Member ID is not entered, show error (only Premium members can pay dues)
                JOptionPane.showMessageDialog(frame, "Please enter a Premium Member ID to pay dues", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            // Get the payment amount entered by the user
            String amountText = payAmountField.getText().trim();
        
            // Check if amount is empty
            if (amountText.isEmpty()) 
            {
                JOptionPane.showMessageDialog(frame, "Please enter the amount", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            try 
            {
                // Parse the payment amount as double
                double payment = Double.parseDouble(amountText);
                PremiumMember premium = (PremiumMember) member;
        
                // Check how much due is left before payment
                double dueBefore = premium.getDueAmount();
        
                // If nothing is due, notify the user
                if (dueBefore == 0) 
                {
                    JOptionPane.showMessageDialog(frame, "Full payment already made.", "Payment Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Validate the payment amount: must be > 0 and not exceed the due amount
                if (payment <= 0 || payment > dueBefore) 
                {
                    JOptionPane.showMessageDialog(frame, "Invalid payment amount. Must be between 1 and " + dueBefore, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Process the payment by calling the member's payDueAmount method
                String result = premium.payDueAmount(payment);
        
                // Show the result message (e.g., successful payment or updated due amount)
                JOptionPane.showMessageDialog(frame, result, "Payment Info", JOptionPane.INFORMATION_MESSAGE);
        
                // Clear the payment amount input field after successful payment
                payAmountField.setText("");
            } 
            catch (NumberFormatException ex)
            {
                // Handle invalid number format for payment amount
                JOptionPane.showMessageDialog(frame, "Invalid number format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Finds and returns a GymMember object with the given ID.
     */
    private GymMember findMemberById(int id) 
    {
        // Loop through the list of all members
        for (GymMember member : arrayList) 
        {
            // Check if the current member's ID matches the given ID
            if (member.getId() == id) 
            {
                return member;  // Return the matching member
            }
        }
        return null;  // No member found with the given ID
    }
    
    /**
     * Displays a simple window with details of the given GymMember.
     */
    private void showSimpleMemberDetails(GymMember member) 
    {
        // Create a new JFrame for displaying details
        JFrame detailsFrame = new JFrame("Member Details");
        detailsFrame.setSize(900, 600);
        detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Only close this window, not the whole app
        detailsFrame.setLocationRelativeTo(null);  // Center the frame on screen
        detailsFrame.getContentPane().setBackground(new Color(204, 255, 229));  // Set background color
        detailsFrame.setLayout(null);  // Use absolute positioning
        
        // Header label
        JLabel headLabel = new JLabel("GYM MEMBER DETAILS");
        headLabel.setBounds(280, 10, 400, 40);
        headLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headLabel.setForeground(new Color(0, 102, 204));
        detailsFrame.add(headLabel);
        
        // Text area to display member details (non-editable)
        JTextArea detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        detailsArea.setText(getMemberDetailsText(member));  // Populate with member details string
        
        // Add scroll pane to handle overflowing text
        JScrollPane scrollPane = new JScrollPane(detailsArea);
        scrollPane.setBounds(50, 70, 780, 450);
        detailsFrame.add(scrollPane);
        
        // Show the frame
        detailsFrame.setVisible(true);
    }
    
    /**
     * Displays a window with detailed information about a GymMember.
     */
    private void showSimpleDetails(GymMember member) 
    {
        // Create a new JFrame titled "Member Details"
        JFrame detailsFrame = new JFrame("Member Details");
        detailsFrame.setSize(900, 600);
        
        // Ensure closing this frame only disposes it, not the entire application
        detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Center the frame on the screen
        detailsFrame.setLocationRelativeTo(null);
        
        // Set a soft background color for the frame
        detailsFrame.getContentPane().setBackground(new Color(204, 255, 229));
        
        // Use absolute positioning for components
        detailsFrame.setLayout(null);
        
        // Add a header label
        JLabel headLabel = new JLabel("GYM MEMBER DETAILS");
        headLabel.setBounds(280, 10, 400, 40);
        headLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headLabel.setForeground(new Color(0, 102, 204));
        detailsFrame.add(headLabel);
        
        // Text area for showing member details, not editable by user
        JTextArea detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Set the member details text (assumed provided by getDetails method)
        detailsArea.setText(getDetails(member));
        
        // Add a scroll pane in case details overflow visible area
        JScrollPane scrollPane = new JScrollPane(detailsArea);
        scrollPane.setBounds(50, 70, 780, 450);
        detailsFrame.add(scrollPane);
        
        // Make the frame visible
        detailsFrame.setVisible(true);
    }
    
    private String getMemberDetailsText(GymMember member) 
    {
        // Initialize an empty string to hold the member details
        String details = "";
    
        // Check if the member is a RegularMember
        if (member instanceof RegularMember) 
        {
            RegularMember reg = (RegularMember) member;
    
            // Build the formatted string containing regular member details
            details = "\n\t\t\t=== REGULAR MEMBER DETAILS ===\n\n" +
                      "\t\t\tID: " + member.getId() + "\n" +
                      "\t\t\tName: " + member.getName() + "\n" +
                      "\t\t\tPhone: " + member.getPhone() + "\n" +
                      "\t\t\tEmail: " + member.getEmail() + "\n" +
                      "\t\t\tLocation: " + member.getLocation() + "\n" +
                      "\t\t\tGender: " + member.getGender() + "\n" +
                      "\t\t\tDOB: " + member.getDOB() + "\n" +
                      "\t\t\tMembership Start: " + member.getMembershipStartDate() + "\n" +
                      "\t\t\tStatus: " + (member.getActiveStatus() ? "Active" : "Inactive") + "\n" +
                      "\t\t\tAttendance: " + member.getAttendance() + "\n\n" +
                      "\t\t\tReferral: " + reg.getReferralSource() + "\n" +  // Referral source specific to RegularMember
                      "\t\t\tPlan: " + reg.getPlan() + "\n" +               // Membership plan for RegularMember
                      "\t\t\tPlan Price: " + reg.getPrice() + "\n" +       // Price of the plan
                      "\t\t\tLoyalty Points: " + reg.getLoyaltyPoints() + "\n"; // Loyalty points accumulated
        }
        // Otherwise, check if the member is a PremiumMember
        else if (member instanceof PremiumMember) 
        {
            PremiumMember prem = (PremiumMember) member;
    
            // Build the formatted string containing premium member details
            details = "\n\t\t\t=== PREMIUM MEMBER DETAILS ===\n\n" +
                      "\t\t\tID: " + member.getId() + "\n" +
                      "\t\t\tName: " + member.getName() + "\n" +
                      "\t\t\tPhone: " + member.getPhone() + "\n" +
                      "\t\t\tEmail: " + member.getEmail() + "\n" +
                      "\t\t\tLocation: " + member.getLocation() + "\n" +
                      "\t\t\tGender: " + member.getGender() + "\n" +
                      "\t\t\tDOB: " + member.getDOB() + "\n" +
                      "\t\t\tMembership Start: " + member.getMembershipStartDate() + "\n" +
                      "\t\t\tStatus: " + (member.getActiveStatus() ? "Active" : "Inactive") + "\n" +
                      "\t\t\tAttendance: " + member.getAttendance() + "\n\n" +
                      "\t\t\tTrainer: " + prem.getPersonalTrainer() + "\n" +  // Personal trainer assigned
                      "\t\t\tPremium Charge: Rs." + prem.getPremiumCharge() + "\n" +  // Premium membership charge
                      "\t\t\tPaid Amount: Rs." + prem.getPaidAmount() + "\n" +         // Amount paid so far
                      "\t\t\tDue Amount: Rs." + prem.getDueAmount() + "\n" +           // Remaining due amount
                      "\t\t\tDiscount Applied: Rs." + prem.getDiscountAmount() + "\n" +      // Discount given
                      "\t\t\tFinal Amount After Discount: Rs." + prem.getDiscount() + "\n";  // Net amount paid after discount
        }
    
        // Return the constructed member details string
        return details;
    }

    private String getDetails(GymMember member)
    {
        String details = "";
    
        // Check if member is a RegularMember
        if (member instanceof RegularMember)
        {
            RegularMember reg = (RegularMember) member;
    
            // Construct RegularMember details string
            details = "\n\t\t\t=== REGULAR MEMBER DETAILS ===\n\n" +
                      "\t\t\tID: " + member.getId() + "\n" +
                      "\t\t\tName: " + member.getName() + "\n" +
                      "\t\t\tPhone: " + member.getPhone() + "\n" +
                      "\t\t\tEmail: " + member.getEmail() + "\n" +
                      "\t\t\tLocation: " + member.getLocation() + "\n" +
                      "\t\t\tGender: " + member.getGender() + "\n" +
                      "\t\t\tDOB: " + member.getDOB() + "\n" +
                      "\t\t\tMembership Start: " + member.getMembershipStartDate() + "\n" +
                      "\t\t\tReferral: " + reg.getReferralSource() + "\n" +
                      "\t\t\tPlan: " + reg.getPlan() + "\n" +
                      "\t\t\tPlan Price: " + reg.getPrice() + "\n";
        }
        // Check if member is a PremiumMember
        else if (member instanceof PremiumMember)
        {
            PremiumMember prem = (PremiumMember) member;
    
            // Construct PremiumMember details string
            details = "\n\t\t\t=== PREMIUM MEMBER DETAILS ===\n\n" +
                      "\t\t\tID: " + member.getId() + "\n" +
                      "\t\t\tName: " + member.getName() + "\n" +
                      "\t\t\tPhone: " + member.getPhone() + "\n" +
                      "\t\t\tEmail: " + member.getEmail() + "\n" +
                      "\t\t\tLocation: " + member.getLocation() + "\n" +
                      "\t\t\tGender: " + member.getGender() + "\n" +
                      "\t\t\tDOB: " + member.getDOB() + "\n" +
                      "\t\t\tMembership Start: " + member.getMembershipStartDate() + "\n" +
                      "\t\t\tTrainer: " + prem.getPersonalTrainer() + "\n" +
                      "\t\t\tPremium Charge: Rs." + prem.getPremiumCharge() + "\n";
        }
    
        // Return the full details string
        return details;
    }


    public static void main(String args[])
    {
        // Create an instance of the GymGUI class to launch the application
        new GymGUI();
    }

}