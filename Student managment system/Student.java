
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Student class that extends Person
 * Represents a student in the educational institution
 */
public class Student extends Person {
    private static final long serialVersionUID = 1L;
    
    private String rollNumber;
    private List<Course> courses;
    private List<FeePayment> feePayments;
    private List<Attendance> attendanceRecords;
    private String gender;  // For report generation based on gender
    private String department;  // For department-based reporting
    private double gpa;  // For academic status (warning, dean's list)
    
    // Constructor
    public Student(int id, String name, String address, String contactNumber, Date dateOfBirth,
                  String rollNumber, String gender, String department) {
        super(id, name, address, contactNumber, dateOfBirth);
        this.rollNumber = rollNumber;
        this.gender = gender;
        this.department = department;
        this.courses = new ArrayList<>();
        this.feePayments = new ArrayList<>();
        this.attendanceRecords = new ArrayList<>();
        this.gpa = 0.0;
    }
    
    // Default constructor
    public Student() {
        super();
        this.rollNumber = "";
        this.gender = "";
        this.department = "";
        this.courses = new ArrayList<>();
        this.feePayments = new ArrayList<>();
        this.attendanceRecords = new ArrayList<>();
        this.gpa = 0.0;
    }
    
    // Getters and setters
    public String getRollNumber() {
        return rollNumber;
    }
    
    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }
    
    public List<Course> getCourses() {
        return courses;
    }
    
    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }
    
    public void removeCourse(Course course) {
        courses.remove(course);
    }
    
    public List<FeePayment> getFeePayments() {
        return feePayments;
    }
    
    public void addFeePayment(FeePayment payment) {
        feePayments.add(payment);
    }
    
    public List<Attendance> getAttendanceRecords() {
        return attendanceRecords;
    }
    
    public void addAttendanceRecord(Attendance attendance) {
        attendanceRecords.add(attendance);
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public double getGpa() {
        return gpa;
    }
    
    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
    
    // Check if student has academic warning based on GPA
    public boolean hasWarning() {
        return gpa < 2.0; // Assuming 2.0 is the warning threshold
    }
    
    // Check if student is on Dean's List
    public boolean isOnDeansList() {
        return gpa >= 3.5; // Assuming 3.5 is the Dean's List threshold
    }
    
    // Calculate total fees paid
    public double getTotalFeesPaid() {
        double total = 0;
        for (FeePayment payment : feePayments) {
            if (payment.isPaid()) {
                total += payment.getAmount();
            }
        }
        return total;
    }
    
    // Calculate total outstanding fees
    public double getOutstandingFees() {
        double total = 0;
        for (FeePayment payment : feePayments) {
            if (!payment.isPaid()) {
                total += payment.getAmount();
            }
        }
        return total;
    }
    
    // Calculate attendance percentage for a subject
    public double getAttendancePercentage(Subject subject) {
        int totalClasses = 0;
        int presentClasses = 0;
        
        for (Attendance attendance : attendanceRecords) {
            if (attendance.getSubject().equals(subject)) {
                totalClasses++;
                if (attendance.isPresent()) {
                    presentClasses++;
                }
            }
        }
        
        return totalClasses > 0 ? ((double) presentClasses / totalClasses) * 100 : 0;
    }
    
    @Override
    public String getDetails() {
        return toString();
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               "\nRoll Number: " + rollNumber +
               "\nGender: " + gender +
               "\nDepartment: " + department +
               "\nGPA: " + gpa +
               "\nNumber of Courses: " + courses.size() +
               "\nOutstanding Fees: $" + getOutstandingFees();
    }
}
