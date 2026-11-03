package lab07.university.app;

import java.util.List;
import java.util.Scanner;

import lab07.university.model.Course;
import lab07.university.model.Faculty;
import lab07.university.model.Feedback;
import lab07.university.model.Student;
import lab07.university.model.UniversityMember;
import lab07.university.service.UniversityManager;





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
                    addFaculty();
                    break;
                case "20":
                    MANAGER.displayAllFaculty();
                    break;
                case "21":
                    MANAGER.displayAllProfiles();
                    break;
                case "22":
                    searchMembers();
                    break;
                case "23":
                    System.out.print("Enter member ID to remove: ");
                    if (MANAGER.removeMember(readLine())) {
                        System.out.println("Member removed.");
                    }
                    break;
                
                case "24":
                    assignInstructor();
                    break;
                case "25":
                    displayCoursesOfFaculty();
                    break;
                case "26":
                    printCourses(MANAGER.getCoursesWithoutInstructor(), "Every course has an instructor.");
                    break;
                
                case "27":
                    giveFeedback();
                    break;
                case "28":
                    displayAllFeedback();
                    break;
                
                case "29":
                    MANAGER.displayStatistics();
                    break;
                case "30":
                    MANAGER.displayEnrollmentStatistics();
                    break;
                case "31":
                    runDemonstration();
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
        System.out.println("\n============== University Management System ==============");
        System.out.println("Students   : 1.Add  2.Remove  3.Display all  4.Search by ID  5.Search by name");
        System.out.println("             6.Filter by program  7.Sort by name");
        System.out.println("Courses    : 8.Add  9.Remove  10.Display all  11.Search by code  12.Search by name");
        System.out.println("             13.Filter by credits  14.Courses of a department");
        System.out.println("Register   : 15.Register student  16.Withdraw student");
        System.out.println("             17.Courses of a student  18.Students of a course");
        System.out.println("Members    : 19.Add faculty  20.Display faculty  21.All profiles (polymorphism)");
        System.out.println("             22.Search members by ID/name  23.Remove any member");
        System.out.println("Instructors: 24.Assign/change instructor  25.Courses of a faculty member");
        System.out.println("             26.Courses without instructor");
        System.out.println("Feedback   : 27.Give feedback (faculty or course)  28.Display all feedback");
        System.out.println("Reports    : 29.General statistics  30.Enrollment statistics  31.Full demonstration");
        System.out.println("0. Exit");
        System.out.print("Select option: ");
    }

    private static void loadSampleData() {
        MANAGER.addStudent(new Student("S101", "Rahul Sharma", "BTech", "rahul.sharma@sau.ac.in"));
        MANAGER.addStudent(new Student("S102", "Priya Verma", "MTech", "priya.verma@sau.ac.in"));
        MANAGER.addStudent(new Student("S103", "Amit Kumar", "BTech", "amit.kumar@sau.ac.in"));
        MANAGER.addStudent(new Student("S104", "Sneha Gupta", "MCA", "sneha.gupta@sau.ac.in"));
        MANAGER.addStudent(new Student("S105", "Rohan Sharma", "BTech", "rohan.sharma@sau.ac.in"));

        MANAGER.addFaculty(new Faculty("F201", "Dr. Anil Mehta", "CSE", "Professor", "anil.mehta@sau.ac.in"));
        MANAGER.addFaculty(new Faculty("F202", "Dr. Kavita Rao", "CSE", "Associate Professor", "kavita.rao@sau.ac.in"));
        MANAGER.addFaculty(new Faculty("F203", "Dr. Suresh Iyer", "MATH", "Assistant Professor", "suresh.iyer@sau.ac.in"));

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

        System.out.println("Loading sample data...");
        MANAGER.assignInstructor("CS201", "F201");
        MANAGER.assignInstructor("CS301", "F201");
        MANAGER.assignInstructor("CS202", "F202");
        MANAGER.assignInstructor("MA101", "F203");
        

        MANAGER.findFacultyById("F201").addFeedback("S101", 5, "Explains OOP concepts very clearly.");
        MANAGER.findCourseByCode("CS201").addFeedback("S102", 4, "Good labs, a little fast-paced.");
    }

    
    
    

    private static void runDemonstration() {
        System.out.println("\n########## 1. All students ##########");
        MANAGER.displayAllStudents();

        System.out.println("\n########## 2. All faculty members ##########");
        MANAGER.displayAllFaculty();

        System.out.println("\n########## 3. Runtime polymorphism ##########");
        
        UniversityMember[] sampleMembers = {
            MANAGER.findMemberById("S101"),
            MANAGER.findMemberById("F201")
        };
        for (UniversityMember member : sampleMembers) {
            System.out.println("Reference type: UniversityMember, object type: "
                    + member.getClass().getSimpleName());
            member.displayProfile();
        }

        System.out.println("\n########## 4. Courses assigned to each faculty member ##########");
        for (Faculty faculty : MANAGER.getAllFaculty()) {
            System.out.println(faculty.getName() + " (" + faculty.getMemberId() + "):");
            printCourses(MANAGER.getCoursesOfFaculty(faculty.getMemberId()), "  No courses assigned.");
        }

        System.out.println("\n########## 5. Courses without an assigned instructor ##########");
        printCourses(MANAGER.getCoursesWithoutInstructor(), "Every course has an instructor.");

        System.out.println("\n########## 6. Feedback through the interface reference ##########");
        Feedback feedbackTarget = MANAGER.findFacultyById("F202"); 
        feedbackTarget.addFeedback("S103", 4, "Very helpful in lab sessions.");
        feedbackTarget = MANAGER.findCourseByCode("CS202");         
        feedbackTarget.addFeedback("S103", 5, "Well structured course.");
        for (Feedback target : MANAGER.getAllFeedbackTargets()) {
            System.out.printf("%-50s %s%n", target.getFeedbackTargetName(), target.getRatingSummary());
        }

        System.out.println("\n########## 7. Registration still works ##########");
        System.out.println("Registering S105 in CS202: "
                + (MANAGER.registerStudent("S105", "CS202") ? "success" : "failed"));
        System.out.println("Registering S105 in CS201 (already full): "
                + (MANAGER.registerStudent("S105", "CS201") ? "success" : "failed"));
        MANAGER.displayEnrollmentStatistics();
    }

    
    
    

    private static void addFaculty() {
        System.out.print("Faculty ID : ");
        String facultyId = readLine();
        System.out.print("Name       : ");
        String name = readLine();
        System.out.print("Department : ");
        String department = readLine();
        System.out.print("Designation: ");
        String designation = readLine();
        System.out.print("Email      : ");
        String email = readLine();
        if (facultyId.isEmpty() || name.isEmpty() || department.isEmpty()) {
            System.out.println("ID, name and department are required.");
            return;
        }
        Faculty faculty = new Faculty(facultyId, name, department, designation, email);
        if (MANAGER.addFaculty(faculty)) {
            System.out.println("Faculty member added:");
            faculty.displayProfile();
        }
    }

    private static void searchMembers() {
        System.out.print("Member ID (leave blank to skip): ");
        String memberId = readLine();
        System.out.print("Name      (leave blank to skip): ");
        String name = readLine();
        List<UniversityMember> matches = MANAGER.searchMembers(memberId, name);
        if (matches.isEmpty()) {
            System.out.println("No matching member found.");
        }
        for (UniversityMember member : matches) {
            member.displayProfile(); 
        }
    }

    private static void assignInstructor() {
        System.out.print("Course code: ");
        String courseCode = readLine();
        System.out.print("Faculty ID : ");
        MANAGER.assignInstructor(courseCode, readLine());
    }

    private static void displayCoursesOfFaculty() {
        System.out.print("Enter faculty ID: ");
        String facultyId = readLine();
        Faculty faculty = MANAGER.findFacultyById(facultyId);
        if (faculty == null) {
            System.out.println("Faculty member with ID \"" + facultyId + "\" not found.");
            return;
        }
        System.out.println("Courses taught by " + faculty.getName() + ":");
        printCourses(MANAGER.getCoursesOfFaculty(facultyId), "  No courses assigned.");
    }

    private static void giveFeedback() {
        System.out.print("Faculty ID or course code: ");
        String idOrCode = readLine();
        Feedback target = MANAGER.findFeedbackTarget(idOrCode); 
        if (target == null) {
            System.out.println("No faculty member or course found for \"" + idOrCode + "\".");
            return;
        }
        System.out.print("Your name or ID: ");
        String givenBy = readLine();
        System.out.print("Rating (" + Feedback.MIN_RATING + "-" + Feedback.MAX_RATING + "): ");
        int rating = readInt();
        if (rating < Feedback.MIN_RATING || rating > Feedback.MAX_RATING) {
            System.out.println("Feedback not saved: rating must be between " + Feedback.MIN_RATING
                    + " and " + Feedback.MAX_RATING + ".");
            return;
        }
        System.out.print("Comment: ");
        String comment = readLine();
        try {
            target.addFeedback(givenBy, rating, comment);
            System.out.println("Thank you! " + target.getFeedbackTargetName() + " now has "
                    + target.getRatingSummary() + ".");
        } catch (IllegalArgumentException e) {
            System.out.println("Feedback not saved: " + e.getMessage());
        }
    }

    private static void displayAllFeedback() {
        for (Feedback target : MANAGER.getAllFeedbackTargets()) {
            if (target.getFeedbackCount() > 0) {
                target.displayFeedback();
            }
        }
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
            Course course = MANAGER.findCourseByCode(courseCode);
            System.out.println("Registered successfully. Seats left in " + course.getCourseCode() + ": "
                    + (course.getMaxCapacity() - MANAGER.getEnrolledCount(courseCode)));
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
        System.out.println("Courses registered by " + student.getName() + ":");
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
