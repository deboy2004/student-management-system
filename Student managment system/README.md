<div align="center">

# 🎓 Student Information Management System

### A comprehensive Java-based solution for educational institutions

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![OOP](https://img.shields.io/badge/OOP-Principles-blue?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Active-success?style=for-the-badge)

</div>

---

## 📋 Overview

A robust desktop application designed for educational institutions to efficiently manage student data, faculty, courses, and administrative tasks. Built with Object-Oriented Programming principles, this system provides a secure, menu-based interface for comprehensive school management.

## ✨ Features

### 👥 User Management
- 🔐 Secure authentication system with role-based access control
- 👤 Multiple user roles (Admin, Teacher, Staff)
- 🔑 Password management and security

### 🎓 Student Management
- ➕ Register new students with detailed information
- 🔍 Search and filter student records
- ✏️ Update student information
- 📄 View comprehensive student profiles

### 👨‍🏫 Teacher Management
- 💼 Manage faculty information
- 📅 Track teaching assignments
- 📊 Monitor teacher performance

### 📚 Course & Subject Management
- 📖 Create and manage courses
- 📝 Assign subjects to courses
- 👥 Enroll students in courses

### 📆 Attendance Tracking
- ✅ Mark daily attendance
- 📊 Generate attendance reports
- 🚨 Track attendance patterns

### 💰 Fee Management
- 💳 Process fee payments
- 🧻 Track payment history
- 📉 Generate financial reports

### 📊 Reporting System
- 📝 Comprehensive reports for all modules
- 📊 Analytics and insights
- 💾 Export capabilities

## 🏛️ OOP Concepts Implemented

This project demonstrates core Object-Oriented Programming principles:

| Concept | Implementation |
|---------|----------------|
| **📦 Abstract Classes** | `Person` class serves as an abstract base class |
| **🧱 Inheritance** | `Student` and `Teacher` extend `Person` |
| **🔄 Polymorphism** | Method overriding in derived classes |
| **🔌 Interfaces** | `Serializable` implemented for data persistence |
| **🔒 Encapsulation** | Private attributes with getter/setter methods |
| **🎯 Objects as Parameters** | Objects passed as method parameters |
| **🛠️ Constructors** | Default and parameterized constructors |

## 💾 Data Persistence

The system utilizes **Java Serialization** for persistent file-based storage, ensuring data is saved and retrieved efficiently across sessions.

## 🚀 Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Command line interface (Terminal/CMD)

### Installation & Running

1. **Clone or download the project**
   ```bash
   cd "Student managment system"
   ```

2. **Compile all Java files**
   ```bash
   javac *.java
   ```

3. **Run the application**
   ```bash
   java Main
   ```

4. **Login with default credentials**
   ```
   Username: admin
   Password: admin
   ```

> 💡 **Tip**: Change the default password after first login for security!

## 📁 Project Structure

```
📂 Student Management System
├── 📄 Person.java                    # Abstract base class
├── 🎓 Student.java                   # Student entity
├── 👨🏫 Teacher.java                   # Teacher entity
├── 📚 Course.java                    # Course management
├── 📖 Subject.java                   # Subject management
├── 📆 Attendance.java                # Attendance tracking
├── 💰 FeePayment.java                # Payment processing
├── 👤 User.java                      # Authentication & authorization
├── 🏛️ StudentInformationSystem.java  # Core system logic
├── 📦 Main.java                      # Application entry point
└── ⚙️ MainFunctions.java             # UI functionality
```

## 🔐 Security Features

- 🔑 Password-protected authentication
- 🛡️ Role-based access control (RBAC)
- 🔒 Permission-based feature access
- 🔄 Password change functionality

## 📝 License

This project is available for educational purposes.

---

<div align="center">

**Built with ❤️ using Java and OOP Principles**

🎓 Perfect for learning Object-Oriented Programming concepts!

</div>
