
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Teacher class that extends Person
 * Represents a teacher in the educational institution
 */
public class Teacher extends Person {
    private static final long serialVersionUID = 1L;
    
    private int teacherId;
    private List<Subject> subjects;
    private String specialization;
    private Date joinDate;
    private String department;
    
    // Constructor
    public Teacher(int id, String name, String address, String contactNumber, Date dateOfBirth,
                  int teacherId, String specialization, Date joinDate, String department) {
        super(id, name, address, contactNumber, dateOfBirth);
        this.teacherId = teacherId;
        this.specialization = specialization;
        this.joinDate = joinDate;
        this.subjects = new ArrayList<>();
        this.department = department;
    }
    
    // Default constructor
    public Teacher() {
        super();
        this.teacherId = 0;
        this.specialization = "";
        this.joinDate = new Date();
        this.subjects = new ArrayList<>();
        this.department = "";
    }
    
    // Getters and setters
    public int getTeacherId() {
        return teacherId;
    }
    
    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
    
    public List<Subject> getSubjects() {
        return subjects;
    }
    
    public void addSubject(Subject subject) {
        if (!subjects.contains(subject)) {
            subjects.add(subject);
        }
    }
    
    public void removeSubject(Subject subject) {
        subjects.remove(subject);
    }
    
    public String getSpecialization() {
        return specialization;
    }
    
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    
    public Date getJoinDate() {
        return joinDate;
    }
    
    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    // Get formatted join date
    public String getFormattedJoinDate() {
        if (joinDate == null) return "Not set";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(joinDate);
    }
    
    @Override
    public String getDetails() {
        return toString();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("\nTeacher ID: ").append(teacherId);
        sb.append("\nDepartment: ").append(department);
        sb.append("\nSpecialization: ").append(specialization);
        sb.append("\nJoin Date: ").append(getFormattedJoinDate());
        sb.append("\nSubjects Teaching: ");
        
        if (subjects.isEmpty()) {
            sb.append("None assigned");
        } else {
            for (Subject subject : subjects) {
                sb.append("\n  - ").append(subject.getName());
            }
        }
        
        return sb.toString();
    }
}
