import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;

// Database Configuration Class
class DatabaseConfig {
    private static final String URL = "jdbc:mysql://localhost:3306/student_management";
    private static final String USER = "root";
    private static final String PASSWORD = "asdf";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

// Student Model Class
class Student {
    private int id;
    private String studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private LocalDate enrollmentDate;
    private String major;
    private double gpa;
    private String status;
    
    // Constructor
    public Student(String studentId, String firstName, String lastName, 
                   String email, String phone, LocalDate dateOfBirth,
                   String major, double gpa) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.major = major;
        this.gpa = gpa;
    }
    
    // Getters and Setters
    public String getStudentId() { return studentId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getMajor() { return major; }
    public double getGpa() { return gpa; }
    
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setMajor(String major) { this.major = major; }
    public void setGpa(double gpa) { this.gpa = gpa; }
}

// Student DAO (Data Access Object) Class
class StudentDAO {
    
    // Create - Add new student
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO students (student_id, first_name, last_name, email, " +
                     "phone, date_of_birth, major, gpa) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, student.getStudentId());
            pstmt.setString(2, student.getFirstName());
            pstmt.setString(3, student.getLastName());
            pstmt.setString(4, student.getEmail());
            pstmt.setString(5, student.getPhone());
            pstmt.setDate(6, Date.valueOf(student.getDateOfBirth()));
            pstmt.setString(7, student.getMajor());
            pstmt.setDouble(8, student.getGpa());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
            return false;
        }
    }
    
    // Read - Get student by ID
    public void getStudentById(String studentId) {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                displayStudent(rs);
            } else {
                System.out.println("Student not found!");
            }
            
        } catch (SQLException e) {
            System.out.println("Error retrieving student: " + e.getMessage());
        }
    }
    
    // Read - Get all students
    public void getAllStudents() {
        String sql = "SELECT * FROM students ORDER BY student_id";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n" + "=".repeat(120));
            System.out.printf("%-10s %-15s %-15s %-30s %-15s %-10s %-5s%n",
                            "ID", "First Name", "Last Name", "Email", "Phone", "Major", "GPA");
            System.out.println("=".repeat(120));
            
            while (rs.next()) {
                System.out.printf("%-10s %-15s %-15s %-30s %-15s %-10s %-5.2f%n",
                    rs.getString("student_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("major"),
                    rs.getDouble("gpa"));
            }
            System.out.println("=".repeat(120) + "\n");
            
        } catch (SQLException e) {
            System.out.println("Error retrieving students: " + e.getMessage());
        }
    }
    
    // Update - Update student information
    public boolean updateStudent(String studentId, String field, String value) {
        String sql = "UPDATE students SET " + field + " = ? WHERE student_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            if (field.equals("gpa")) {
                pstmt.setDouble(1, Double.parseDouble(value));
            } else {
                pstmt.setString(1, value);
            }
            pstmt.setString(2, studentId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
            return false;
        }
    }
    
    // Delete - Remove student
    public boolean deleteStudent(String studentId) {
        String sql = "DELETE FROM students WHERE student_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, studentId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
            return false;
        }
    }
    
    // Search students by major
    public void searchByMajor(String major) {
        String sql = "SELECT * FROM students WHERE major LIKE ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + major + "%");
            ResultSet rs = pstmt.executeQuery();
            
            System.out.println("\nStudents in " + major + ":");
            System.out.println("-".repeat(120));
            
            boolean found = false;
            while (rs.next()) {
                displayStudent(rs);
                found = true;
            }
            
            if (!found) {
                System.out.println("No students found in this major.");
            }
            
        } catch (SQLException e) {
            System.out.println("Error searching students: " + e.getMessage());
        }
    }
    
    // Helper method to display student information
    private void displayStudent(ResultSet rs) throws SQLException {
        System.out.println("\nStudent ID: " + rs.getString("student_id"));
        System.out.println("Name: " + rs.getString("first_name") + " " + rs.getString("last_name"));
        System.out.println("Email: " + rs.getString("email"));
        System.out.println("Phone: " + rs.getString("phone"));
        System.out.println("Date of Birth: " + rs.getDate("date_of_birth"));
        System.out.println("Enrollment Date: " + rs.getDate("enrollment_date"));
        System.out.println("Major: " + rs.getString("major"));
        System.out.println("GPA: " + rs.getDouble("gpa"));
        System.out.println("Status: " + rs.getString("status"));
        System.out.println("-".repeat(50));
    }
}

// Main Application Class
public class StudentRecordManager {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentDAO studentDAO = new StudentDAO();
    
    public static void main(String[] args) {
        System.out.println("===== Student Record Management System =====\n");
        
        while (true) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    searchByMajor();
                    break;
                case 7:
                    System.out.println("Thank you for using Student Record Manager!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
    
    private static void displayMenu() {
        System.out.println("\n===== MENU =====");
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student by ID");
        System.out.println("4. Update Student Information");
        System.out.println("5. Delete Student");
        System.out.println("6. Search by Major");
        System.out.println("7. Exit");
        System.out.println("================\n");
    }
    
    private static void addStudent() {
        System.out.println("\n--- Add New Student ---");
        
        String studentId = getInput("Student ID: ");
        String firstName = getInput("First Name: ");
        String lastName = getInput("Last Name: ");
        String email = getInput("Email: ");
        String phone = getInput("Phone: ");
        String dobStr = getInput("Date of Birth (YYYY-MM-DD): ");
        String major = getInput("Major: ");
        double gpa = getDoubleInput("GPA: ");
        
        try {
            LocalDate dob = LocalDate.parse(dobStr);
            Student student = new Student(studentId, firstName, lastName, email, 
                                        phone, dob, major, gpa);
            
            if (studentDAO.addStudent(student)) {
                System.out.println("Student added successfully!");
            } else {
                System.out.println("Failed to add student.");
            }
        } catch (Exception e) {
            System.out.println("Error: Invalid date format or data.");
        }
    }
    
    private static void viewAllStudents() {
        System.out.println("\n--- All Students ---");
        studentDAO.getAllStudents();
    }
    
    private static void searchStudent() {
        System.out.println("\n--- Search Student ---");
        String studentId = getInput("Enter Student ID: ");
        studentDAO.getStudentById(studentId);
    }
    
    private static void updateStudent() {
        System.out.println("\n--- Update Student ---");
        String studentId = getInput("Enter Student ID: ");
        
        System.out.println("\nWhat would you like to update?");
        System.out.println("1. First Name");
        System.out.println("2. Last Name");
        System.out.println("3. Email");
        System.out.println("4. Phone");
        System.out.println("5. Major");
        System.out.println("6. GPA");
        
        int choice = getIntInput("Enter choice: ");
        String field = "";
        
        switch (choice) {
            case 1: field = "first_name"; break;
            case 2: field = "last_name"; break;
            case 3: field = "email"; break;
            case 4: field = "phone"; break;
            case 5: field = "major"; break;
            case 6: field = "gpa"; break;
            default:
                System.out.println("Invalid choice!");
                return;
        }
        
        String newValue = getInput("Enter new value: ");
        
        if (studentDAO.updateStudent(studentId, field, newValue)) {
            System.out.println("Student updated successfully!");
        } else {
            System.out.println("Failed to update student.");
        }
    }
    
    private static void deleteStudent() {
        System.out.println("\n--- Delete Student ---");
        String studentId = getInput("Enter Student ID: ");
        
        String confirm = getInput("Are you sure you want to delete this student? (yes/no): ");
        if (confirm.equalsIgnoreCase("yes")) {
            if (studentDAO.deleteStudent(studentId)) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Failed to delete student.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
    
    private static void searchByMajor() {
        System.out.println("\n--- Search by Major ---");
        String major = getInput("Enter Major: ");
        studentDAO.searchByMajor(major);
    }
    
    private static String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }
    
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }
}
