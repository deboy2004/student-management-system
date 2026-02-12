import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Main class that manages the Student Information System
 * Contains lists of all entities and methods to manipulate them
 */
public class StudentInformationSystem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Lists to store data
    private List<Student> students;
    private List<Teacher> teachers;
    private List<Course> courses;
    private List<Subject> subjects;
    private List<Attendance> attendances;
    private List<FeePayment> feePayments;
    private List<User> users;
    private List<Grade> grades;
    private Map<Integer, AcademicRecord> academicRecords; // Mapped by student ID
    private List<FeeReceipt> feeReceipts;
    
    // Constructor
    public StudentInformationSystem() {
        this.students = new ArrayList<>();
        this.teachers = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.subjects = new ArrayList<>();
        this.attendances = new ArrayList<>();
        this.feePayments = new ArrayList<>();
        this.users = new ArrayList<>();
        this.grades = new ArrayList<>();
        this.academicRecords = new HashMap<>();
        this.feeReceipts = new ArrayList<>();
        
        // Add default admin user
        users.add(new User("admin", "admin", "Administrator", "Administrator", null, null));
        // Add default teacher user
        users.add(new User("teacher", "teacher", "Default Teacher", "Teacher", "Computer Science", null));
        // Add default financial officer user
        users.add(new User("finance", "finance", "Financial Officer", "Financial Officer", null, null));
        // Add default registrar user
        users.add(new User("registrar", "registrar", "Registrar", "Registrar", null, null));
    }
    
    // Methods for Student management
    public void registerStudent(Student student) {
        students.add(student);
    }
    
    public void updateStudent(Student student) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == student.getId()) {
                students.set(i, student);
                break;
            }
        }
    }
    
    public void deleteStudent(Student student) {
        students.removeIf(s -> s.getId() == student.getId());
    }
    
    private User currentUser;

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Student findStudentById(int id) {
        Student student = students.stream()
            .filter(s -> s.getId() == id)
            .findFirst()
            .orElse(null);

        if (student != null && currentUser != null && !currentUser.canAccessStudent(student)) {
            return null; // User doesn't have permission to access this student
        }

        return student;
    }
    
    public Student findStudentByRollNumber(String rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber().equals(rollNumber)) {
                return student;
            }
        }
        return null;
    }
    
    public List<Student> searchStudentsByName(String name) {
        return students.stream()
                .filter(s -> s.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(s -> currentUser == null || currentUser.canAccessStudent(s))
                .collect(Collectors.toList());
    }
    
    // Methods for Teacher management
    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }
    
    public void updateTeacher(Teacher teacher) {
        for (int i = 0; i < teachers.size(); i++) {
            if (teachers.get(i).getId() == teacher.getId()) {
                teachers.set(i, teacher);
                break;
            }
        }
    }
    
    public void deleteTeacher(Teacher teacher) {
        teachers.removeIf(t -> t.getId() == teacher.getId());
    }
    
    public Teacher findTeacherById(int id) {
        for (Teacher teacher : teachers) {
            if (teacher.getId() == id) {
                return teacher;
            }
        }
        return null;
    }
    
    // Methods for Course management
    public void addCourse(Course course) {
        courses.add(course);
    }
    
    public void updateCourse(Course course) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCourseId() == course.getCourseId()) {
                courses.set(i, course);
                break;
            }
        }
    }
    
    public void deleteCourse(Course course) {
        courses.removeIf(c -> c.getCourseId() == course.getCourseId());
    }
    
    public Course findCourseById(int id) {
        for (Course course : courses) {
            if (course.getCourseId() == id) {
                return course;
            }
        }
        return null;
    }
    
    // Methods for Subject management
    public void addSubject(Subject subject) {
        subjects.add(subject);
    }
    
    public void updateSubject(Subject subject) {
        for (int i = 0; i < subjects.size(); i++) {
            if (subjects.get(i).getSubjectId() == subject.getSubjectId()) {
                subjects.set(i, subject);
                break;
            }
        }
    }
    
    public void deleteSubject(Subject subject) {
        subjects.removeIf(s -> s.getSubjectId() == subject.getSubjectId());
    }
    
    public Subject findSubjectById(int id) {
        for (Subject subject : subjects) {
            if (subject.getSubjectId() == id) {
                return subject;
            }
        }
        return null;
    }
    
    // Methods for Attendance management
    public void markAttendance(Attendance attendance) {
        attendances.add(attendance);
    }
    
    public List<Attendance> getAttendanceByStudent(Student student) {
        return attendances.stream()
                .filter(a -> a.getStudent().getId() == student.getId())
                .collect(Collectors.toList());
    }
    
    public List<Attendance> getAttendanceBySubject(Subject subject) {
        return attendances.stream()
                .filter(a -> a.getSubject().getSubjectId() == subject.getSubjectId())
                .collect(Collectors.toList());
    }
    
    public List<Attendance> getAttendanceByDate(Date date) {
        return attendances.stream()
                .filter(a -> a.getDate().equals(date))
                .collect(Collectors.toList());
    }
    
    // Methods for Fee Payment management
    public void recordFeePayment(FeePayment feePayment) {
        feePayments.add(feePayment);
    }
    
    public List<FeePayment> getFeePaymentsByStudent(Student student) {
        return feePayments.stream()
                .filter(f -> f.getStudent().getId() == student.getId())
                .collect(Collectors.toList());
    }
    
    public List<FeePayment> getUnpaidFeePayments() {
        return feePayments.stream()
                .filter(f -> !f.isPaid())
                .collect(Collectors.toList());
    }
    
    // Methods for Fee Receipt management
    public void addFeeReceipt(FeeReceipt receipt) {
        feeReceipts.add(receipt);
    }
    
    public List<FeeReceipt> getFeeReceiptsByStudent(Student student) {
        return feeReceipts.stream()
                .filter(r -> r.getStudent().getId() == student.getId())
                .collect(Collectors.toList());
    }
    
    public FeeReceipt getFeeReceiptByNumber(String receiptNumber) {
        for (FeeReceipt receipt : feeReceipts) {
            if (receipt.getReceiptNumber().equals(receiptNumber)) {
                return receipt;
            }
        }
        return null;
    }
    
    public List<FeeReceipt> getFeeReceiptsByStatus(String status) {
        return feeReceipts.stream()
                .filter(r -> r.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }
    
    public List<FeeReceipt> getFeeReceiptsByDateRange(Date startDate, Date endDate) {
        return feeReceipts.stream()
                .filter(r -> !r.getIssueDate().before(startDate) && !r.getIssueDate().after(endDate))
                .collect(Collectors.toList());
    }
    
    public String generateFeeReceiptReport(List<FeeReceipt> receipts) {
        if (receipts == null || receipts.isEmpty()) {
            return "No receipts found for the specified criteria.";
        }
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder report = new StringBuilder();
        
        report.append("=============================================\n");
        report.append("             FEE RECEIPT REPORT             \n");
        report.append("=============================================\n\n");
        
        report.append(String.format("%-15s %-20s %-12s %-10s %-10s\n", 
                      "Receipt #", "Student", "Date", "Amount", "Status"));
        report.append("---------------------------------------------\n");
        
        double totalAmount = 0.0;
        int paidCount = 0;
        int partialCount = 0;
        int pendingCount = 0;
        
        for (FeeReceipt receipt : receipts) {
            report.append(String.format("%-15s %-20s %-12s $%-9.2f %-10s\n",
                         receipt.getReceiptNumber(),
                         receipt.getStudent().getName(),
                         dateFormat.format(receipt.getIssueDate()),
                         receipt.getTotalAmount(),
                         receipt.getStatus()));
            
            totalAmount += receipt.getTotalAmount();
            
            switch (receipt.getStatus().toLowerCase()) {
                case "paid":
                    paidCount++;
                    break;
                case "partial":
                    partialCount++;
                    break;
                case "pending":
                    pendingCount++;
                    break;
            }
        }
        
        report.append("\n---------------------------------------------\n");
        report.append("Total Receipts: ").append(receipts.size()).append("\n");
        report.append("Total Amount: $").append(String.format("%.2f", totalAmount)).append("\n\n");
        
        report.append("Status Summary:\n");
        report.append("  Paid: ").append(paidCount).append("\n");
        report.append("  Partial: ").append(partialCount).append("\n");
        report.append("  Pending: ").append(pendingCount).append("\n");
        
        report.append("\n=============================================\n");
        report.append("Report Generated: ").append(dateFormat.format(new Date())).append("\n");
        report.append("=============================================\n");
        
        return report.toString();
    }
    
    public String generateOutstandingFeesReport() {
        List<FeeReceipt> pendingReceipts = getFeeReceiptsByStatus("Pending");
        pendingReceipts.addAll(getFeeReceiptsByStatus("Partial"));
        
        return generateFeeReceiptReport(pendingReceipts);
    }
    
    // Methods for User management
    public void addUser(User user) {
        users.add(user);
    }
    
    public User authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
    
    public User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    
    // Get list of students with warning (GPA < 2.0)
    public List<Student> getStudentsWithWarning() {
        return students.stream()
                .filter(s -> s.getGpa() < 2.0)
                .collect(Collectors.toList());
    }
    
    // Get list of students on Dean's List (GPA >= 3.5)
    public List<Student> getStudentsOnDeansList() {
        return students.stream()
                .filter(s -> s.getGpa() >= 3.5)
                .collect(Collectors.toList());
    }
    
    // Get list of female students in a specific department
    public List<Student> getFemaleStudentsByDepartment(String department) {
        return students.stream()
                .filter(s -> s.getGender().equalsIgnoreCase("female") && s.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }
    
    // Get list of male students in a specific department
    public List<Student> getMaleStudentsByDepartment(String department) {
        return students.stream()
                .filter(s -> s.getGender().equalsIgnoreCase("male") && s.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }
    
    // Get all students in a specific department
    public List<Student> getStudentsByDepartment(String department) {
        return students.stream()
                .filter(s -> s.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }
    
    // Get all students in a specific course
    public List<Student> getStudentsByCourse(Course course) {
        return students.stream()
                .filter(s -> s.getCourses().contains(course))
                .collect(Collectors.toList());
    }
    
    // Methods for Grade management
    public void addGrade(Grade grade) {
        grades.add(grade);
        
        // Update the student's academic record
        Student student = grade.getStudent();
        if (student != null) {
            AcademicRecord record = getAcademicRecord(student);
            record.addGrade(grade);
            academicRecords.put(student.getId(), record);
            
            // Update student GPA
            double gpa = record.calculateCumulativeGPA();
            student.setGpa(gpa);
        }
    }
    
    public List<Grade> getGradesByStudent(Student student) {
        return grades.stream()
                .filter(g -> g.getStudent() != null && g.getStudent().getId() == student.getId())
                .collect(Collectors.toList());
    }
    
    public List<Grade> getGradesBySubject(Subject subject) {
        return grades.stream()
                .filter(g -> g.getSubject() != null && g.getSubject().getSubjectId() == subject.getSubjectId())
                .collect(Collectors.toList());
    }
    
    public List<Grade> getGradesBySemester(String semester) {
        return grades.stream()
                .filter(g -> g.getSemester().equals(semester))
                .collect(Collectors.toList());
    }
    
    // Methods for Academic Record management
    public AcademicRecord getAcademicRecord(Student student) {
        if (!academicRecords.containsKey(student.getId())) {
            academicRecords.put(student.getId(), new AcademicRecord(student));
        }
        return academicRecords.get(student.getId());
    }
    
    public String generateTranscript(Student student) {
        AcademicRecord record = getAcademicRecord(student);
        return record.generateTranscript();
    }
    
    public List<Student> getStudentsWithGPARange(double minGPA, double maxGPA) {
        return students.stream()
                .filter(s -> s.getGpa() >= minGPA && s.getGpa() <= maxGPA)
                .collect(Collectors.toList());
    }
    
    // Getters for all lists
    public List<Student> getStudents() {
        if (currentUser == null) return new ArrayList<>();

        return students.stream()
                .filter(s -> currentUser.canAccessStudent(s))
                .collect(Collectors.toList());
    }
    
    public List<Teacher> getTeachers() {
        return teachers;
    }
    
    public List<Course> getCourses() {
        return courses;
    }
    
    public List<Subject> getSubjects() {
        return subjects;
    }
    
    public List<Attendance> getAttendances() {
        return attendances;
    }
    
    public List<FeePayment> getFeePayments() {
        return feePayments;
    }
    
    public List<User> getUsers() {
        return users;
    }
    
    public List<Grade> getGrades() {
        return grades;
    }
    
    public Map<Integer, AcademicRecord> getAcademicRecords() {
        return academicRecords;
    }
    
    public List<FeeReceipt> getFeeReceipts() {
        return feeReceipts;
    }
    
    // Save all data to files
    public void saveData() {
        try {
            FileOutputStream fileOut = new FileOutputStream("student_system.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.println("Data saved successfully.");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    
    // Load data from files
    public static StudentInformationSystem loadData() {
        StudentInformationSystem system = null;
        try {
            FileInputStream fileIn = new FileInputStream("student_system.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            system = (StudentInformationSystem) in.readObject();
            in.close();
            fileIn.close();
            // Data loaded silently
            
            // Ensure backwards compatibility with older versions that didn't have grades or academic records
            if (system.grades == null) {
                system.grades = new ArrayList<>();
            }
            
            if (system.academicRecords == null) {
                system.academicRecords = new HashMap<>();
            }
            
            if (system.feeReceipts == null) {
                system.feeReceipts = new ArrayList<>();
            }
            
        } catch (IOException i) {
            System.out.println("No existing data found. Creating a new system.");
            system = new StudentInformationSystem();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found.");
            c.printStackTrace();
            system = new StudentInformationSystem();
        }
        return system;
    }
}
