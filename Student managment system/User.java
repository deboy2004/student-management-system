import java.io.Serializable;

/**
 * User class for authentication and access control
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String username;
    private String password;
    private String fullName;
    private String role;  // Administrator, Teacher, Financial Officer, Registrar, Student, User
    private String department; // For teachers to manage their department
    private Integer associatedId; // Student or Teacher ID associated with this user
    
    // Constructor
    public User(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = "User"; // Default role
        this.department = null;
        this.associatedId = null;
    }
    
    // Constructor with role
    public User(String username, String password, String fullName, String role, String department, Integer associatedId) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.department = department;
        this.associatedId = associatedId;
    }
    
    // Default constructor
    public User() {
        this.username = "";
        this.password = "";
        this.fullName = "";
        this.role = "";
        this.department = null;
        this.associatedId = null;
    }
    
    // Getters and setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    // Role check methods
    public boolean isAdmin() {
        return role.equalsIgnoreCase("Administrator");
    }
    
    public boolean isTeacher() {
        return role.equalsIgnoreCase("Teacher");
    }
    
    public boolean isFinancialOfficer() {
        return role.equalsIgnoreCase("Financial Officer");
    }
    
    public boolean isRegistrar() {
        return role.equalsIgnoreCase("Registrar");
    }
    
    public boolean isStudent() {
        return role.equalsIgnoreCase("Student");
    }
    
    public boolean isUser() {
        return false; // Regular user role is not used in the system
    }
    
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getAssociatedId() {
        return associatedId;
    }

    public void setAssociatedId(Integer associatedId) {
        this.associatedId = associatedId;
    }

    public boolean hasPermission(String permission) {
        // Permission system based on specific roles
        switch (permission) {
            case "manage_users":
                return isAdmin(); // Only admins can manage users
                
            case "manage_students":
                return isAdmin() || // Admin can manage all students
                       (isRegistrar() && hasDepartmentAccess()); // Registrar can manage students in their department
                
            case "manage_teachers":
                return isAdmin() || // Admin can manage all teachers
                       (isRegistrar() && hasDepartmentAccess()); // Registrar can manage teachers in their department
                
            case "manage_courses":
                return isAdmin() || // Admin can manage all courses
                       (isRegistrar() && hasDepartmentAccess()) || // Registrar can manage courses in their department
                       (isTeacher() && hasDepartmentAccess()); // Teachers can manage courses in their department
                
            case "manage_fees":
                return isAdmin() || isFinancialOfficer(); // Only admin and financial officers can manage fees
                
            case "manage_grades":
                return isAdmin() || // Admin can manage all grades
                       (isTeacher() && hasDepartmentAccess()); // Teachers can manage grades for their department
                
            case "manage_attendance":
                return isAdmin() || // Admin can manage all attendance
                       (isTeacher() && hasDepartmentAccess()); // Teachers can manage attendance for their department
                
            case "view_student":
                return isAdmin() || // Admin can view all students
                       (isRegistrar() && hasDepartmentAccess()) || // Registrar can view students in their department
                       (isTeacher() && hasDepartmentAccess()) || // Teachers can view students in their department
                       (isFinancialOfficer()) || // Financial officers can view student info for fees
                       (isStudent() && hasStudentSelfAccess()); // Students can view themselves
                
            case "view_grades":
                return isAdmin() || // Admin can view all grades
                       (isRegistrar() && hasDepartmentAccess()) || // Registrar can view grades in their department
                       (isTeacher() && hasDepartmentAccess()) || // Teachers can view grades in their department
                       (isStudent() && hasStudentSelfAccess()); // Students can view their own grades
                
            case "view_attendance":
                return isAdmin() || // Admin can view all attendance
                       (isRegistrar() && hasDepartmentAccess()) || // Registrar can view attendance in their department
                       (isTeacher() && hasDepartmentAccess()) || // Teachers can view attendance in their department
                       (isStudent() && hasStudentSelfAccess()); // Students can view their own attendance
                
            case "view_fees":
                return isAdmin() || // Admin can view all fees
                       isFinancialOfficer() || // Financial officers can view all fees
                       (isRegistrar() && hasDepartmentAccess()) || // Registrar can view fees in their department
                       (isStudent() && hasStudentSelfAccess()); // Students can view their own fees
                
            case "generate_reports":
                return isAdmin() || // Admin can generate all reports
                       isFinancialOfficer() || // Financial officers can generate fee reports
                       (isRegistrar() && hasDepartmentAccess()) || // Registrar can generate reports for their department
                       (isTeacher() && hasDepartmentAccess()); // Teachers can generate reports for their department
                
            case "view_reports":
                return true; // All roles can view reports
                
            case "generate_transcripts":
                return isAdmin() || isRegistrar(); // Only these roles can generate official transcripts
                
            default:
                System.out.println("Invalid role selected. Please try again.");
                return false;
        }
    }

    // Check if a teacher has access to a specific department
    public boolean hasDepartmentAccess() {
        if (!isTeacher() || department == null) {
            return false;
        }
        return true; // Will be checked against student's department in business logic
    }

    // Check if a student has access to view their own information
    public boolean hasStudentSelfAccess() {
        if (!isStudent() || associatedId == null) {
            return false;
        }
        return true; // Will be checked against requested student ID in business logic
    }

    // Check if user has access to a specific student
    public boolean canAccessStudent(Student student) {
        if (student == null) return false;
        
        if (isAdmin()) {
            return true; // Admin can access all students
        }
        
        if (isRegistrar()) {
            return student.getDepartment().equals(this.department); // Registrar can only access students in their department
        }
        
        if (isTeacher()) {
            return student.getDepartment().equals(this.department); // Teacher can only access students in their department
        }
        
        if (isFinancialOfficer()) {
            // Financial officers can access student records but with limited information
            return true;
        }
        
        if (isStudent()) {
            return student.getId() == this.associatedId; // Student can only access their own information
        }
        
        return false;
    }

    // Check if user has access to a specific teacher
    public boolean canAccessTeacher(Teacher teacher) {
        if (teacher == null) return false;

        if (isAdmin()) {
            return true; // Admin can access all teachers
        }

        if (isRegistrar()) {
            return teacher.getDepartment().equals(this.department); // Registrar can only access teachers in their department
        }

        if (isTeacher()) {
            // Teachers can view other teachers in their department
            return teacher.getDepartment().equals(this.department);
        }

        if (isStudent()) {
            // Students can only view teachers who teach their courses
            Student student = findStudentById(this.associatedId);
            if (student != null) {
                return student.getCourses().stream()
                    .flatMap(course -> course.getSubjects().stream())
                    .anyMatch(subject -> subject.getTeacher() != null && 
                                       subject.getTeacher().getId() == teacher.getId());
            }
        }

        return false;
    }

    // Check if user has access to a specific course
    public boolean canAccessCourse(Course course) {
        if (course == null) return false;

        if (isAdmin()) {
            return true; // Admin can access all courses
        }

        if (isRegistrar()) {
            return course.getDepartment().equals(this.department); // Registrar can only access courses in their department
        }

        if (isTeacher()) {
            // Teachers can access courses in their department
            return course.getDepartment().equals(this.department);
        }

        if (isStudent()) {
            // Students can only view courses they are enrolled in
            Student student = findStudentById(this.associatedId);
            if (student != null) {
                return student.getCourses().contains(course);
            }
        }

        return false;
    }

    // Check if user has access to fee information
    public boolean canAccessFeeInfo(Student student) {
        if (student == null) return false;

        if (isAdmin() || isFinancialOfficer()) {
            return true; // Admin and financial officers can access all fee information
        }

        if (isRegistrar()) {
            // Registrars can view but not modify fee information for their department
            return student.getDepartment().equals(this.department);
        }

        if (isStudent()) {
            // Students can only view their own fee information
            return student.getId() == this.associatedId;
        }

        return false;
    }

    // Helper method to find student by ID
    private Student findStudentById(int id) {
        // This should be implemented to actually find the student
        // For now, return null as placeholder
        return null;
    }
    
    @Override
    public String toString() {
        return "Username: " + username + 
               "\nRole: " + role;
    }
}
