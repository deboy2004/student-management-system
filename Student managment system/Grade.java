import java.io.Serializable;
import java.util.Date;

/**
 * Grade class to track individual grades for assignments, exams, and other academic work
 * Implements Serializable for file storage
 */
public class Grade implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Student student;
    private Subject subject;
    private String assignmentName;
    private String gradeType; // Exam, Quiz, Assignment, Project, etc.
    private double score;
    private double maxScore;
    private Date date;
    private String remarks;
    private String semester; // Spring 2025, Fall 2024, etc.
    
    // Constructor
    public Grade(Student student, Subject subject, String assignmentName, String gradeType, 
                double score, double maxScore, Date date, String remarks, String semester) {
        this.student = student;
        this.subject = subject;
        this.assignmentName = assignmentName;
        this.gradeType = gradeType;
        this.score = score;
        this.maxScore = maxScore;
        this.date = date;
        this.remarks = remarks;
        this.semester = semester;
    }
    
    // Default constructor
    public Grade() {
        this.student = null;
        this.subject = null;
        this.assignmentName = "";
        this.gradeType = "";
        this.score = 0.0;
        this.maxScore = 100.0;
        this.date = new Date();
        this.remarks = "";
        this.semester = "";
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
    
    public String getAssignmentName() {
        return assignmentName;
    }
    
    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }
    
    public String getGradeType() {
        return gradeType;
    }
    
    public void setGradeType(String gradeType) {
        this.gradeType = gradeType;
    }
    
    public double getScore() {
        return score;
    }
    
    public void setScore(double score) {
        this.score = score;
    }
    
    public double getMaxScore() {
        return maxScore;
    }
    
    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public String getSemester() {
        return semester;
    }
    
    public void setSemester(String semester) {
        this.semester = semester;
    }
    
    // Calculate percentage score
    public double getPercentageScore() {
        return (score / maxScore) * 100;
    }
    
    // Calculate letter grade based on percentage
    public String getLetterGrade() {
        double percentage = getPercentageScore();
        
        if (percentage >= 90) return "A";
        else if (percentage >= 80) return "B";
        else if (percentage >= 70) return "C";
        else if (percentage >= 60) return "D";
        else return "F";
    }
    
    // Get grade points (for GPA calculation)
    public double getGradePoints() {
        String letter = getLetterGrade();
        
        switch (letter) {
            case "A": return 4.0;
            case "B": return 3.0;
            case "C": return 2.0;
            case "D": return 1.0;
            default: return 0.0;
        }
    }
    
    @Override
    public String toString() {
        return "Student: " + (student != null ? student.getName() : "Not assigned") +
               "\nSubject: " + (subject != null ? subject.getName() : "Not assigned") +
               "\nAssignment: " + assignmentName +
               "\nType: " + gradeType +
               "\nScore: " + score + "/" + maxScore + " (" + String.format("%.2f", getPercentageScore()) + "%)" +
               "\nLetter Grade: " + getLetterGrade() +
               "\nDate: " + (date != null ? new java.text.SimpleDateFormat("dd/MM/yyyy").format(date) : "Not set") +
               "\nSemester: " + semester +
               "\nRemarks: " + remarks;
    }
}
