import java.util.ArrayList;
import java.util.Scanner;

class Student {
    private String studentId;
    private String name;
    private String program;
    private String email;

    Student(String studentId, String name, String program, String email) {
        this.studentId = studentId.trim().toUpperCase();   
        this.name = name.trim();
        this.program = program.trim().toUpperCase();       
        this.email = email.trim().toLowerCase();
    }

    public String getStudentId() { return studentId; }
    public String getName()      { return name; }
    public String getProgram()   { return program; }
    public String getEmail()     { return email; }

    public void display() {
        System.out.printf("%-10s %-22s %-12s %s%n", studentId, name, program, email);
    }
}

class Course {
    private String courseCode;
    private String courseName;
    private int credits;

    Course(String courseCode, String courseName, int credits) {
        this.courseCode = courseCode.trim().toUpperCase();
        this.courseName = courseName.trim();
        this.credits = credits;
    }

    public String getCourseCode() { return courseCode; }
    public String getCourseName() { return courseName; }
    public int getCredits()       { return credits; }

    public void display() {
        System.out.printf("%-10s %-35s %d%n", courseCode, courseName, credits);
    }
}

public class UniversityManagementSystem {
    static ArrayList<Student> students = new ArrayList<>();
    static ArrayList<Course> courses = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Sample data
        students.add(new Student("S101", "Aarav Mehta", "BTech CSE", "aarav.mehta@sau.ac.in"));
        students.add(new Student("S102", "Diya Kapoor", "BTech CSE", "diya.kapoor@sau.ac.in"));
        students.add(new Student("S103", "Rohan Das", "BBA", "rohan.das@sau.ac.in"));
        students.add(new Student("S104", "Ananya Iyer", "MCA", "ananya.iyer@sau.ac.in"));
        courses.add(new Course("CS201", "Object Oriented Programming in Java", 4));
        courses.add(new Course("CS203", "Design and Analysis of Algorithms", 4));
        courses.add(new Course("CS205", "Database Management Systems", 4));
        courses.add(new Course("MA201", "Probability and Statistics", 4));

        int choice;
        do {
            System.out.println("\n===== UNIVERSITY MANAGEMENT SYSTEM =====");
            System.out.println("1. Add student");
            System.out.println("2. Display all students");
            System.out.println("3. Add course");
            System.out.println("4. Display all courses");
            System.out.println("5. Search student by ID");
            System.out.println("6. Search student by name");
            System.out.println("7. Search course by code / name");
            System.out.println("8. Display statistics");
            System.out.println("9. String operations on student & course data");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = readInt();

            switch (choice) {
                case 1: addStudent(); break;
                case 2: displayStudents(); break;
                case 3: addCourse(); break;
                case 4: displayCourses(); break;
                case 5: searchStudentById(); break;
                case 6: searchStudentByName(); break;
                case 7: searchCourse(); break;
                case 8: displayStatistics(); break;
                case 9: stringOperations(); break;
                case 0: System.out.println("Exiting..."); break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    static void addStudent() {
        System.out.print("Enter student ID: ");
        String id = sc.nextLine().trim();
        if (id.isEmpty()) { System.out.println("ID cannot be empty."); return; }
        if (findStudentById(id) != null) {
            System.out.println("A student with ID " + id.toUpperCase() + " already exists.");
            return;
        }
        System.out.print("Enter name: ");
        String name = sc.nextLine().trim();
        System.out.print("Enter program: ");
        String program = sc.nextLine().trim();
        System.out.print("Enter email: ");
        String email = sc.nextLine().trim();

        if (name.isEmpty() || program.isEmpty()) {
            System.out.println("Name and program cannot be empty.");
            return;
        }
        if (!isValidEmail(email)) {
            System.out.println("Invalid email address.");
            return;
        }
        students.add(new Student(id, name, program, email));
        System.out.println("Student added successfully.");
    }

    static void displayStudents() {
        if (students.isEmpty()) { System.out.println("No students found."); return; }
        System.out.printf("%-10s %-22s %-12s %s%n", "ID", "NAME", "PROGRAM", "EMAIL");
        for (Student s : students) s.display();
    }

    static void addCourse() {
        System.out.print("Enter course code: ");
        String code = sc.nextLine().trim();
        if (code.isEmpty()) { System.out.println("Code cannot be empty."); return; }
        for (Course c : courses) {
            if (c.getCourseCode().equalsIgnoreCase(code)) {
                System.out.println("Course " + code.toUpperCase() + " already exists.");
                return;
            }
        }
        System.out.print("Enter course name: ");
        String name = sc.nextLine().trim();
        System.out.print("Enter credits: ");
        int credits = readInt();
        if (name.isEmpty() || credits <= 0) {
            System.out.println("Invalid course details.");
            return;
        }
        courses.add(new Course(code, name, credits));
        System.out.println("Course added successfully.");
    }

    static void displayCourses() {
        if (courses.isEmpty()) { System.out.println("No courses found."); return; }
        System.out.printf("%-10s %-35s %s%n", "CODE", "COURSE NAME", "CREDITS");
        for (Course c : courses) c.display();
    }

    static Student findStudentById(String id) {
        for (Student s : students)
            if (s.getStudentId().equalsIgnoreCase(id.trim())) return s;
        return null;
    }

    static void searchStudentById() {
        System.out.print("Enter student ID: ");
        String id = sc.nextLine();
        Student s = findStudentById(id);
        if (s == null) {
            System.out.println("Student with ID \"" + id.trim() + "\" not found.");   // (h)
        } else {
            System.out.printf("%-10s %-22s %-12s %s%n", "ID", "NAME", "PROGRAM", "EMAIL");
            s.display();
        }
    }

    static void searchStudentByName() {
        System.out.print("Enter name (full/partial): ");
        String query = sc.nextLine().trim().toLowerCase();
        if (query.isEmpty()) { System.out.println("Name cannot be empty."); return; }

        int found = 0;
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(query)) {
                if (found == 0) System.out.printf("%-10s %-22s %-12s %s%n", "ID", "NAME", "PROGRAM", "EMAIL");
                s.display();
                found++;
            }
        }
        if (found == 0) System.out.println("No student found with name \"" + query + "\".");   // (h)
    }

    static void searchCourse() {
        System.out.print("Enter course code or name: ");
        String query = sc.nextLine().trim();
        if (query.isEmpty()) { System.out.println("Input cannot be empty."); return; }

        int found = 0;
        for (Course c : courses) {
            boolean codeMatch = c.getCourseCode().equalsIgnoreCase(query);
            boolean nameMatch = c.getCourseName().toLowerCase().contains(query.toLowerCase());
            if (codeMatch || nameMatch) {
                if (found == 0) System.out.printf("%-10s %-35s %s%n", "CODE", "COURSE NAME", "CREDITS");
                c.display();
                found++;
            }
        }
        if (found == 0) System.out.println("No course found for \"" + query + "\".");   // (h)
    }

    static void displayStatistics() {
        System.out.println("\n----- STATISTICS -----");
        System.out.println("Total students : " + students.size());
        System.out.println("Total courses  : " + courses.size());

        ArrayList<String> programs = new ArrayList<>();
        for (Student s : students)
            if (!programs.contains(s.getProgram())) programs.add(s.getProgram());

        System.out.println("\nStudents in each program:");
        for (String p : programs) {
            int count = 0;
            for (Student s : students)
                if (s.getProgram().equals(p)) count++;
            System.out.printf("  %-12s : %d%n", p, count);
        }

        int totalCredits = 0;
        for (Course c : courses) totalCredits += c.getCredits();
        System.out.println("\nTotal credits offered : " + totalCredits);
        if (!courses.isEmpty())
            System.out.printf("Average credits/course: %.2f%n", (double) totalCredits / courses.size());
    }

    static void stringOperations() {
        if (students.isEmpty() || courses.isEmpty()) {
            System.out.println("Need at least one student and one course.");
            return;
        }
        System.out.println("\n----- STRING OPERATIONS -----");

        System.out.println("\n1. Usernames extracted from email (substring/indexOf):");
        for (Student s : students) {
            String email = s.getEmail();
            String username = email.substring(0, email.indexOf('@'));
            System.out.println("   " + s.getName() + " -> " + username);
        }

        System.out.println("\n2. Student initials (split/charAt):");
        for (Student s : students) {
            String[] parts = s.getName().split("\\s+");
            StringBuilder initials = new StringBuilder();
            for (String part : parts) initials.append(Character.toUpperCase(part.charAt(0)));
            System.out.println("   " + s.getName() + " -> " + initials);
        }

        ArrayList<Student> sorted = new ArrayList<>(students);
        for (int i = 0; i < sorted.size() - 1; i++) {
            for (int j = 0; j < sorted.size() - 1 - i; j++) {
                if (sorted.get(j).getName().compareToIgnoreCase(sorted.get(j + 1).getName()) > 0) {
                    Student temp = sorted.get(j);
                    sorted.set(j, sorted.get(j + 1));
                    sorted.set(j + 1, temp);
                }
            }
        }
        System.out.println("\n3. Students sorted by name (compareToIgnoreCase):");
        for (Student s : sorted) System.out.println("   " + s.getName());

        System.out.println("\n4. Department from course code (substring):");
        for (Course c : courses) {
            String dept = c.getCourseCode().replaceAll("[0-9]", "");
            String level = c.getCourseCode().substring(dept.length(), dept.length() + 1);
            System.out.println("   " + c.getCourseCode() + " -> Dept: " + dept + ", Year level: " + level);
        }

        System.out.println("\n5. Course name processing (toUpperCase/length/abbreviation):");
        for (Course c : courses) {
            StringBuilder abbr = new StringBuilder();
            for (String w : c.getCourseName().split("\\s+")) {
                if (!w.equalsIgnoreCase("and") && !w.equalsIgnoreCase("in") && !w.equalsIgnoreCase("of"))
                    abbr.append(Character.toUpperCase(w.charAt(0)));
            }
            System.out.println("   " + c.getCourseName().toUpperCase()
                    + " | Length: " + c.getCourseName().length()
                    + " | Abbr: " + abbr);
        }

        if (students.size() >= 2) {
            Student a = students.get(0), b = students.get(1);
            System.out.println("\n6. Comparing programs of " + a.getName() + " and " + b.getName() + ":");
            System.out.println("   equals()           : " + a.getProgram().equals(b.getProgram()));
            System.out.println("   equalsIgnoreCase() : " + a.getProgram().equalsIgnoreCase(b.getProgram()));
            System.out.println("   (Always use equals() for content comparison, not ==)");
        }

        System.out.println("\n7. University email check (endsWith):");
        for (Student s : students) {
            boolean official = s.getEmail().endsWith("@sau.ac.in");
            System.out.println("   " + s.getEmail() + " -> " + (official ? "Official" : "Personal"));
        }
    }

    static boolean isValidEmail(String email) {
        int at = email.indexOf('@');
        int dot = email.lastIndexOf('.');
        return at > 0 && at == email.lastIndexOf('@') && dot > at + 1
                && dot < email.length() - 1 && !email.contains(" ");
    }

    static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid number: ");
            }
        }
    }
}
