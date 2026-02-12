import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Course class for managing courses in the educational institution
 */
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int courseId;
    private String name;
    private List<Subject> subjects;
    private String department;
    private int creditHours;
    
    // Constructor
    public Course(int courseId, String name, String department, int creditHours) {
        this.courseId = courseId;
        this.name = name;
        this.department = department;
        this.creditHours = creditHours;
        this.subjects = new ArrayList<>();
    }
    
    // Default constructor
    public Course() {
        this.courseId = 0;
        this.name = "";
        this.department = "";
        this.creditHours = 0;
        this.subjects = new ArrayList<>();
    }
    
    // Getters and setters
    public int getCourseId() {
        return courseId;
    }
    
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
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
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public int getCreditHours() {
        return creditHours;
    }
    
    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Course ID: ").append(courseId);
        sb.append("\nName: ").append(name);
        sb.append("\nDepartment: ").append(department);
        sb.append("\nCredit Hours: ").append(creditHours);
        sb.append("\nSubjects: ");
        
        if (subjects.isEmpty()) {
            sb.append("None assigned");
        } else {
            for (Subject subject : subjects) {
                sb.append("\n  - ").append(subject.getName());
            }
        }
        
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return courseId == course.courseId;
    }
    
    @Override
    public int hashCode() {
        return courseId;
    }
}
