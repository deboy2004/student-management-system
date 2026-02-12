import java.io.Serializable;

/**
 * Subject class for managing subjects in the courses
 */
public class Subject implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int subjectId;
    private String name;
    private Teacher teacher;
    private int creditHours;
    private String description;
    
    // Constructor
    public Subject(int subjectId, String name, Teacher teacher, int creditHours, String description) {
        this.subjectId = subjectId;
        this.name = name;
        this.teacher = teacher;
        this.creditHours = creditHours;
        this.description = description;
    }
    
    // Default constructor
    public Subject() {
        this.subjectId = 0;
        this.name = "";
        this.teacher = null;
        this.creditHours = 0;
        this.description = "";
    }
    
    // Getters and setters
    public int getSubjectId() {
        return subjectId;
    }
    
    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Teacher getTeacher() {
        return teacher;
    }
    
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
        // Add this subject to the teacher's subjects list
        if (teacher != null) {
            teacher.addSubject(this);
        }
    }
    
    public int getCreditHours() {
        return creditHours;
    }
    
    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Subject ID: ").append(subjectId);
        sb.append("\nName: ").append(name);
        sb.append("\nCredit Hours: ").append(creditHours);
        sb.append("\nDescription: ").append(description);
        sb.append("\nTeacher: ");
        if (teacher == null) {
            sb.append("Not assigned");
        } else {
            sb.append(teacher.getName());
        }
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Subject subject = (Subject) obj;
        return subjectId == subject.subjectId;
    }
    
    @Override
    public int hashCode() {
        return subjectId;
    }
}
