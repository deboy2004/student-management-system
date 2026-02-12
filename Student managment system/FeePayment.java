import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * FeePayment class to manage student fee payments
 */
public class FeePayment implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Student student;
    private double amount;
    private Date date;
    private boolean isPaid;
    private String paymentType;  // e.g. Tuition, Library, Hostel, etc.
    private String receiptNumber;
    
    // Constructor
    public FeePayment(Student student, double amount, Date date, boolean isPaid, String paymentType, String receiptNumber) {
        this.student = student;
        this.amount = amount;
        this.date = date;
        this.isPaid = isPaid;
        this.paymentType = paymentType;
        this.receiptNumber = receiptNumber;
    }
    
    // Default constructor
    public FeePayment() {
        this.student = null;
        this.amount = 0.0;
        this.date = new Date();
        this.isPaid = false;
        this.paymentType = "";
        this.receiptNumber = "";
    }
    
    // Getters and setters
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public boolean isPaid() {
        return isPaid;
    }
    
    public void setPaid(boolean paid) {
        isPaid = paid;
    }
    
    public String getPaymentType() {
        return paymentType;
    }
    
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
    
    public String getReceiptNumber() {
        return receiptNumber;
    }
    
    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }
    
    // Get formatted date
    public String getFormattedDate() {
        if (date == null) return "Not set";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
    
    @Override
    public String toString() {
        return "Student: " + (student != null ? student.getName() : "Not assigned") +
               "\nAmount: $" + amount +
               "\nDate: " + getFormattedDate() +
               "\nStatus: " + (isPaid ? "Paid" : "Unpaid") +
               "\nPayment Type: " + paymentType +
               "\nReceipt Number: " + (isPaid ? receiptNumber : "N/A");
    }
    
    // Generate a receipt for a payment
    public String generateReceipt() {
        if (!isPaid) {
            return "Cannot generate receipt for unpaid fees";
        }
        
        StringBuilder receipt = new StringBuilder();
        receipt.append("--------- PAYMENT RECEIPT ---------\n");
        receipt.append("Receipt Number: ").append(receiptNumber).append("\n");
        receipt.append("Date: ").append(getFormattedDate()).append("\n");
        receipt.append("Student Name: ").append(student.getName()).append("\n");
        receipt.append("Student ID: ").append(student.getId()).append("\n");
        receipt.append("Roll Number: ").append(student.getRollNumber()).append("\n");
        receipt.append("Payment Type: ").append(paymentType).append("\n");
        receipt.append("Amount Paid: $").append(amount).append("\n");
        receipt.append("Status: Paid").append("\n");
        receipt.append("-----------------------------------\n");
        
        return receipt.toString();
    }
}
