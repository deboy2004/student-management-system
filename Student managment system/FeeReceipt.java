import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * FeeReceipt class to generate detailed receipts for fee payments
 * Implements Serializable for file storage
 */
public class FeeReceipt implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String receiptNumber;
    private Student student;
    private List<FeePayment> payments;
    private Date issueDate;
    private double totalAmount;
    private String paymentMethod; // Cash, Check, Bank Transfer, etc.
    private String issuedBy; // User who issued the receipt
    private String status; // Paid, Partial, Pending
    private String remarks;
    
    // Constructor
    public FeeReceipt(String receiptNumber, Student student, Date issueDate, 
                     String paymentMethod, String issuedBy, String status) {
        this.receiptNumber = receiptNumber;
        this.student = student;
        this.payments = new ArrayList<>();
        this.issueDate = issueDate;
        this.totalAmount = 0.0;
        this.paymentMethod = paymentMethod;
        this.issuedBy = issuedBy;
        this.status = status;
        this.remarks = "";
    }
    
    // Add a payment to this receipt
    public void addPayment(FeePayment payment) {
        payments.add(payment);
        totalAmount += payment.getAmount();
        
        // Update the status based on the total payments
        updateStatus();
    }
    
    // Update receipt status based on payments
    private void updateStatus() {
        if (payments.isEmpty()) {
            status = "Pending";
        } else {
            boolean allPaid = true;
            for (FeePayment payment : payments) {
                if (!payment.isPaid()) {
                    allPaid = false;
                    break;
                }
            }
            
            if (allPaid) {
                status = "Paid";
            } else {
                status = "Partial";
            }
        }
    }
    
    // Generate a formatted receipt text
    public String generateReceiptText() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder receiptText = new StringBuilder();
        
        receiptText.append("======================================\n");
        receiptText.append("           OFFICIAL FEE RECEIPT       \n");
        receiptText.append("======================================\n\n");
        
        receiptText.append("Receipt Number: ").append(receiptNumber).append("\n");
        receiptText.append("Date: ").append(dateFormat.format(issueDate)).append("\n\n");
        
        receiptText.append("Student Information:\n");
        receiptText.append("Name: ").append(student.getName()).append("\n");
        receiptText.append("ID: ").append(student.getId()).append("\n");
        receiptText.append("Roll Number: ").append(student.getRollNumber()).append("\n");
        receiptText.append("Department: ").append(student.getDepartment()).append("\n\n");
        
        receiptText.append("Payment Details:\n");
        receiptText.append("------------------\n");
        
        int i = 1;
        for (FeePayment payment : payments) {
            receiptText.append(i).append(". ");
            receiptText.append(payment.getPaymentType()).append(": $");
            receiptText.append(String.format("%.2f", payment.getAmount())).append(" - ");
            receiptText.append(payment.isPaid() ? "PAID" : "PENDING");
            receiptText.append(" (").append(dateFormat.format(payment.getDate())).append(")");
            receiptText.append("\n");
            i++;
        }
        
        receiptText.append("\nTotal Amount: $").append(String.format("%.2f", totalAmount)).append("\n");
        receiptText.append("Payment Method: ").append(paymentMethod).append("\n");
        receiptText.append("Status: ").append(status).append("\n\n");
        
        if (!remarks.isEmpty()) {
            receiptText.append("Remarks: ").append(remarks).append("\n\n");
        }
        
        receiptText.append("Issued By: ").append(issuedBy).append("\n");
        receiptText.append("======================================\n");
        receiptText.append("Thank you for your payment!\n");
        receiptText.append("======================================\n");
        
        return receiptText.toString();
    }
    
    // Getters and setters
    public String getReceiptNumber() {
        return receiptNumber;
    }
    
    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }
    
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public List<FeePayment> getPayments() {
        return payments;
    }
    
    public void setPayments(List<FeePayment> payments) {
        this.payments = payments;
        
        // Recalculate the total amount
        this.totalAmount = 0.0;
        for (FeePayment payment : payments) {
            this.totalAmount += payment.getAmount();
        }
        
        // Update status
        updateStatus();
    }
    
    public Date getIssueDate() {
        return issueDate;
    }
    
    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public String getIssuedBy() {
        return issuedBy;
    }
    
    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    /**
     * Generates a physical receipt file and saves it to the specified path
     * @param filePath Path where the receipt file should be saved
     * @return boolean indicating if the file was successfully created
     */
    public boolean saveReceiptToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write the receipt text to the file
            writer.write(generateReceiptText());
            // Success is reported by the return value, not printed here
            return true;
        } catch (IOException e) {
            System.err.println("Error generating receipt file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
