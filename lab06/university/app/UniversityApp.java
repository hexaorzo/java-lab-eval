package lab06.university.app;

import java.util.List;
import java.util.Scanner;

import lab06.university.model.Course;
import lab06.university.model.Student;
import lab06.university.service.UniversityManager;





public class UniversityApp {

    private static final int DEFAULT_CAPACITY = 3;

    private static final UniversityManager MANAGER = new UniversityManager();
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        loadSampleData();

        boolean running = true;
        while (running) {
            printMenu();
            switch (readLine()) {
                
                case "1":
                    addStudent();
                    break;
                case "2":
                    System.out.print("Enter student ID to remove: ");
                    if (MANAGER.removeStudent(readLine())) {
                        System.out.println("Student removed.");
                    }
                    break;
                case "3":
                    MANAGER.displayAllStudents();
                    break;
                case "4":
                    searchStudentById();
                    break;
                case "5":
                    System.out.print("Enter name (full or partial): ");
                    printStudents(MANAGER.findStudentsByName(readLine()), "No matching student found.");
                    break;
                case "6":
                    System.out.print("Enter program (e.g. BTECH): ");
                    printStudents(MANAGER.filterStudentsByProgram(readLine()), "No students in that program.");
                    break;
                case "7":
                    MANAGER.displayStudentsSortedByName();
                    break;
                
                case "8":
                    addCourse();
                    break;
                case "9":
                    System.out.print("Enter course code to remove: ");
                    if (MANAGER.removeCourse(readLine())) {
                        System.out.println("Course removed.");
                    }
                    break;
                case "10":
                    MANAGER.displayAllCourses();
                    break;
                case "11":
                    searchCourseByCode();
                    break;
                case "12":
                    System.out.print("Enter course name (full or partial): ");
                    printCourses(MANAGER.findCoursesByName(readLine()), "No matching course found.");
                    break;
                case "13":
                    filterCoursesByCredits();
                    break;
                case "14":
                    System.out.print("Enter department prefix (e.g. CS): ");
                    MANAGER.displayCoursesByDepartment(readLine());
                    break;
                
                case "15":
                    registerStudent();
                    break;
                case "16":
                    withdrawStudent();
                    break;
                case "17":
                    displayCoursesOfStudent();
                    break;
                case "18":
                    displayStudentsOfCourse();
                    break;
                
                case "19":
                    MANAGER.displayStatistics();
                    break;
                case "20":
                    MANAGER.displayEnrollmentStatistics();
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
        System.out.println("Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\n========== University Management System ==========");
        System.out.println("Students : 1.Add  2.Remove  3.Display all  4.Search by ID  5.Search by name");
        System.out.println("           6.Filter by program  7.Sort by name");
        System.out.println("Courses  : 8.Add  9.Remove  10.Display all  11.Search by code  12.Search by name");
        System.out.println("           13.Filter by credits  14.Courses of a department");
        System.out.println("Register : 15.Register student  16.Withdraw student");
        System.out.println("           17.Courses of a student  18.Students of a course");
        System.out.println("Reports  : 19.General statistics  20.Enrollment statistics");
        System.out.println("0. Exit");
        System.out.print("Select option: ");
    }

    private static void loadSampleData() {
        MANAGER.addStudent(new Student("S101", "Rahul Sharma", "BTech", "rahul.sharma@sau.ac.in"));
        MANAGER.addStudent(new Student("S102", "Priya Verma", "MTech", "priya.verma@sau.ac.in"));
        MANAGER.addStudent(new Student("S103", "Amit Kumar", "BTech", "amit.kumar@sau.ac.in"));
        MANAGER.addStudent(new Student("S104", "Sneha Gupta", "MCA", "sneha.gupta@sau.ac.in"));
        MANAGER.addStudent(new Student("S105", "Rohan Sharma", "BTech", "rohan.sharma@sau.ac.in"));

        MANAGER.addCourse(new Course("CS201", "Object Oriented Programming", 4, DEFAULT_CAPACITY));
        MANAGER.addCourse(new Course("CS202", "Data Structures", 4, DEFAULT_CAPACITY));
        MANAGER.addCourse(new Course("MA101", "Discrete Mathematics", 3, DEFAULT_CAPACITY));
        MANAGER.addCourse(new Course("CS301", "Database Management Systems", 3, DEFAULT_CAPACITY));
        MANAGER.addCourse(new Course("HS101", "Technical Communication", 2, DEFAULT_CAPACITY));

        MANAGER.registerStudent("S101", "CS201");
        MANAGER.registerStudent("S101", "MA101");
        MANAGER.registerStudent("S102", "CS201");
        MANAGER.registerStudent("S103", "CS202");
        MANAGER.registerStudent("S104", "CS201");
    }

    private static void addStudent() {
        System.out.print("Student ID: ");
        String studentId = readLine();
        System.out.print("Name      : ");
        String studentName = readLine();
        System.out.print("Program   : ");
        String program = readLine();
        System.out.print("Email     : ");
        String email = readLine();
        if (studentId.isEmpty() || studentName.isEmpty() || program.isEmpty()) {
            System.out.println("ID, name and program are required.");
            return;
        }
        Student student = new Student(studentId, studentName, program, email);
        if (MANAGER.addStudent(student)) {
            System.out.println("Student added:");
            student.displayStudent();
            if (!student.hasValidEmail()) {
                System.out.println("Warning: the email address does not look valid.");
            }
        }
    }

    private static void addCourse() {
        System.out.print("Course code : ");
        String courseCode = readLine();
        System.out.print("Course name : ");
        String courseName = readLine();
        System.out.print("Credits     : ");
        int credits = readInt();
        System.out.print("Max capacity: ");
        int capacity = readInt();
        if (courseCode.isEmpty() || courseName.isEmpty() || credits <= 0 || capacity <= 0) {
            System.out.println("Code, name, positive credits and positive capacity are required.");
            return;
        }
        Course course = new Course(courseCode, courseName, credits, capacity);
        if (MANAGER.addCourse(course)) {
            System.out.println("Course added:");
            course.displayCourse();
        }
    }

    private static void searchStudentById() {
        System.out.print("Enter student ID: ");
        String studentId = readLine();
        Student student = MANAGER.findStudentById(studentId);
        if (student == null) {
            System.out.println("Student with ID \"" + studentId + "\" not found.");
        } else {
            student.displayStudent();
        }
    }

    private static void searchCourseByCode() {
        System.out.print("Enter course code: ");
        String courseCode = readLine();
        Course course = MANAGER.findCourseByCode(courseCode);
        if (course == null) {
            System.out.println("Course with code \"" + courseCode + "\" not found.");
        } else {
            course.displayCourse();
        }
    }

    private static void filterCoursesByCredits() {
        System.out.print("Minimum credits: ");
        int minimumCredits = readInt();
        System.out.print("Maximum credits (same value for an exact match): ");
        int maximumCredits = readInt();
        printCourses(MANAGER.filterCoursesByCredits(minimumCredits, maximumCredits),
                "No courses with " + minimumCredits + "-" + maximumCredits + " credits.");
    }

    private static void registerStudent() {
        System.out.print("Student ID : ");
        String studentId = readLine();
        System.out.print("Course code: ");
        String courseCode = readLine();
        if (MANAGER.registerStudent(studentId, courseCode)) {
            System.out.println("Registered successfully. Seats left in " + courseCode.toUpperCase() + ": "
                    + (MANAGER.findCourseByCode(courseCode).getMaxCapacity() - MANAGER.getEnrolledCount(courseCode)));
        }
    }

    private static void withdrawStudent() {
        System.out.print("Student ID : ");
        String studentId = readLine();
        System.out.print("Course code: ");
        String courseCode = readLine();
        if (MANAGER.withdrawStudent(studentId, courseCode)) {
            System.out.println("Withdrawn successfully.");
        }
    }

    private static void displayCoursesOfStudent() {
        System.out.print("Enter student ID: ");
        String studentId = readLine();
        Student student = MANAGER.findStudentById(studentId);
        if (student == null) {
            System.out.println("Student with ID \"" + studentId + "\" not found.");
            return;
        }
        System.out.println("Courses registered by " + student.getStudentName() + ":");
        printCourses(MANAGER.getCoursesOfStudent(studentId), "  No courses registered.");
    }

    private static void displayStudentsOfCourse() {
        System.out.print("Enter course code: ");
        String courseCode = readLine();
        Course course = MANAGER.findCourseByCode(courseCode);
        if (course == null) {
            System.out.println("Course with code \"" + courseCode + "\" not found.");
            return;
        }
        System.out.println("Students registered in " + course.getCourseCode() + " - " + course.getCourseName()
                + " (" + MANAGER.getEnrolledCount(courseCode) + "/" + course.getMaxCapacity() + "):");
        printStudents(MANAGER.getStudentsOfCourse(courseCode), "  No students registered.");
    }

    private static void printStudents(List<Student> students, String emptyMessage) {
        if (students.isEmpty()) {
            System.out.println(emptyMessage);
        }
        for (Student student : students) {
            student.displayStudent();
        }
    }

    private static void printCourses(List<Course> courses, String emptyMessage) {
        if (courses.isEmpty()) {
            System.out.println(emptyMessage);
        }
        for (Course course : courses) {
            course.displayCourse();
        }
    }

    private static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(readLine());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a whole number: ");
            }
        }
    }

    private static String readLine() {
        if (!SCANNER.hasNextLine()) {
            System.out.println("\nInput ended.");
            System.exit(0);
        }
        return SCANNER.nextLine().trim();
    }
}
