import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * AcademicRecord class to track a student's complete academic history
 * Implements Serializable for file storage
 */
public class AcademicRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Student student;
    private List<Grade> grades;
    private Map<String, List<Grade>> semesterGrades; // Organized by semester
    private Map<Subject, List<Grade>> subjectGrades; // Organized by subject
    private Date lastUpdated;
    
    // Constructor
    public AcademicRecord(Student student) {
        this.student = student;
        this.grades = new ArrayList<>();
        this.semesterGrades = new HashMap<>();
        this.subjectGrades = new HashMap<>();
        this.lastUpdated = new Date();
    }
    
    // Add a new grade
    public void addGrade(Grade grade) {
        // Set student if not already set
        if (grade.getStudent() == null) {
            grade.setStudent(this.student);
        }
        
        // Add to the list of all grades
        grades.add(grade);
        
        // Add to semester map
        String semester = grade.getSemester();
        if (!semesterGrades.containsKey(semester)) {
            semesterGrades.put(semester, new ArrayList<>());
        }
        semesterGrades.get(semester).add(grade);
        
        // Add to subject map
        Subject subject = grade.getSubject();
        if (!subjectGrades.containsKey(subject)) {
            subjectGrades.put(subject, new ArrayList<>());
        }
        subjectGrades.get(subject).add(grade);
        
        // Update timestamp
        this.lastUpdated = new Date();
    }
    
    // Calculate cumulative GPA across all grades
    public double calculateCumulativeGPA() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        
        double totalPoints = 0.0;
        int totalCourses = 0;
        
        // Get all unique semester-subject combinations
        Map<String, Set<Subject>> semesterSubjects = new HashMap<>();
        for (Grade grade : grades) {
            String semester = grade.getSemester();
            Subject subject = grade.getSubject();
            
            if (!semesterSubjects.containsKey(semester)) {
                semesterSubjects.put(semester, new HashSet<>());
            }
            semesterSubjects.get(semester).add(subject);
        }
        
        // Calculate overall grade for each subject in each semester
        for (String semester : semesterSubjects.keySet()) {
            for (Subject subject : semesterSubjects.get(semester)) {
                Grade overallGrade = calculateOverallSubjectGrade(subject, semester);
                if (overallGrade != null) {
                    totalPoints += overallGrade.getGradePoints();
                    totalCourses++;
                }
            }
        }
        
        return totalCourses > 0 ? totalPoints / totalCourses : 0.0;
    }
    
    // Calculate GPA for a specific semester
    public double calculateSemesterGPA(String semester) {
        List<Grade> semGrades = semesterGrades.get(semester);
        
        if (semGrades == null || semGrades.isEmpty()) {
            return 0.0;
        }
        
        double totalPoints = 0.0;
        int totalCourses = 0;
        
        // Get all subjects for this semester
        Set<Subject> subjects = new HashSet<>();
        for (Grade grade : semGrades) {
            subjects.add(grade.getSubject());
        }
        
        // Calculate overall grade for each subject
        for (Subject subject : subjects) {
            Grade overallGrade = calculateOverallSubjectGrade(subject, semester);
            if (overallGrade != null) {
                totalPoints += overallGrade.getGradePoints();
                totalCourses++;
            }
        }
        
        return totalCourses > 0 ? totalPoints / totalCourses : 0.0;
    }
    
    // Calculate overall grade for a subject
    public Grade calculateOverallSubjectGrade(Subject subject, String semester) {
        List<Grade> subjectGradeList = subjectGrades.get(subject);
        if (subjectGradeList == null || subjectGradeList.isEmpty()) {
            return null;
        }
        
        double totalWeightedScore = 0.0;
        double totalWeight = 0.0;
        
        // Weight distribution
        Map<String, Double> weights = new HashMap<>();
        weights.put("Quiz", 0.15);       // 15% for quizzes
        weights.put("Assignment", 0.15);  // 15% for assignments
        weights.put("Midterm", 0.30);    // 30% for midterm
        weights.put("Final", 0.40);      // 40% for final exam
        
        // Calculate weighted average
        for (Grade grade : subjectGradeList) {
            if (grade.getSemester().equals(semester)) {
                String type = grade.getGradeType();
                if (weights.containsKey(type)) {
                    double weight = weights.get(type);
                    totalWeightedScore += grade.getPercentageScore() * weight;
                    totalWeight += weight;
                }
            }
        }
        
        // If no grades found for this semester
        if (totalWeight == 0) {
            return null;
        }
        
        // Create an overall grade object
        Grade overallGrade = new Grade();
        overallGrade.setStudent(student);
        overallGrade.setSubject(subject);
        overallGrade.setGradeType("Course");
        overallGrade.setSemester(semester);
        
        // Calculate final score
        double finalPercentage = totalWeightedScore / totalWeight;
        overallGrade.setScore(finalPercentage);
        overallGrade.setMaxScore(100);
        
        return overallGrade;
    }
    
    // Generate transcript
    public String generateTranscript() {
        StringBuilder transcript = new StringBuilder();
        
        transcript.append("ACADEMIC TRANSCRIPT\n");
        transcript.append("==================\n\n");
        transcript.append("Student: ").append(student.getName()).append("\n");
        transcript.append("ID: ").append(student.getId()).append("\n");
        transcript.append("Department: ").append(student.getDepartment()).append("\n");
        transcript.append("Cumulative GPA: ").append(String.format("%.2f", calculateCumulativeGPA())).append("\n\n");
        
        // List grades by semester
        for (String semester : semesterGrades.keySet()) {
            transcript.append("SEMESTER: ").append(semester).append("\n");
            transcript.append("GPA: ").append(String.format("%.2f", calculateSemesterGPA(semester))).append("\n");
            transcript.append("Courses:\n");
            
            // Get all subjects for this semester
            Set<Subject> semesterSubjects = new HashSet<>();
            for (Grade grade : semesterGrades.get(semester)) {
                semesterSubjects.add(grade.getSubject());
            }
            
            // Display each subject's overall grade
            for (Subject subject : semesterSubjects) {
                Grade overallGrade = calculateOverallSubjectGrade(subject, semester);
                if (overallGrade != null) {
                    transcript.append("  - ").append(subject.getName())
                              .append(": ").append(overallGrade.getLetterGrade())
                              .append(" (").append(String.format("%.2f", overallGrade.getPercentageScore())).append("%)")
                              .append("\n");
                }
            }
            
            transcript.append("\n");
        }
        
        transcript.append("Last Updated: ").append(new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(lastUpdated));
        return transcript.toString();
    }
    
    // Getters and setters
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public List<Grade> getGrades() {
        return grades;
    }
    
    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }
    
    public Map<String, List<Grade>> getSemesterGrades() {
        return semesterGrades;
    }
    
    public void setSemesterGrades(Map<String, List<Grade>> semesterGrades) {
        this.semesterGrades = semesterGrades;
    }
    
    public Map<Subject, List<Grade>> getSubjectGrades() {
        return subjectGrades;
    }
    
    public void setSubjectGrades(Map<Subject, List<Grade>> subjectGrades) {
        this.subjectGrades = subjectGrades;
    }
    
    public Date getLastUpdated() {
        return lastUpdated;
    }
    
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
