# Student Information Management System

A Java-based desktop application for educational institutions to manage student data efficiently according to OOP principles.
# Group members
       Id_no.                                          Name
1.NaScR/3789/16	...............................YABSIRA GUGSSA GURARA
2.NaScR/3440/16	...............................SEID MUHIDIN ALI
3.NaScR/1447/16	...............................BEREDA NEGESA WAKO
4.NaScR/1790/16	...............................DEBELA GIRMA GELCHU
5.NaScR/2549/16	...............................KALKIDAN SHIFERAW AYELE
6.NaScR/3672/16	...............................TITEM MULUGETA TEKLE
7.NaScR/1048/16	...............................ABEBU ALEBACHEW BIYAZIN

## Features

- Menu-based interface with comprehensive functionalities
- Student registration and management
- Teacher management
- Course and subject management
- Attendance tracking
- Fee payment management
- Reporting capabilities
- User authentication system

## OOP Concepts Used

- **Abstract Classes**: Person class serves as an abstract base class
- **Inheritance**: Student and Teacher inherit from Person
- **Polymorphism**: Method overriding in derived classes
- **Interfaces**: Serializable implemented for file storage
- **Encapsulation**: Private attributes with getter/setter methods
- **Objects as Parameters**: Using objects as method parameters throughout
- **Constructors**: Multiple constructors (default and parameterized)

## File-Based Storage

The system uses Java's serialization for persistent data storage.

## How to Run

1. Compile all Java files:
   ```
   javac *.java
   ```

2. Run the application:
   ```
   java Main
   ```

3. Default login:
   - Username: admin
   - Password: admin

## Project Structure

- `Person.java` - Abstract base class for all people
- `Student.java` - Student class extending Person
- `Teacher.java` - Teacher class extending Person
- `Course.java` - Class for course management
- `Subject.java` - Class for subject management 
- `Attendance.java` - Class for tracking attendance
- `FeePayment.java` - Class for managing payments
- `User.java` - Class for authentication
- `StudentInformationSystem.java` - Main system class
- `Main.java` - Entry point with UI implementation
- `MainFunctions.java` - Implementation of UI functionality
