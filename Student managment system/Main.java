import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Main class for the Student Information System
 * Provides a menu-based interface for users to interact with the system
 */
public class Main {
    private static StudentInformationSystem system;
    private static Scanner scanner;
    private static SimpleDateFormat dateFormat;
    private static boolean loggedIn = false;
    
    public static void main(String[] args) {
        // Initialize system
        system = StudentInformationSystem.loadData();
        scanner = new Scanner(System.in);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        System.out.println("Welcome to Student Information Management System");
        
        // System initialized
        
        // Login
        while (!loggedIn) {
            loggedIn = login();
        }
        
        // Main menu loop
        boolean exit = false;
        while (!exit && loggedIn) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    if (system.getCurrentUser().hasPermission("manage_students")) {
                        studentManagementMenu();
                    } else {
                        System.out.println("Access denied. You don't have permission to access student management.");
                    }
                    break;
                case 2:
                    if (system.getCurrentUser().hasPermission("manage_teachers")) {
                        teacherManagementMenu();
                    } else {
                        System.out.println("Access denied. You don't have permission to access teacher management.");
                    }
                    break;
                case 3:
                    if (system.getCurrentUser().hasPermission("manage_courses")) {
                        courseManagementMenu();
                    } else {
                        System.out.println("Access denied. You don't have permission to access course management.");
                    }
                    break;
                case 4:
                    if (system.getCurrentUser().hasPermission("manage_attendance")) {
                        attendanceManagementMenu();
                    } else {
                        System.out.println("Access denied. You don't have permission to access attendance management.");
                    }
                    break;
                case 5:
                    if (system.getCurrentUser().hasPermission("manage_fees")) {
                        feeManagementMenu();
                    } else {
                        System.out.println("Access denied. You don't have permission to access fee management.");
                    }
                    break;
                case 6:
                    if (system.getCurrentUser().hasPermission("manage_grades")) {
                        academicManagementMenu();
                    } else {
                        System.out.println("Access denied. You don't have permission to access academic management.");
                    }
                    break;
                case 7:
                    reportsMenu();
                    break;
                case 8:
                    userManagementMenu();
                    break;
                case 9:
                    changePassword();
                    break;
                case 10:
                    loggedIn = false;
                    System.out.println("Logged out successfully.");
                    break;
                case 11:
                    exit = true;
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
    
    // Login method
    private static boolean login() {
        System.out.println("\n===== Login =====");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        User authenticatedUser = system.authenticateUser(username, password);
        if (authenticatedUser != null) {
            system.setCurrentUser(authenticatedUser);
            System.out.println("Login successful! Welcome, " + authenticatedUser.getFullName() + 
                             " (" + authenticatedUser.getRole() + ")");
            return true;
        } else {
            System.out.println("Invalid username or password. Please try again.");
            return false;
        }
    }
    
    // Display the main menu
    private static void displayMainMenu() {
        User currentUser = system.getCurrentUser();
        System.out.println("\n===== Main Menu =====");
        
        if (currentUser.hasPermission("manage_students")) {
            System.out.println("1. Student Management");
        }
        
        if (currentUser.hasPermission("manage_teachers")) {
            System.out.println("2. Teacher Management");
        }
        
        if (currentUser.hasPermission("manage_courses")) {
            System.out.println("3. Course Management");
        }
        
        if (currentUser.hasPermission("manage_attendance")) {
            System.out.println("4. Attendance Management");
        }
        
        if (currentUser.hasPermission("manage_fees")) {
            System.out.println("5. Fee Management");
        }
        
        if (currentUser.hasPermission("manage_grades")) {
            System.out.println("6. Academic Management");
        }
        
        System.out.println("7. Reports");
        System.out.println("8. User Management");
        System.out.println("9. Change Password");
        System.out.println("10. Logout");
        System.out.println("11. Exit");
    }
    
    // STUDENT MANAGEMENT METHODS
    
    // Student Management Menu
    private static void studentManagementMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n===== Student Management =====");
            System.out.println("1. Add New Student");
            System.out.println("2. Search Student");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Display All Students");
            System.out.println("6. Count Students");
            System.out.println("7. Sort Students");
            System.out.println("8. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addNewStudent();
                    break;
                case 2:
                    searchStudentMenu();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    displayAllStudents();
                    break;
                case 6:
                    countStudents();
                    break;
                case 7:
                    sortStudentsMenu();
                    break;
                case 8:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    // Add new student method
    private static void addNewStudent() {
        System.out.println("\n===== Add New Student =====");
        
        // Get the next available ID
        int nextId = system.getStudents().size() + 1;
        
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        
        System.out.print("Enter contact number: ");
        String contactNumber = scanner.nextLine();
        
        Date dateOfBirth = getDateInput("Enter date of birth");
        
        System.out.print("Enter roll number: ");
        String rollNumber = scanner.nextLine();
        
        System.out.print("Enter gender (Male/Female): ");
        String gender = scanner.nextLine();
        
        System.out.print("Enter department: ");
        String department = scanner.nextLine();
        
        // Create and register the new student
        Student student = new Student(nextId, name, address, contactNumber, dateOfBirth,
                                    rollNumber, gender, department);
        system.registerStudent(student);
        
        System.out.println("Student added successfully!");
    }
    
    // Search student menu
    private static void searchStudentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n===== Search Student =====");
            System.out.println("1. Search by ID");
            System.out.println("2. Search by Roll Number");
            System.out.println("3. Search by Name");
            System.out.println("4. Back to Student Management");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    searchStudentById();
                    break;
                case 2:
                    searchStudentByRollNumber();
                    break;
                case 3:
                    searchStudentsByName();
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    // Search student by ID
    private static void searchStudentById() {
        int id = getIntInput("Enter student ID: ");
        Student student = system.findStudentById(id);
        
        if (student != null) {
            System.out.println("\n----- Student Found -----");
            System.out.println(student.getDetails());
        } else {
            System.out.println("No student found with ID: " + id);
        }
    }
    
    // Search student by roll number
    private static void searchStudentByRollNumber() {
        System.out.print("Enter roll number: ");
        String rollNumber = scanner.nextLine();
        Student student = system.findStudentByRollNumber(rollNumber);
        
        if (student != null) {
            System.out.println("\n----- Student Found -----");
            System.out.println(student.getDetails());
        } else {
            System.out.println("No student found with roll number: " + rollNumber);
        }
    }
    
    // Search students by name
    private static void searchStudentsByName() {
        System.out.print("Enter name or part of name: ");
        String name = scanner.nextLine();
        List<Student> matchingStudents = system.searchStudentsByName(name);
        
        if (!matchingStudents.isEmpty()) {
            System.out.println("\n----- Students Found -----");
            for (Student student : matchingStudents) {
                System.out.println(student.getDetails());
                System.out.println("---------------------------");
            }
        } else {
            System.out.println("No students found with name containing: " + name);
        }
    }
    
    // Update student
    private static void updateStudent() {
        System.out.println("\n===== Update Student =====");
        
        // First find the student to update
        int id = getIntInput("Enter student ID to update: ");
        Student student = system.findStudentById(id);
        
        if (student == null) {
            System.out.println("No student found with ID: " + id);
            return;
        }
        
        // Display current student details
        System.out.println("\nCurrent Student Details:");
        System.out.println(student.getDetails());
        
        // Update menu
        boolean done = false;
        while (!done) {
            System.out.println("\n----- Update Options -----");
            System.out.println("1. Update Name");
            System.out.println("2. Update Address");
            System.out.println("3. Update Contact Number");
            System.out.println("4. Update Date of Birth");
            System.out.println("5. Update Roll Number");
            System.out.println("6. Update Gender");
            System.out.println("7. Update Department");
            System.out.println("8. Update GPA");
            System.out.println("9. Done Updating");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    System.out.print("Enter new name: ");
                    student.setName(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Enter new address: ");
                    student.setAddress(scanner.nextLine());
                    break;
                case 3:
                    System.out.print("Enter new contact number: ");
                    student.setContactNumber(scanner.nextLine());
                    break;
                case 4:
                    student.setDateOfBirth(getDateInput("Enter new date of birth"));
                    break;
                case 5:
                    System.out.print("Enter new roll number: ");
                    student.setRollNumber(scanner.nextLine());
                    break;
                case 6:
                    System.out.print("Enter new gender (Male/Female): ");
                    student.setGender(scanner.nextLine());
                    break;
                case 7:
                    System.out.print("Enter new department: ");
                    student.setDepartment(scanner.nextLine());
                    break;
                case 8:
                    double gpa = getDoubleInput("Enter new GPA: ");
                    student.setGpa(gpa);
                    break;
                case 9:
                    done = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        // Update the student in the system
        system.updateStudent(student);
        System.out.println("Student updated successfully!");
    }
    
    // Delete student
    private static void deleteStudent() {
        System.out.println("\n===== Delete Student =====");
        
        int id = getIntInput("Enter student ID to delete: ");
        Student student = system.findStudentById(id);
        
        if (student == null) {
            System.out.println("No student found with ID: " + id);
            return;
        }
        
        // Display student details
        System.out.println("\nStudent to Delete:");
        System.out.println(student.getDetails());
        
        // Confirm deletion
        System.out.print("Are you sure you want to delete this student? (y/n): ");
        String confirm = scanner.nextLine().toLowerCase();
        
        if (confirm.equals("y") || confirm.equals("yes")) {
            system.deleteStudent(student);
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
    
    // Display all students
    private static void displayAllStudents() {
        System.out.println("\n===== All Students =====");
        
        List<Student> students = system.getStudents();
        
        if (students.isEmpty()) {
            System.out.println("No students registered in the system.");
            return;
        }
        
        for (Student student : students) {
            System.out.println(student.getDetails());
            System.out.println("---------------------------");
        }
    }
    
    // Count students
    private static void countStudents() {
        System.out.println("\n===== Student Count =====");
        
        int totalStudents = system.getStudents().size();
        System.out.println("Total number of students: " + totalStudents);
        
        // Count by department
        Map<String, Integer> departmentCounts = new HashMap<>();
        for (Student student : system.getStudents()) {
            String dept = student.getDepartment();
            departmentCounts.put(dept, departmentCounts.getOrDefault(dept, 0) + 1);
        }
        
        System.out.println("\nStudents by Department:");
        for (Map.Entry<String, Integer> entry : departmentCounts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        
        // Count by gender
        int maleCount = 0;
        int femaleCount = 0;
        
        for (Student student : system.getStudents()) {
            if (student.getGender().equalsIgnoreCase("male")) {
                maleCount++;
            } else if (student.getGender().equalsIgnoreCase("female")) {
                femaleCount++;
            }
        }
        
        System.out.println("\nStudents by Gender:");
        System.out.println("Male: " + maleCount);
        System.out.println("Female: " + femaleCount);
    }
    
    // Sort students menu
    private static void sortStudentsMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n===== Sort Students =====");
            System.out.println("1. Sort by ID");
            System.out.println("2. Sort by Name");
            System.out.println("3. Sort by Roll Number");
            System.out.println("4. Sort by GPA (Descending)");
            System.out.println("5. Back to Student Management");
            
            int choice = getIntInput("Enter your choice: ");
            
            List<Student> sortedList = new ArrayList<>(system.getStudents());
            
            switch (choice) {
                case 1:
                    sortedList.sort(Comparator.comparingInt(Student::getId));
                    displaySortedStudents(sortedList, "ID");
                    break;
                case 2:
                    sortedList.sort(Comparator.comparing(Student::getName));
                    displaySortedStudents(sortedList, "Name");
                    break;
                case 3:
                    sortedList.sort(Comparator.comparing(Student::getRollNumber));
                    displaySortedStudents(sortedList, "Roll Number");
                    break;
                case 4:
                    sortedList.sort(Comparator.comparing(Student::getGpa).reversed());
                    displaySortedStudents(sortedList, "GPA (Descending)");
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    // Display sorted students
    private static void displaySortedStudents(List<Student> students, String sortedBy) {
        System.out.println("\n===== Students Sorted by " + sortedBy + " =====");
        
        if (students.isEmpty()) {
            System.out.println("No students registered in the system.");
            return;
        }
        
        for (Student student : students) {
            System.out.println(student.getDetails());
            System.out.println("---------------------------");
        }
    }
    
    // REPORTS MENU METHODS
    
    // Reports menu
    private static void reportsMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n===== Reports =====");
            System.out.println("1. Students with Warning");
            System.out.println("2. Dean's List Students");
            System.out.println("3. Students by Gender and Department");
            System.out.println("4. Fee Payment Status");
            System.out.println("5. Attendance Report");
            System.out.println("6. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    generateWarningStudentsReport();
                    break;
                case 2:
                    generateDeansListReport();
                    break;
                case 3:
                    generateStudentsByGenderAndDepartmentReport();
                    break;
                case 4:
                    generateFeePaymentReport();
                    break;
                case 5:
                    generateAttendanceReport();
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    // Helper method to get integer input
    private static int getIntInput(String message) {
        int input = 0;
        boolean valid = false;
        
        while (!valid) {
            System.out.print(message);
            try {
                input = Integer.parseInt(scanner.nextLine().trim());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        
        return input;
    }
    
    // Helper method to get double input
    private static double getDoubleInput(String prompt) {
        double value = 0;
        boolean validInput = false;
        
        while (!validInput) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                value = Double.parseDouble(input);
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        
        return value;
    }
    
    // Helper method to get date input
    private static Date getDateInput(String message) {
        Date date = null;
        boolean valid = false;
        
        while (!valid) {
            System.out.print(message);
            String dateString = scanner.nextLine().trim();
            
            try {
                date = dateFormat.parse(dateString);
                valid = true;
            } catch (ParseException e) {
                System.out.println("Please enter a valid date in the format dd/MM/yyyy.");
            }
        }
        
        return date;
    }
    
    // Generate report for students with warnings
    private static void generateWarningStudentsReport() {
        System.out.println("\n===== Students with Academic Warning =====");
        
        List<Student> warningStudents = system.getStudentsWithWarning();
        
        if (warningStudents.isEmpty()) {
            System.out.println("No students have academic warnings.");
            return;
        }
        
        for (Student student : warningStudents) {
            System.out.println("ID: " + student.getId());
            System.out.println("Name: " + student.getName());
            System.out.println("Roll Number: " + student.getRollNumber());
            System.out.println("GPA: " + student.getGpa());
            System.out.println("Department: " + student.getDepartment());
            System.out.println("---------------------------");
        }
    }
    
    // Generate report for Dean's List students
    private static void generateDeansListReport() {
        System.out.println("\n===== Dean's List Students =====");
        
        List<Student> deansListStudents = system.getStudentsOnDeansList();
        
        if (deansListStudents.isEmpty()) {
            System.out.println("No students on the Dean's List.");
            return;
        }
        
        for (Student student : deansListStudents) {
            System.out.println("ID: " + student.getId());
            System.out.println("Name: " + student.getName());
            System.out.println("Roll Number: " + student.getRollNumber());
            System.out.println("GPA: " + student.getGpa());
            System.out.println("Department: " + student.getDepartment());
            System.out.println("---------------------------");
        }
    }
    
    // Generate report for students by gender and department
    private static void generateStudentsByGenderAndDepartmentReport() {
        System.out.println("\n===== Students by Gender and Department =====");
        
        System.out.print("Enter department name: ");
        String department = scanner.nextLine();
        
        System.out.println("1. Female Students");
        System.out.println("2. Male Students");
        System.out.println("3. All Students");
        
        int choice = getIntInput("Enter your choice: ");
        
        List<Student> filteredStudents = new ArrayList<>();
        
        switch (choice) {
            case 1:
                filteredStudents = system.getFemaleStudentsByDepartment(department);
                System.out.println("\n----- Female Students in " + department + " Department -----");
                break;
            case 2:
                filteredStudents = system.getMaleStudentsByDepartment(department);
                System.out.println("\n----- Male Students in " + department + " Department -----");
                break;
            case 3:
                filteredStudents = system.getStudentsByDepartment(department);
                System.out.println("\n----- All Students in " + department + " Department -----");
                break;
            default:
                System.out.println("Invalid choice. Returning to Reports Menu.");
                return;
        }
        
        if (filteredStudents.isEmpty()) {
            System.out.println("No matching students found.");
            return;
        }
        
        for (Student student : filteredStudents) {
            System.out.println("ID: " + student.getId());
            System.out.println("Name: " + student.getName());
            System.out.println("Roll Number: " + student.getRollNumber());
            System.out.println("Gender: " + student.getGender());
            System.out.println("---------------------------");
        }
    }
    
    // Generate fee payment report
    private static void generateFeePaymentReport() {
        System.out.println("\n===== Fee Payment Report =====");
        
        System.out.println("1. Outstanding Payments");
        System.out.println("2. Payment History for a Student");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                List<FeePayment> unpaidFees = system.getUnpaidFeePayments();
                
                if (unpaidFees.isEmpty()) {
                    System.out.println("No outstanding fee payments.");
                    return;
                }
                
                System.out.println("\n----- Outstanding Payments -----");
                for (FeePayment payment : unpaidFees) {
                    Student student = payment.getStudent();
                    System.out.println("Student: " + student.getName() + " (ID: " + student.getId() + ")");
                    System.out.println("Amount: $" + payment.getAmount());
                    System.out.println("Due Date: " + payment.getFormattedDate());
                    System.out.println("Payment Type: " + payment.getPaymentType());
                    System.out.println("---------------------------");
                }
                break;
                
            case 2:
                int studentId = getIntInput("Enter student ID: ");
                Student student = system.findStudentById(studentId);
                
                if (student == null) {
                    System.out.println("No student found with ID: " + studentId);
                    return;
                }
                
                List<FeePayment> payments = system.getFeePaymentsByStudent(student);
                
                if (payments.isEmpty()) {
                    System.out.println("No fee payment records for this student.");
                    return;
                }
                
                System.out.println("\n----- Payment History for " + student.getName() + " -----");
                
                double totalPaid = 0;
                double totalOutstanding = 0;
                
                for (FeePayment payment : payments) {
                    System.out.println("Date: " + payment.getFormattedDate());
                    System.out.println("Amount: $" + payment.getAmount());
                    System.out.println("Status: " + (payment.isPaid() ? "Paid" : "Unpaid"));
                    System.out.println("Payment Type: " + payment.getPaymentType());
                    if (payment.isPaid()) {
                        System.out.println("Receipt Number: " + payment.getReceiptNumber());
                        totalPaid += payment.getAmount();
                    } else {
                        totalOutstanding += payment.getAmount();
                    }
                    System.out.println("---------------------------");
                }
                
                System.out.println("Total Paid: $" + totalPaid);
                System.out.println("Total Outstanding: $" + totalOutstanding);
                break;
                
            default:
                System.out.println("Invalid choice. Returning to Reports Menu.");
        }
    }
    
    // Generate attendance report
    private static void generateAttendanceReport() {
        System.out.println("\n===== Attendance Report =====");
        
        System.out.println("1. Attendance by Student");
        System.out.println("2. Attendance by Subject");
        System.out.println("3. Attendance by Date");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                int studentId = getIntInput("Enter student ID: ");
                Student student = system.findStudentById(studentId);
                
                if (student == null) {
                    System.out.println("No student found with ID: " + studentId);
                    return;
                }
                
                List<Attendance> studentAttendance = system.getAttendanceByStudent(student);
                
                if (studentAttendance.isEmpty()) {
                    System.out.println("No attendance records for this student.");
                    return;
                }
                
                System.out.println("\n----- Attendance for " + student.getName() + " -----");
                
                // Group attendance by subject
                Map<Subject, List<Attendance>> attendanceBySubject = new HashMap<>();
                
                for (Attendance attendance : studentAttendance) {
                    Subject subject = attendance.getSubject();
                    if (!attendanceBySubject.containsKey(subject)) {
                        attendanceBySubject.put(subject, new ArrayList<>());
                    }
                    attendanceBySubject.get(subject).add(attendance);
                }
                
                // Display attendance percentage by subject
                for (Map.Entry<Subject, List<Attendance>> entry : attendanceBySubject.entrySet()) {
                    Subject subject = entry.getKey();
                    List<Attendance> records = entry.getValue();
                    
                    int totalClasses = records.size();
                    int presentClasses = 0;
                    
                    for (Attendance record : records) {
                        if (record.isPresent()) {
                            presentClasses++;
                        }
                    }
                    
                    double percentage = (double) presentClasses / totalClasses * 100;
                    
                    System.out.println("Subject: " + subject.getName());
                    System.out.println("Attendance: " + presentClasses + "/" + totalClasses + " (" + String.format("%.2f", percentage) + "%)");
                    System.out.println("---------------------------");
                }
                break;
                
            case 2:
                int subjectId = getIntInput("Enter subject ID: ");
                Subject subject = system.findSubjectById(subjectId);
                
                if (subject == null) {
                    System.out.println("No subject found with ID: " + subjectId);
                    return;
                }
                
                List<Attendance> subjectAttendance = system.getAttendanceBySubject(subject);
                
                if (subjectAttendance.isEmpty()) {
                    System.out.println("No attendance records for this subject.");
                    return;
                }
                
                System.out.println("\n----- Attendance for " + subject.getName() + " -----");
                
                // Group attendance by date
                Map<String, List<Attendance>> attendanceByDate = new HashMap<>();
                
                for (Attendance attendance : subjectAttendance) {
                    String date = attendance.getFormattedDate();
                    if (!attendanceByDate.containsKey(date)) {
                        attendanceByDate.put(date, new ArrayList<>());
                    }
                    attendanceByDate.get(date).add(attendance);
                }
                
                // Display attendance by date
                for (Map.Entry<String, List<Attendance>> entry : attendanceByDate.entrySet()) {
                    String date = entry.getKey();
                    List<Attendance> records = entry.getValue();
                    
                    int totalStudents = records.size();
                    int presentStudents = 0;
                    
                    for (Attendance record : records) {
                        if (record.isPresent()) {
                            presentStudents++;
                        }
                    }
                    
                    double percentage = (double) presentStudents / totalStudents * 100;
                    
                    System.out.println("Date: " + date);
                    System.out.println("Attendance: " + presentStudents + "/" + totalStudents + " (" + String.format("%.2f", percentage) + "%)");
                    System.out.println("---------------------------");
                }
                break;
                
            case 3:
                Date date = getDateInput("Enter date for attendance report");
                List<Attendance> dateAttendance = system.getAttendanceByDate(date);
                
                if (dateAttendance.isEmpty()) {
                    System.out.println("No attendance records for this date.");
                    return;
                }
                
                System.out.println("\n----- Attendance for " + dateFormat.format(date) + " -----");
                
                // Group attendance by subject
                Map<Subject, List<Attendance>> attendanceBySubjectForDate = new HashMap<>();
                
                for (Attendance attendance : dateAttendance) {
                    Subject subj = attendance.getSubject();
                    if (!attendanceBySubjectForDate.containsKey(subj)) {
                        attendanceBySubjectForDate.put(subj, new ArrayList<>());
                    }
                    attendanceBySubjectForDate.get(subj).add(attendance);
                }
                
                // Display attendance by subject for the date
                for (Map.Entry<Subject, List<Attendance>> entry : attendanceBySubjectForDate.entrySet()) {
                    Subject subj = entry.getKey();
                    List<Attendance> records = entry.getValue();
                    
                    int totalStudents = records.size();
                    int presentStudents = 0;
                    
                    for (Attendance record : records) {
                        if (record.isPresent()) {
                            presentStudents++;
                        }
                    }
                    
                    double percentage = (double) presentStudents / totalStudents * 100;
                    
                    System.out.println("Subject: " + subj.getName());
                    System.out.println("Attendance: " + presentStudents + "/" + totalStudents + " (" + String.format("%.2f", percentage) + "%)");
                    System.out.println("---------------------------");
                }
                break;
                
            default:
                System.out.println("Invalid choice. Returning to Reports Menu.");
        }
    }
    
    // ATTENDANCE MANAGEMENT METHODS
    private static void attendanceManagementMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n===== Attendance Management =====");
            System.out.println("1. Mark Attendance");
            System.out.println("2. View Attendance");
            System.out.println("3. Update Attendance");
            System.out.println("4. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    markAttendance();
                    break;
                case 2:
                    viewAttendance();
                    break;
                case 3:
                    updateAttendance();
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    // TEACHER MANAGEMENT METHODS
    private static void teacherManagementMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n===== Teacher Management =====");
            System.out.println("1. Add New Teacher");
            System.out.println("2. Search Teacher");
            System.out.println("3. Update Teacher");
            System.out.println("4. Delete Teacher");
            System.out.println("5. Display All Teachers");
            System.out.println("6. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addNewTeacher();
                    break;
                case 2:
                    searchTeacher();
                    break;
                case 3:
                    updateTeacher();
                    break;
                case 4:
                    deleteTeacher();
                    break;
                case 5:
                    displayAllTeachers();
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    // Teacher Management methods implementation
    private static void addNewTeacher() {
        System.out.println("\n===== Add New Teacher =====");
        
        // Get basic person information
        int id = getIntInput("Enter Person ID: ");
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter Contact Number: ");
        String contactNumber = scanner.nextLine();
        Date dateOfBirth = getDateInput("Enter Date of Birth (dd/MM/yyyy): ");
        
        // Get teacher specific information
        int teacherId = getIntInput("Enter Teacher ID: ");
        System.out.print("Enter Specialization: ");
        String specialization = scanner.nextLine();
        Date joinDate = getDateInput("Enter Join Date (dd/MM/yyyy): ");
        System.out.print("Enter Department: ");
        String department = scanner.nextLine();
        
        // Create new teacher object
        Teacher newTeacher = new Teacher(id, name, address, contactNumber, dateOfBirth, 
                                       teacherId, specialization, joinDate, department);
        
        // Add to system
        system.addTeacher(newTeacher);
        System.out.println("Teacher added successfully!");
        
        // Save data
        system.saveData();
    }
    
    private static void searchTeacher() {
        System.out.println("\n===== Search Teacher =====");
        System.out.println("1. Search by ID");
        System.out.println("2. Back to Teacher Management");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                searchTeacherById();
                break;
            case 2:
                // Return to teacher management menu
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    // Search teacher by ID
    private static void searchTeacherById() {
        int id = getIntInput("Enter Teacher ID to search: ");
        Teacher teacher = system.findTeacherById(id);
        
        if (teacher != null) {
            System.out.println("\n===== Teacher Details =====");
            System.out.println(teacher.getDetails());
        } else {
            System.out.println("Teacher with ID " + id + " not found.");
        }
    }
    
    private static void updateTeacher() {
        System.out.println("\n===== Update Teacher =====");
        int id = getIntInput("Enter Teacher ID to update: ");
        
        Teacher teacher = system.findTeacherById(id);
        if (teacher == null) {
            System.out.println("Teacher with ID " + id + " not found.");
            return;
        }
        
        System.out.println("\nCurrent Teacher Details:");
        System.out.println(teacher.getDetails());
        System.out.println("\nEnter new details (leave blank to keep current value):");
        
        // Update basic Person information
        System.out.print("Enter Name [" + teacher.getName() + "]: ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            teacher.setName(name);
        }
        
        System.out.print("Enter Address [" + teacher.getAddress() + "]: ");
        String address = scanner.nextLine();
        if (!address.isEmpty()) {
            teacher.setAddress(address);
        }
        
        System.out.print("Enter Contact Number [" + teacher.getContactNumber() + "]: ");
        String contactNumber = scanner.nextLine();
        if (!contactNumber.isEmpty()) {
            teacher.setContactNumber(contactNumber);
        }
        
        // Update Teacher specific information
        System.out.print("Enter Specialization [" + teacher.getSpecialization() + "]: ");
        String specialization = scanner.nextLine();
        if (!specialization.isEmpty()) {
            teacher.setSpecialization(specialization);
        }
        
        // Update in system
        system.updateTeacher(teacher);
        System.out.println("Teacher updated successfully!");
        
        // Save data
        system.saveData();
    }
    
    private static void deleteTeacher() {
        System.out.println("\n===== Delete Teacher =====");
        int id = getIntInput("Enter Teacher ID to delete: ");
        
        Teacher teacher = system.findTeacherById(id);
        if (teacher == null) {
            System.out.println("Teacher with ID " + id + " not found.");
            return;
        }
        
        System.out.println("\nTeacher to be deleted:");
        System.out.println(teacher.getDetails());
        
        System.out.print("Are you sure you want to delete this teacher? (Y/N): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("Y")) {
            system.deleteTeacher(teacher);
            System.out.println("Teacher deleted successfully!");
            
            // Save data
            system.saveData();
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
    
    private static void displayAllTeachers() {
        System.out.println("\n===== All Teachers =====");
        
        List<Teacher> teachers = system.getTeachers();
        if (teachers.isEmpty()) {
            System.out.println("No teachers registered in the system.");
            return;
        }
        
        System.out.println("\nList of Teachers:");
        System.out.printf("%-5s %-20s %-20s %-15s %-15s\n", "ID", "Name", "Specialization", "Join Date", "Contact");
        System.out.println("-------------------------------------------------------------------------");
        
        for (Teacher teacher : teachers) {
            System.out.printf("%-5d %-20s %-20s %-15s %-15s\n", 
                teacher.getTeacherId(), 
                teacher.getName(), 
                teacher.getSpecialization(), 
                teacher.getFormattedJoinDate(), 
                teacher.getContactNumber());
        }
    }
    
    // COURSE MANAGEMENT METHODS
    private static void courseManagementMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n===== Course Management =====");
            System.out.println("1. Add New Course");
            System.out.println("2. Add New Subject");
            System.out.println("3. Assign Subject to Course");
            System.out.println("4. Update Course");
            System.out.println("5. Update Subject");
            System.out.println("6. Delete Course");
            System.out.println("7. Delete Subject");
            System.out.println("8. Display All Courses");
            System.out.println("9. Display All Subjects");
            System.out.println("10. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addNewCourse();
                    break;
                case 2:
                    addNewSubject();
                    break;
                case 3:
                    assignSubjectToCourse();
                    break;
                case 4:
                    updateCourse();
                    break;
                case 5:
                    updateSubject();
                    break;
                case 6:
                    deleteCourse();
                    break;
                case 7:
                    deleteSubject();
                    break;
                case 8:
                    displayAllCourses();
                    break;
                case 9:
                    displayAllSubjects();
                    break;
                case 10:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    // Course Management methods implementation
    private static void addNewCourse() {
        System.out.println("\n===== Add New Course =====");
        
        int courseId = getIntInput("Enter Course ID: ");
        System.out.print("Enter Course Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Department: ");
        String department = scanner.nextLine();
        System.out.print("Enter Credit Hours: ");
        int creditHours = getIntInput("");
        
        // Create new course object
        Course newCourse = new Course(courseId, name, department, creditHours);
        
        // Add to system
        system.addCourse(newCourse);
        System.out.println("Course added successfully!");
        
        // Save data
        system.saveData();
    }
    
    private static void addNewSubject() {
        System.out.println("\n===== Add New Subject =====");
        
        int subjectId = getIntInput("Enter Subject ID: ");
        System.out.print("Enter Subject Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Subject Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter Credit Hours: ");
        int creditHours = getIntInput("");
        
        // Option to assign teacher
        System.out.println("\nDo you want to assign a teacher to this subject? (Y/N): ");
        String assignTeacher = scanner.nextLine();
        Teacher teacher = null;
        
        if (assignTeacher.equalsIgnoreCase("Y")) {
            // Display available teachers
            List<Teacher> teachers = system.getTeachers();
            if (teachers.isEmpty()) {
                System.out.println("No teachers available. Subject will be created without a teacher.");
            } else {
                System.out.println("Available Teachers:");
                for (int i = 0; i < teachers.size(); i++) {
                    System.out.printf("%d. %s (ID: %d, Specialization: %s)\n", 
                                      i + 1, 
                                      teachers.get(i).getName(), 
                                      teachers.get(i).getTeacherId(),
                                      teachers.get(i).getSpecialization());
                }
                
                int teacherIndex = getIntInput("Select teacher number (0 for none): ");
                if (teacherIndex > 0 && teacherIndex <= teachers.size()) {
                    teacher = teachers.get(teacherIndex - 1);
                }
            }
        }
        
        // Create new subject object
        Subject newSubject = new Subject(subjectId, name, teacher, creditHours, description);
        
        // Add to system
        system.addSubject(newSubject);
        System.out.println("Subject added successfully!");
        
        // Save data
        system.saveData();
    }
    
    private static void assignSubjectToCourse() {
        System.out.println("\n===== Assign Subject to Course =====");
        
        // Display all courses
        List<Course> courses = system.getCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses available. Please add a course first.");
            return;
        }
        
        System.out.println("Available Courses:");
        for (int i = 0; i < courses.size(); i++) {
            System.out.printf("%d. %s (ID: %d)\n", i + 1, courses.get(i).getName(), courses.get(i).getCourseId());
        }
        
        int courseIndex = getIntInput("Select course number: ") - 1;
        if (courseIndex < 0 || courseIndex >= courses.size()) {
            System.out.println("Invalid course selection.");
            return;
        }
        
        Course selectedCourse = courses.get(courseIndex);
        
        // Display all subjects
        List<Subject> subjects = system.getSubjects();
        if (subjects.isEmpty()) {
            System.out.println("No subjects available. Please add a subject first.");
            return;
        }
        
        System.out.println("\nAvailable Subjects:");
        for (int i = 0; i < subjects.size(); i++) {
            System.out.printf("%d. %s (ID: %d)\n", i + 1, subjects.get(i).getName(), subjects.get(i).getSubjectId());
        }
        
        int subjectIndex = getIntInput("Select subject number: ") - 1;
        if (subjectIndex < 0 || subjectIndex >= subjects.size()) {
            System.out.println("Invalid subject selection.");
            return;
        }
        
        Subject selectedSubject = subjects.get(subjectIndex);
        
        // Add subject to course
        selectedCourse.addSubject(selectedSubject);
        System.out.println("Subject '" + selectedSubject.getName() + "' assigned to course '" + selectedCourse.getName() + "'.");
        
        // Save data
        system.updateCourse(selectedCourse);
        system.saveData();
    }
    
    private static void updateCourse() {
        System.out.println("\n===== Update Course =====");
        
        int courseId = getIntInput("Enter Course ID to update: ");
        Course course = system.findCourseById(courseId);
        
        if (course == null) {
            System.out.println("Course with ID " + courseId + " not found.");
            return;
        }
        
        System.out.println("\nCurrent Course Details:");
        System.out.println("ID: " + course.getCourseId());
        System.out.println("Name: " + course.getName());
        System.out.println("Department: " + course.getDepartment());
        System.out.println("Credit Hours: " + course.getCreditHours());
        
        System.out.println("\nEnter new details (leave blank to keep current value):");
        
        System.out.print("Enter Name [" + course.getName() + "]: ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            course.setName(name);
        }
        
        System.out.print("Enter Department [" + course.getDepartment() + "]: ");
        String department = scanner.nextLine();
        if (!department.isEmpty()) {
            course.setDepartment(department);
        }
        
        System.out.print("Enter Credit Hours [" + course.getCreditHours() + "]: ");
        String creditHoursStr = scanner.nextLine();
        if (!creditHoursStr.isEmpty()) {
            try {
                int creditHours = Integer.parseInt(creditHoursStr);
                course.setCreditHours(creditHours);
            } catch (NumberFormatException e) {
                System.out.println("Invalid credit hours format. Credit hours not updated.");
            }
        }
        
        // Update in system
        system.updateCourse(course);
        System.out.println("Course updated successfully!");
        
        // Save data
        system.saveData();
    }
    
    private static void updateSubject() {
        System.out.println("\n===== Update Subject =====");
        
        int subjectId = getIntInput("Enter Subject ID to update: ");
        Subject subject = system.findSubjectById(subjectId);
        
        if (subject == null) {
            System.out.println("Subject with ID " + subjectId + " not found.");
            return;
        }
        
        System.out.println("\nCurrent Subject Details:");
        System.out.println("ID: " + subject.getSubjectId());
        System.out.println("Name: " + subject.getName());
        System.out.println("Description: " + subject.getDescription());
        System.out.println("Credit Hours: " + subject.getCreditHours());
        System.out.println("Teacher: " + (subject.getTeacher() != null ? subject.getTeacher().getName() : "Not assigned"));
        
        System.out.println("\nEnter new details (leave blank to keep current value):");
        
        System.out.print("Enter Name [" + subject.getName() + "]: ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            subject.setName(name);
        }
        
        System.out.print("Enter Description [" + subject.getDescription() + "]: ");
        String description = scanner.nextLine();
        if (!description.isEmpty()) {
            subject.setDescription(description);
        }
        
        System.out.print("Enter Credit Hours [" + subject.getCreditHours() + "]: ");
        String creditHoursStr = scanner.nextLine();
        if (!creditHoursStr.isEmpty()) {
            try {
                int creditHours = Integer.parseInt(creditHoursStr);
                subject.setCreditHours(creditHours);
            } catch (NumberFormatException e) {
                System.out.println("Invalid credit hours format. Credit hours not updated.");
            }
        }
        
        // Option to update teacher
        System.out.println("\nDo you want to update the teacher assignment? (Y/N): ");
        String updateTeacher = scanner.nextLine();
        
        if (updateTeacher.equalsIgnoreCase("Y")) {
            // Display available teachers
            List<Teacher> teachers = system.getTeachers();
            if (teachers.isEmpty()) {
                System.out.println("No teachers available. Cannot update teacher assignment.");
            } else {
                System.out.println("Available Teachers:");
                System.out.println("0. Remove teacher assignment");
                for (int i = 0; i < teachers.size(); i++) {
                    System.out.printf("%d. %s (ID: %d, Specialization: %s)\n", 
                                     i + 1, 
                                     teachers.get(i).getName(), 
                                     teachers.get(i).getTeacherId(),
                                     teachers.get(i).getSpecialization());
                }
                
                int teacherIndex = getIntInput("Select teacher number: ");
                if (teacherIndex == 0) {
                    subject.setTeacher(null);
                    System.out.println("Teacher assignment removed.");
                } else if (teacherIndex > 0 && teacherIndex <= teachers.size()) {
                    subject.setTeacher(teachers.get(teacherIndex - 1));
                    System.out.println("Teacher updated to: " + teachers.get(teacherIndex - 1).getName());
                } else {
                    System.out.println("Invalid selection. Teacher not updated.");
                }
            }
        }
        
        // Update in system
        system.updateSubject(subject);
        System.out.println("Subject updated successfully!");
        
        // Save data
        system.saveData();
    }
    
    private static void deleteCourse() {
        System.out.println("\n===== Delete Course =====");
        
        int courseId = getIntInput("Enter Course ID to delete: ");
        Course course = system.findCourseById(courseId);
        
        if (course == null) {
            System.out.println("Course with ID " + courseId + " not found.");
            return;
        }
        
        System.out.println("\nCourse to be deleted:");
        System.out.println("ID: " + course.getCourseId());
        System.out.println("Name: " + course.getName());
        System.out.println("Department: " + course.getDepartment());
        
        System.out.print("Are you sure you want to delete this course? (Y/N): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("Y")) {
            system.deleteCourse(course);
            System.out.println("Course deleted successfully!");
            
            // Save data
            system.saveData();
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
    
    private static void deleteSubject() {
        System.out.println("\n===== Delete Subject =====");
        
        int subjectId = getIntInput("Enter Subject ID to delete: ");
        Subject subject = system.findSubjectById(subjectId);
        
        if (subject == null) {
            System.out.println("Subject with ID " + subjectId + " not found.");
            return;
        }
        
        System.out.println("\nSubject to be deleted:");
        System.out.println("ID: " + subject.getSubjectId());
        System.out.println("Name: " + subject.getName());
        System.out.println("Description: " + subject.getDescription());
        
        System.out.print("Are you sure you want to delete this subject? (Y/N): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("Y")) {
            system.deleteSubject(subject);
            System.out.println("Subject deleted successfully!");
            
            // Save data
            system.saveData();
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
    
    private static void displayAllCourses() {
        System.out.println("\n===== All Courses =====");
        
        List<Course> courses = system.getCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses registered in the system.");
            return;
        }
        
        System.out.println("\nList of Courses:");
        System.out.printf("%-5s %-25s %-15s %-10s\n", "ID", "Name", "Department", "Credits");
        System.out.println("--------------------------------------------------");
        
        for (Course course : courses) {
            System.out.printf("%-5d %-25s %-15s %-10d\n", 
                course.getCourseId(), 
                course.getName(), 
                course.getDepartment(), 
                course.getCreditHours());
        }
        
        // Option to view details of a specific course
        System.out.println("\nEnter course ID to view details (0 to go back): ");
        int courseId = getIntInput("");
        
        if (courseId > 0) {
            Course course = system.findCourseById(courseId);
            if (course != null) {
                System.out.println("\n===== Course Details =====");
                System.out.println("ID: " + course.getCourseId());
                System.out.println("Name: " + course.getName());
                System.out.println("Department: " + course.getDepartment());
                System.out.println("Credit Hours: " + course.getCreditHours());
                
                System.out.println("\nSubjects in this course:");
                List<Subject> subjects = course.getSubjects();
                if (subjects.isEmpty()) {
                    System.out.println("No subjects assigned to this course.");
                } else {
                    for (Subject subject : subjects) {
                        System.out.printf("- %s: %d credit hours%s\n", 
                            subject.getName(), 
                            subject.getCreditHours(),
                            subject.getTeacher() != null ? " (Teacher: " + subject.getTeacher().getName() + ")" : "");
                    }
                }
            } else {
                System.out.println("Course with ID " + courseId + " not found.");
            }
        }
    }
    
    private static void displayAllSubjects() {
        System.out.println("\n===== All Subjects =====");
        
        List<Subject> subjects = system.getSubjects();
        if (subjects.isEmpty()) {
            System.out.println("No subjects registered in the system.");
            return;
        }
        
        System.out.println("\nList of Subjects:");
        System.out.printf("%-5s %-25s %-10s %-20s\n", "ID", "Name", "Credits", "Teacher");
        System.out.println("------------------------------------------------------------------");
        
        for (Subject subject : subjects) {
            System.out.printf("%-5d %-25s %-10d %-20s\n", 
                subject.getSubjectId(), 
                subject.getName(), 
                subject.getCreditHours(),
                subject.getTeacher() != null ? subject.getTeacher().getName() : "Not assigned");
        }
        
        // Option to view details of a specific subject
        System.out.println("\nEnter subject ID to view details (0 to go back): ");
        int subjectId = getIntInput("");
        
        if (subjectId > 0) {
            Subject subject = system.findSubjectById(subjectId);
            if (subject != null) {
                System.out.println("\n===== Subject Details =====");
                System.out.println("ID: " + subject.getSubjectId());
                System.out.println("Name: " + subject.getName());
                System.out.println("Description: " + subject.getDescription());
                System.out.println("Credit Hours: " + subject.getCreditHours());
                
                // Display teacher information
                if (subject.getTeacher() != null) {
                    Teacher teacher = subject.getTeacher();
                    System.out.println("\nAssigned Teacher:");
                    System.out.println("Name: " + teacher.getName());
                    System.out.println("ID: " + teacher.getTeacherId());
                    System.out.println("Specialization: " + teacher.getSpecialization());
                    System.out.println("Contact: " + teacher.getContactNumber());
                } else {
                    System.out.println("\nNo teacher assigned to this subject.");
                }
            } else {
                System.out.println("Subject with ID " + subjectId + " not found.");
            }
        }
    }
    
    // Attendance Management methods implementation
    private static void markAttendance() {
        System.out.println("\n===== Mark Attendance =====");
        
        // First select a subject
        List<Subject> subjects = system.getSubjects();
        if (subjects.isEmpty()) {
            System.out.println("No subjects available. Please add a subject first.");
            return;
        }
        
        System.out.println("Available Subjects:");
        for (int i = 0; i < subjects.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, subjects.get(i).getName());
        }
        
        int subjectIndex = getIntInput("Select subject number: ") - 1;
        if (subjectIndex < 0 || subjectIndex >= subjects.size()) {
            System.out.println("Invalid subject selection.");
            return;
        }
        
        Subject selectedSubject = subjects.get(subjectIndex);
        
        // Get the date for attendance
        Date attendanceDate = getDateInput("Enter attendance date (dd/MM/yyyy): ");
        
        // Get the list of students
        List<Student> students = system.getStudents();
        if (students.isEmpty()) {
            System.out.println("No students registered in the system.");
            return;
        }
        
        System.out.println("\nMarking attendance for " + selectedSubject.getName() + " on " + dateFormat.format(attendanceDate));
        System.out.println("Mark each student as Present (P) or Absent (A), and add optional remarks:");
        
        for (Student student : students) {
            System.out.print(student.getName() + " (" + student.getRollNumber() + ") [P/A]: ");
            String status = scanner.nextLine().toUpperCase();
            
            while (!status.equals("P") && !status.equals("A") && !status.isEmpty()) {
                System.out.print("Invalid status. Enter P or A: ");
                status = scanner.nextLine().toUpperCase();
            }
            
            // Default to present if empty
            boolean isPresent = status.isEmpty() || status.equals("P");
            
            // Get optional remarks
            System.out.print("Remarks (optional): ");
            String remarks = scanner.nextLine();
            
            // Create and add attendance record
            Attendance attendance = new Attendance(student, selectedSubject, attendanceDate, isPresent, remarks);
            system.markAttendance(attendance);
        }
        
        System.out.println("\nAttendance marked successfully for all students!");
        
        // Save data
        system.saveData();
    }
    
    private static void viewAttendance() {
        System.out.println("\n===== View Attendance =====");
        System.out.println("1. View by Student");
        System.out.println("2. View by Subject");
        System.out.println("3. View by Date");
        System.out.println("4. Back to Attendance Management");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                viewAttendanceByStudent();
                break;
            case 2:
                viewAttendanceBySubject();
                break;
            case 3:
                viewAttendanceByDate();
                break;
            case 4:
                // Return to attendance management menu
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    // View attendance by student
    private static void viewAttendanceByStudent() {
        System.out.println("\n===== View Attendance by Student =====");
        
        // Get student ID
        int studentId = getIntInput("Enter Student ID: ");
        Student student = system.findStudentById(studentId);
        
        if (student == null) {
            System.out.println("Student with ID " + studentId + " not found.");
            return;
        }
        
        // Get attendance records for this student
        List<Attendance> attendanceRecords = system.getAttendanceByStudent(student);
        
        if (attendanceRecords.isEmpty()) {
            System.out.println("No attendance records found for " + student.getName() + ".");
            return;
        }
        
        // Display attendance records
        System.out.println("\nAttendance Records for " + student.getName() + " (" + student.getRollNumber() + "):");
        System.out.printf("%-15s %-25s %-10s %-20s\n", "Date", "Subject", "Status", "Remarks");
        System.out.println("-------------------------------------------------------------------------");
        
        for (Attendance record : attendanceRecords) {
            System.out.printf("%-15s %-25s %-10s %-20s\n", 
                record.getFormattedDate(), 
                record.getSubject().getName(), 
                record.isPresent() ? "Present" : "Absent",
                record.getRemarks());
        }
        
        // Calculate attendance percentage
        int totalRecords = attendanceRecords.size();
        long presentCount = attendanceRecords.stream()
            .filter(Attendance::isPresent)
            .count();
        
        double attendancePercentage = (double) presentCount / totalRecords * 100;
        
        System.out.println("\nAttendance Summary:");
        System.out.println("Total Classes: " + totalRecords);
        System.out.println("Present: " + presentCount);
        System.out.println("Absent: " + (totalRecords - presentCount));
        System.out.printf("Attendance Percentage: %.2f%%\n", attendancePercentage);
    }
    
    // View attendance by subject
    private static void viewAttendanceBySubject() {
        System.out.println("\n===== View Attendance by Subject =====");
        
        // Select a subject
        List<Subject> subjects = system.getSubjects();
        if (subjects.isEmpty()) {
            System.out.println("No subjects available.");
            return;
        }
        
        System.out.println("Available Subjects:");
        for (int i = 0; i < subjects.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, subjects.get(i).getName());
        }
        
        int subjectIndex = getIntInput("Select subject number: ") - 1;
        if (subjectIndex < 0 || subjectIndex >= subjects.size()) {
            System.out.println("Invalid subject selection.");
            return;
        }
        
        Subject selectedSubject = subjects.get(subjectIndex);
        
        // Get attendance records for this subject
        List<Attendance> attendanceRecords = system.getAttendanceBySubject(selectedSubject);
        
        if (attendanceRecords.isEmpty()) {
            System.out.println("No attendance records found for " + selectedSubject.getName() + ".");
            return;
        }
        
        // Display attendance records
        System.out.println("\nAttendance Records for " + selectedSubject.getName() + ":");
        System.out.printf("%-15s %-25s %-10s %-20s\n", "Date", "Student", "Status", "Remarks");
        System.out.println("-------------------------------------------------------------------------");
        
        // Group by date
        Map<Date, List<Attendance>> recordsByDate = attendanceRecords.stream()
            .collect(Collectors.groupingBy(Attendance::getDate));
        
        for (Date date : recordsByDate.keySet()) {
            System.out.println("\nDate: " + dateFormat.format(date));
            
            for (Attendance record : recordsByDate.get(date)) {
                System.out.printf("%-15s %-25s %-10s %-20s\n", 
                    "", // Date is already shown above
                    record.getStudent().getName() + " (" + record.getStudent().getRollNumber() + ")", 
                    record.isPresent() ? "Present" : "Absent",
                    record.getRemarks());
            }
            
            // Calculate stats for this date
            List<Attendance> dayRecords = recordsByDate.get(date);
            int totalStudents = dayRecords.size();
            long presentCount = dayRecords.stream()
                .filter(Attendance::isPresent)
                .count();
            
            System.out.println("Present: " + presentCount + ", Absent: " + (totalStudents - presentCount) + 
                             ", Attendance Rate: " + String.format("%.1f%%", (double) presentCount / totalStudents * 100));
        }
    }
    
    // View attendance by date
    private static void viewAttendanceByDate() {
        System.out.println("\n===== View Attendance by Date =====");
        
        // Get the date for attendance
        Date attendanceDate = getDateInput("Enter attendance date (dd/MM/yyyy): ");
        
        // Get attendance records for this date
        List<Attendance> attendanceRecords = system.getAttendanceByDate(attendanceDate);
        
        if (attendanceRecords.isEmpty()) {
            System.out.println("No attendance records found for " + dateFormat.format(attendanceDate) + ".");
            return;
        }
        
        // Display attendance records
        System.out.println("\nAttendance Records for " + dateFormat.format(attendanceDate) + ":");
        
        // Group by subject
        Map<Subject, List<Attendance>> recordsBySubject = attendanceRecords.stream()
            .collect(Collectors.groupingBy(Attendance::getSubject));
        
        for (Subject subject : recordsBySubject.keySet()) {
            System.out.println("\nSubject: " + subject.getName());
            System.out.printf("%-25s %-10s %-20s\n", "Student", "Status", "Remarks");
            System.out.println("-----------------------------------------------------");
            
            for (Attendance record : recordsBySubject.get(subject)) {
                System.out.printf("%-25s %-10s %-20s\n", 
                    record.getStudent().getName() + " (" + record.getStudent().getRollNumber() + ")", 
                    record.isPresent() ? "Present" : "Absent",
                    record.getRemarks());
            }
            
            // Calculate stats for this subject
            List<Attendance> subjectRecords = recordsBySubject.get(subject);
            int totalStudents = subjectRecords.size();
            long presentCount = subjectRecords.stream()
                .filter(Attendance::isPresent)
                .count();
            
            System.out.println("Present: " + presentCount + ", Absent: " + (totalStudents - presentCount) + 
                             ", Attendance Rate: " + String.format("%.1f%%", (double) presentCount / totalStudents * 100));
        }
    }
    
    private static void updateAttendance() {
        System.out.println("\n===== Update Attendance =====");
        
        // First select a subject
        List<Subject> subjects = system.getSubjects();
        if (subjects.isEmpty()) {
            System.out.println("No subjects available.");
            return;
        }
        
        System.out.println("Available Subjects:");
        for (int i = 0; i < subjects.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, subjects.get(i).getName());
        }
        
        int subjectIndex = getIntInput("Select subject number: ") - 1;
        if (subjectIndex < 0 || subjectIndex >= subjects.size()) {
            System.out.println("Invalid subject selection.");
            return;
        }
        
        Subject selectedSubject = subjects.get(subjectIndex);
        
        // Get the date for attendance
        Date attendanceDate = getDateInput("Enter attendance date to update (dd/MM/yyyy): ");
        
        // Get student ID
        int studentId = getIntInput("Enter Student ID to update attendance: ");
        Student student = system.findStudentById(studentId);
        
        if (student == null) {
            System.out.println("Student with ID " + studentId + " not found.");
            return;
        }
        
        // Find the attendance record
        List<Attendance> attendanceRecords = system.getAttendances();
        Attendance targetRecord = null;
        
        for (Attendance record : attendanceRecords) {
            if (record.getStudent().getId() == student.getId() && 
                record.getSubject().getSubjectId() == selectedSubject.getSubjectId() &&
                record.getDate().equals(attendanceDate)) {
                targetRecord = record;
                break;
            }
        }
        
        if (targetRecord == null) {
            System.out.println("No attendance record found for the selected criteria.");
            System.out.println("Do you want to create a new attendance record? (Y/N): ");
            String choice = scanner.nextLine();
            
            if (choice.equalsIgnoreCase("Y")) {
                System.out.print("Is student present? (Y/N): ");
                String presenceInput = scanner.nextLine();
                boolean isPresent = presenceInput.equalsIgnoreCase("Y");
                
                System.out.print("Enter remarks (optional): ");
                String remarks = scanner.nextLine();
                
                Attendance newRecord = new Attendance(student, selectedSubject, attendanceDate, isPresent, remarks);
                system.markAttendance(newRecord);
                System.out.println("New attendance record created successfully!");
            } else {
                System.out.println("Operation cancelled.");
                return;
            }
        } else {
            // Display current status
            System.out.println("\nCurrent attendance status: " + (targetRecord.isPresent() ? "Present" : "Absent"));
            System.out.println("Current remarks: " + targetRecord.getRemarks());
            
            System.out.print("Update attendance status? (Y/N): ");
            String updateStatus = scanner.nextLine();
            
            if (updateStatus.equalsIgnoreCase("Y")) {
                System.out.print("Is student present? (Y/N): ");
                String presenceInput = scanner.nextLine();
                boolean isPresent = presenceInput.equalsIgnoreCase("Y");
                targetRecord.setPresent(isPresent);
            }
            
            System.out.print("Update remarks? (Y/N): ");
            String updateRemarks = scanner.nextLine();
            
            if (updateRemarks.equalsIgnoreCase("Y")) {
                System.out.print("Enter new remarks: ");
                String newRemarks = scanner.nextLine();
                targetRecord.setRemarks(newRemarks);
            }
            
            System.out.println("Attendance record updated successfully!");
        }
        
        // Save data
        system.saveData();
    }
    
    // FEE MANAGEMENT METHODS
    private static void feeManagementMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n===== Fee Management =====");
            System.out.println("1. Record New Payment");
            System.out.println("2. Update Payment Status");
            System.out.println("3. Generate Receipt");
            System.out.println("4. View Outstanding Fees");
            System.out.println("5. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    recordNewPayment();
                    break;
                case 2:
                    updatePaymentStatus();
                    break;
                case 3:
                    generateReceipt();
                    break;
                case 4:
                    viewOutstandingFees();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    // Fee Management methods placeholder implementations
    private static void recordNewPayment() {
        System.out.println("\n===== Record New Payment =====");
        
        // Get student
        System.out.println("\nSelect a student:");
        List<Student> students = system.getStudents();
        if (students.isEmpty()) {
            System.out.println("No students registered in the system.");
            return;
        }
        
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getName() + " (" + students.get(i).getRollNumber() + ")");
        }
        
        int studentIndex = getIntInput("Enter student number: ") - 1;
        if (studentIndex < 0 || studentIndex >= students.size()) {
            System.out.println("Invalid student number.");
            return;
        }
        Student student = students.get(studentIndex);
        
        // Get payment type
        System.out.println("\nSelect payment type:");
        System.out.println("1. Tuition Fee");
        System.out.println("2. Library Fee");
        System.out.println("3. Hostel Fee");
        System.out.println("4. Other");
        
        int paymentTypeIndex = getIntInput("Enter payment type number: ") - 1;
        String paymentType;
        switch (paymentTypeIndex) {
            case 0:
                paymentType = "Tuition Fee";
                break;
            case 1:
                paymentType = "Library Fee";
                break;
            case 2:
                paymentType = "Hostel Fee";
                break;
            case 3:
                System.out.print("Enter other payment type: ");
                paymentType = scanner.nextLine();
                break;
            default:
                System.out.println("Invalid payment type.");
                return;
        }
        
        // Get payment amount
        System.out.print("Enter payment amount: ");
        double amount = getDoubleInput("Payment amount:");
        
        // Get payment date (default to today)
        Date date = new Date();
        System.out.println("\nUsing today's date: " + dateFormat.format(date));
        System.out.print("Do you want to enter a different date? (y/n): ");
        String customDateOption = scanner.nextLine();
        if (customDateOption.equalsIgnoreCase("y")) {
            date = getDateInput("Enter date (dd/MM/yyyy): ");
        }
        
        // Generate receipt number
        String receiptNumber = "REC-" + System.currentTimeMillis();
        
        // Create payment record
        FeePayment payment = new FeePayment(student, amount, date, true, paymentType, receiptNumber);
        system.recordFeePayment(payment);
        
        // Create receipt
        FeeReceipt receipt = new FeeReceipt(receiptNumber, student, date, "Cash", "System", "Paid");
        receipt.addPayment(payment);
        system.addFeeReceipt(receipt);
        
        System.out.println("\nPayment recorded successfully!");
        System.out.println("Student: " + student.getName());
        System.out.println("Payment Type: " + paymentType);
        System.out.println("Amount: " + amount);
        System.out.println("Date: " + dateFormat.format(date));
        System.out.println("Receipt Number: " + receiptNumber);
        
        // Save changes
        system.saveData();
    }
    
    private static void updatePaymentStatus() {
        System.out.println("\n===== Update Payment Status =====");
        
        // Get student
        System.out.println("\nSelect a student:");
        List<Student> students = system.getStudents();
        if (students.isEmpty()) {
            System.out.println("No students registered in the system.");
            return;
        }
        
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getName() + " (" + students.get(i).getRollNumber() + ")");
        }
        
        int studentIndex = getIntInput("Enter student number: ") - 1;
        if (studentIndex < 0 || studentIndex >= students.size()) {
            System.out.println("Invalid student number.");
            return;
        }
        Student student = students.get(studentIndex);
        
        // Display student's unpaid payments
        System.out.println("\nUnpaid payments for " + student.getName() + ":");
        List<FeePayment> unpaidPayments = system.getFeePaymentsByStudent(student)
                .stream()
                .filter(p -> !p.isPaid())
                .collect(Collectors.toList());
                
        if (unpaidPayments.isEmpty()) {
            System.out.println("No unpaid payments found for this student.");
            return;
        }
        
        // Display payment details
        System.out.printf("%-5s %-20s %-12s %-10s %-30s\n", "No.", "Date", "Amount", "Type", "Receipt Number");
        System.out.println("-----------------------------------------------------------------------------------");
        
        for (int i = 0; i < unpaidPayments.size(); i++) {
            FeePayment payment = unpaidPayments.get(i);
            System.out.printf("%-5d %-20s $%-10.2f %-12s %-30s\n", 
                            (i + 1),
                            dateFormat.format(payment.getDate()),
                            payment.getAmount(),
                            payment.getPaymentType(),
                            payment.getReceiptNumber());
        }
        
        // Get payment to update
        int paymentIndex = getIntInput("Enter the number of the payment to update: ") - 1;
        if (paymentIndex < 0 || paymentIndex >= unpaidPayments.size()) {
            System.out.println("Invalid payment number.");
            return;
        }
        
        FeePayment payment = unpaidPayments.get(paymentIndex);
        
        // Update payment status
        System.out.print("Is the payment now complete? (y/n): ");
        String statusInput = scanner.nextLine();
        boolean isPaid = statusInput.equalsIgnoreCase("y");
        payment.setPaid(isPaid);
        
        // Update the receipt status
        FeeReceipt receipt = system.getFeeReceiptByNumber(payment.getReceiptNumber());
        if (receipt != null) {
            receipt.setStatus(isPaid ? "Paid" : "Pending");
        }
        
        System.out.println("\nPayment status updated successfully.");
        system.saveData();
    }
    
    private static void generateReceipt() {
        System.out.println("\n===== Generate Receipt =====");
        
        // Get student
        System.out.println("\nSelect a student:");
        List<Student> students = system.getStudents();
        if (students.isEmpty()) {
            System.out.println("No students registered in the system.");
            return;
        }
        
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getName() + " (" + students.get(i).getRollNumber() + ")");
        }
        
        int studentIndex = getIntInput("Enter student number: ") - 1;
        if (studentIndex < 0 || studentIndex >= students.size()) {
            System.out.println("Invalid student number.");
            return;
        }
        Student student = students.get(studentIndex);
        
        // Get payment details
        System.out.println("\nEnter payment details:");
        
        // Get payment type
        System.out.println("\nSelect payment type:");
        System.out.println("1. Tuition Fee");
        System.out.println("2. Library Fee");
        System.out.println("3. Hostel Fee");
        System.out.println("4. Other");
        
        int paymentTypeIndex = getIntInput("Enter payment type number: ") - 1;
        String paymentType;
        switch (paymentTypeIndex) {
            case 0:
                paymentType = "Tuition Fee";
                break;
            case 1:
                paymentType = "Library Fee";
                break;
            case 2:
                paymentType = "Hostel Fee";
                break;
            case 3:
                System.out.print("Enter other payment type: ");
                paymentType = scanner.nextLine();
                break;
            default:
                System.out.println("Invalid payment type.");
                return;
        }
        
        // Get payment amount
        System.out.print("Enter payment amount: ");
        double amount = getDoubleInput("Payment amount:");
        
        // Get payment date
        Date date = getDateInput("Enter payment date (dd/MM/yyyy): ");
        
        // Get payment method
        System.out.print("Enter payment method (Cash/Check/Bank Transfer): ");
        String paymentMethod = scanner.nextLine();
        
        // Generate receipt number
        String receiptNumber = "REC-" + System.currentTimeMillis();
        
        // Create receipt
        FeeReceipt receipt = new FeeReceipt(receiptNumber, student, date, paymentMethod, "System", "Paid");
        
        // Create payment record
        FeePayment payment = new FeePayment(student, amount, date, true, paymentType, receiptNumber);
        receipt.addPayment(payment);
        
        // Save receipt and payment
        system.addFeeReceipt(receipt);
        system.recordFeePayment(payment);
        
        // Print receipt
        System.out.println("\n===== Fee Receipt =====");
        System.out.println("Receipt Number: " + receiptNumber);
        System.out.println("Date: " + dateFormat.format(date));
        System.out.println("Student: " + student.getName());
        System.out.println("Roll Number: " + student.getRollNumber());
        System.out.println("Payment Type: " + paymentType);
        System.out.println("Amount: " + amount);
        System.out.println("Payment Method: " + paymentMethod);
        
        // Generate physical receipt file
        String fileName = "receipt_" + receiptNumber + ".txt";
        if (receipt.saveReceiptToFile(fileName)) {
            System.out.println("\nReceipt file successfully generated: " + fileName);
        } else {
            System.out.println("\nFailed to generate receipt file.");
        }
        
        // Save changes
        system.saveData();
    }
    
    private static void viewOutstandingFees() {
        System.out.println("\n===== View Outstanding Fees =====");
        
        // Get student
        System.out.println("\nSelect a student:");
        List<Student> students = system.getStudents();
        if (students.isEmpty()) {
            System.out.println("No students registered in the system.");
            return;
        }
        
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getName() + " (" + students.get(i).getRollNumber() + ")");
        }
        
        int studentIndex = getIntInput("Enter student number: ") - 1;
        if (studentIndex < 0 || studentIndex >= students.size()) {
            System.out.println("Invalid student number.");
            return;
        }
        Student student = students.get(studentIndex);
        
        // Get all payments for this student
        List<FeePayment> payments = system.getFeePaymentsByStudent(student);
        if (payments.isEmpty()) {
            System.out.println("No fee records found for this student.");
            return;
        }
        
        // Calculate totals
        double totalPaid = 0.0;
        double totalTuition = 0.0;
        double totalLibrary = 0.0;
        double totalHostel = 0.0;
        
        for (FeePayment payment : payments) {
            if (payment.isPaid()) {
                totalPaid += payment.getAmount();
                switch (payment.getPaymentType()) {
                    case "Tuition Fee":
                        totalTuition += payment.getAmount();
                        break;
                    case "Library Fee":
                        totalLibrary += payment.getAmount();
                        break;
                    case "Hostel Fee":
                        totalHostel += payment.getAmount();
                        break;
                }
            }
        }
        
        // Calculate outstanding amounts
        double tuitionOutstanding = totalTuition - totalPaid;
        double libraryOutstanding = totalLibrary - totalPaid;
        double hostelOutstanding = totalHostel - totalPaid;
        
        // Display report
        System.out.println("\n===== Fee Report for " + student.getName() + " =====");
        System.out.println("Roll Number: " + student.getRollNumber());
        System.out.println("\nTotal Amounts Paid:");
        System.out.printf("Tuition: $%.2f\n", totalTuition);
        System.out.printf("Library: $%.2f\n", totalLibrary);
        System.out.printf("Hostel: $%.2f\n", totalHostel);
        System.out.printf("Total Paid: $%.2f\n", totalPaid);
        System.out.println("\nOutstanding Amounts:");
        System.out.printf("Tuition: $%.2f\n", tuitionOutstanding);
        System.out.printf("Library: $%.2f\n", libraryOutstanding);
        System.out.printf("Hostel: $%.2f\n", hostelOutstanding);
        System.out.printf("Total Outstanding: $%.2f\n", 
            tuitionOutstanding + libraryOutstanding + hostelOutstanding);
        
        // Save changes
        system.saveData();
    }
    // USER MANAGEMENT METHODS
    private static void userManagementMenu() {
        if (!system.getCurrentUser().hasPermission("manage_users")) {
            System.out.println("Access denied. You don't have permission to access user management.");
            return;
        }
        
        boolean back = false;
        while (!back) {
            System.out.println("\n===== User Management =====");
            System.out.println("1. Create New User");
            System.out.println("2. View All Users");
            System.out.println("3. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    createUser();
                    break;
                case 2:
                    viewAllUsers();
                    break;
                case 3:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Academic Management methods
    private static void academicManagementMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n===== Academic Management =====");
            System.out.println("1. Add Grade");
            System.out.println("2. View Academic Record");
            System.out.println("3. Add Final Exam Grade");
            System.out.println("4. Calculate GPA");
            System.out.println("5. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addGrade();
                    break;
                case 2:
                    viewAcademicRecord();
                    break;
                case 3:
                    addFinalExamGrade();
                    break;
                case 4:
                    calculateGPA();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addGrade() {
        System.out.println("\n===== Add Grade =====");
        
        // Get student
        Student student = findStudent();
        if (student == null) return;
        
        // Get subject
        Subject subject = findSubject();
        if (subject == null) return;

        // Get grade details
        System.out.print("Enter Assignment Name: ");
        String assignmentName = scanner.nextLine();

        System.out.println("\nSelect Grade Type:");
        System.out.println("1. Quiz (15%)");
        System.out.println("2. Assignment (15%)");
        System.out.println("3. Midterm (30%)");
        System.out.println("4. Final (40%)");
        String gradeType;
        switch (getIntInput("Enter choice: ")) {
            case 1: gradeType = "Quiz"; break;
            case 2: gradeType = "Assignment"; break;
            case 3: gradeType = "Midterm"; break;
            case 4: gradeType = "Final"; break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        double score = getDoubleInput("Enter score: ");
        double maxScore = getDoubleInput("Enter maximum possible score: ");
        System.out.print("Enter remarks (optional): ");
        String remarks = scanner.nextLine();
        System.out.print("Enter semester (e.g., Spring 2025): ");
        String semester = scanner.nextLine();
        
        // Create grade object
        Grade grade = new Grade(student, subject, assignmentName, gradeType, score, maxScore, new Date(), remarks, semester);
        
        // Add grade to student's record
        AcademicRecord record = system.getAcademicRecords().get(student.getId());
        if (record == null) {
            record = new AcademicRecord(student);
            system.getAcademicRecords().put(student.getId(), record);
        }
        record.addGrade(grade);
        System.out.println("Grade added successfully!");
        
        // Display grade details
        System.out.printf("Percentage Score: %.2f%%\n", grade.getPercentageScore());
        System.out.println("Letter Grade: " + grade.getLetterGrade());
        
        // Save data
        system.saveData();
    }

    private static void addFinalExamGrade() {
        System.out.println("\n===== Add Final Exam Grade =====");
        

        // Get student
        Student student = findStudent();
        if (student == null) return;

        // Get subject
        Subject subject = findSubject();
        if (subject == null) return;

        // Get grade details
        System.out.print("Enter semester (e.g., Spring 2025): ");
        String semester = scanner.nextLine();

        double score = getDoubleInput("Enter final exam score: ");
        double maxScore = getDoubleInput("Enter maximum possible score: ");

        System.out.print("Enter remarks (optional): ");
        String remarks = scanner.nextLine();

        // Create and add the final exam grade
        Grade finalGrade = new Grade(student, subject, "Final Examination", "Final",
                                   score, maxScore, new Date(), remarks, semester);

        // Add to academic record
        AcademicRecord record = system.getAcademicRecords().get(student.getId());
        if (record == null) {
            record = new AcademicRecord(student);
            system.getAcademicRecords().put(student.getId(), record);
        }
        record.addGrade(finalGrade);

        System.out.println("Final exam grade added successfully!");
        System.out.println("Percentage Score: " + String.format("%.2f%%", finalGrade.getPercentageScore()));
        System.out.println("Letter Grade: " + finalGrade.getLetterGrade());

        // Save changes
        system.saveData();
    }

    private static void calculateGPA() {
        System.out.println("\n===== Calculate GPA =====");

        // Get student
        Student student = findStudent();
        if (student == null) return;

        AcademicRecord record = system.getAcademicRecords().get(student.getId());
        if (record == null) {
            System.out.println("No academic record found for this student.");
            return;
        }

        System.out.println("\nSelect calculation type:");
        System.out.println("1. Cumulative GPA");
        System.out.println("2. Semester GPA");

        int choice = getIntInput("Enter choice: ");
        switch (choice) {
            case 1:
                double cgpa = record.calculateCumulativeGPA();
                System.out.println("Cumulative GPA: " + String.format("%.2f", cgpa));
                break;

            case 2:
                System.out.print("Enter semester (e.g., Spring 2025): ");
                String semester = scanner.nextLine();
                double semesterGpa = record.calculateSemesterGPA(semester);
                System.out.println("GPA for " + semester + ": " + String.format("%.2f", semesterGpa));
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void viewAcademicRecord() {
        System.out.println("\n===== View Academic Record =====");

        // Get student
        Student student = findStudent();
        if (student == null) return;

        AcademicRecord record = system.getAcademicRecords().get(student.getId());
        if (record == null) {
            System.out.println("No academic record found for this student.");
            return;
        }

        // Display the transcript
        System.out.println(record.generateTranscript());
    }



    // Helper methods for finding students and subjects
    private static Student findStudent() {
        System.out.print("Enter Student ID: ");
        try {
            int studentId = Integer.parseInt(scanner.nextLine());
            Student student = system.findStudentById(studentId);
            if (student == null) {
                System.out.println("Student not found.");
                return null;
            }
            
            // Check if current user has permission to access this student
            if (!system.getCurrentUser().canAccessStudent(student)) {
                System.out.println("You do not have permission to access this student's records.");
                return null;
            }
            
            return student;
        } catch (NumberFormatException e) {
            System.out.println("Invalid student ID format. Please enter a number.");
            return null;
        }
    }
    
    private static Subject findSubject() {
        System.out.print("Enter Subject ID: ");
        try {
            int subjectId = Integer.parseInt(scanner.nextLine());
            Subject subject = system.findSubjectById(subjectId);
            if (subject == null) {
                System.out.println("Subject not found.");
                return null;
            }
            return subject;
        } catch (NumberFormatException e) {
            System.out.println("Invalid subject ID format. Please enter a number.");
            return null;
        }
    }
    
    // User Management methods
    private static void createUser() {
        System.out.println("\n===== Create New User =====");
        
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();
        
        System.out.println("\nSelect role:");
        System.out.println("1. Administrator");
        System.out.println("2. Teacher");
        System.out.println("3. Financial Officer");
        System.out.println("4. Registrar");
        System.out.println("5. Student");
        
        int roleChoice = getIntInput("Enter choice: ");
        String role;
        String department = null;
        Integer associatedId = null;
        
        switch (roleChoice) {
            case 1:
                role = "Administrator";
                break;
            case 2:
                role = "Teacher";
                System.out.print("Enter department: ");
                department = scanner.nextLine();
                break;
            case 3:
                role = "Financial Officer";
                break;
            case 4:
                role = "Registrar";
                System.out.print("Enter department: ");
                department = scanner.nextLine();
                break;
            case 5:
                role = "Student";
                Student student = findStudent();
                if (student != null) {
                    department = student.getDepartment();
                    associatedId = student.getId();
                } else {
                    System.out.println("Failed to create user: Student not found.");
                    return;
                }
                break;
            default:
                System.out.println("Invalid role choice.");
                return;
        }
        
        switch (roleChoice) {
            case 1:
                role = "Administrator";
                break;
            case 2:
                role = "Teacher";
                System.out.print("Enter department: ");
                department = scanner.nextLine();
                break;
            case 3:
                role = "Financial Officer";
                break;
            case 4:
                role = "Registrar";
                break;
            case 5:
                role = "Student";
                associatedId = getIntInput("Enter student ID: ");
                Student student = system.findStudentById(associatedId);
                if (student == null) {
                    System.out.println("Error: Student ID not found!");
                    return;
                }
                break;
            default:
                System.out.println("Invalid role selected. Please try again.");
                return;
        }
        
        User newUser = new User(username, password, fullName, role, department, associatedId);
        system.addUser(newUser);
        
        System.out.println("User created successfully!");
        System.out.println("Username: " + username);
        System.out.println("Full Name: " + fullName);
        System.out.println("Role: " + role);
        
        // Save changes
        system.saveData();
    }
    
    private static void changePassword() {
        System.out.println("\n===== Change Password =====");
        
        // Get current user
        System.out.print("Enter your current username: ");
        String username = scanner.nextLine();
        
        // Find user
        User user = system.findUserByUsername(username);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        
        // Verify current password
        System.out.print("Enter your current password: ");
        String currentPassword = scanner.nextLine();
        if (!user.getPassword().equals(currentPassword)) {
            System.out.println("Incorrect current password.");
            return;
        }
        
        // Get new password
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        
        // Update password
        user.setPassword(newPassword);
        
        System.out.println("Password changed successfully!");
        
        // Save changes
        system.saveData();
    }
    
    private static void viewAllUsers() {
        System.out.println("\n===== All Users =====");
        
        List<User> users = system.getUsers();
        if (users.isEmpty()) {
            System.out.println("No users registered in the system.");
            return;
        }
        
        System.out.println("\nList of Users:");
        System.out.printf("%-15s %-20s %-15s\n", "Username", "Full Name", "Role");
        System.out.println("--------------------------------------------------");
        
        for (User user : users) {
            System.out.printf("%-15s %-20s %-15s\n", 
                user.getUsername(), 
                user.getFullName(), 
                user.getRole());
        }
    }
    

}
    

