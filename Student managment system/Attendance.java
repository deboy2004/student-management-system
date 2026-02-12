import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Attendance class to track student attendance for subjects
 */
public class Attendance implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Student student;
    private Subject subject;
    private Date date;
    private boolean isPresent;
    private String remarks;
    
    // Constructor
    public Attendance(Student student, Subject subject, Date date, boolean isPresent, String remarks) {
        this.student = student;
        this.subject = subject;
        this.date = date;
        this.isPresent = isPresent;
        this.remarks = remarks;
    }
    
    // Default constructor
    public Attendance() {
        this.student = null;
        this.subject = null;
        this.date = new Date();
        this.isPresent = false;
        this.remarks = "";
    }
    
    // Getters and setters
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public Subject getSubject() {
        return subject;
    }
    
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public boolean isPresent() {
        return isPresent;
    }
    
    public void setPresent(boolean present) {
        isPresent = present;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
               "\nSubject: " + (subject != null ? subject.getName() : "Not assigned") +
               "\nDate: " + getFormattedDate() +
               "\nStatus: " + (isPresent ? "Present" : "Absent") +
               "\nRemarks: " + remarks;
    }
}
