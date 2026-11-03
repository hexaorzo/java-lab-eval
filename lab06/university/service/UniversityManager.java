package lab06.university.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import lab06.university.model.Course;
import lab06.university.model.Student;












public class UniversityManager {

    private final Map<String, Student> students = new LinkedHashMap<>();
    private final Map<String, Course> courses = new LinkedHashMap<>();
    private final Map<String, Set<String>> coursesOfStudent = new LinkedHashMap<>();
    private final Map<String, Set<String>> studentsOfCourse = new LinkedHashMap<>();

    
    
    

    public boolean addStudent(Student student) {
        if (students.containsKey(student.getStudentId())) {
            System.out.println("A student with ID " + student.getStudentId() + " already exists.");
            return false;
        }
        students.put(student.getStudentId(), student);
        coursesOfStudent.put(student.getStudentId(), new TreeSet<>());
        return true;
    }

    
    public boolean removeStudent(String studentId) {
        String key = normaliseId(studentId);
        Student removed = students.remove(key);
        if (removed == null) {
            System.out.println("Student with ID \"" + studentId.trim() + "\" not found.");
            return false;
        }
        for (String courseCode : coursesOfStudent.remove(key)) {
            studentsOfCourse.get(courseCode).remove(key);
        }
        return true;
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.println("---------------- Students ----------------");
        for (Student student : students.values()) {
            student.displayStudent();
        }
    }

    public Student findStudentById(String studentId) {
        return students.get(normaliseId(studentId));
    }

    public List<Student> findStudentsByName(String name) {
        String searchText = name.trim().toLowerCase();
        List<Student> matches = new ArrayList<>();
        if (searchText.isEmpty()) {
            return matches;
        }
        for (Student student : students.values()) {
            if (student.getStudentName().toLowerCase().contains(searchText)) {
                matches.add(student);
            }
        }
        return matches;
    }

    public List<Student> filterStudentsByProgram(String program) {
        List<Student> matches = new ArrayList<>();
        for (Student student : students.values()) {
            if (student.getProgram().equalsIgnoreCase(program.trim())) {
                matches.add(student);
            }
        }
        return matches;
    }

    public void displayStudentsSortedByName() {
        List<Student> sorted = new ArrayList<>(students.values());
        sorted.sort(Comparator.comparing(Student::getStudentName, String.CASE_INSENSITIVE_ORDER));
        System.out.println("------- Students sorted by name -------");
        for (Student student : sorted) {
            student.displayStudent();
        }
    }

    
    
    

    public boolean addCourse(Course course) {
        if (courses.containsKey(course.getCourseCode())) {
            System.out.println("A course with code " + course.getCourseCode() + " already exists.");
            return false;
        }
        courses.put(course.getCourseCode(), course);
        studentsOfCourse.put(course.getCourseCode(), new TreeSet<>());
        return true;
    }

    
    public boolean removeCourse(String courseCode) {
        String key = normaliseCode(courseCode);
        Course removed = courses.remove(key);
        if (removed == null) {
            System.out.println("Course with code \"" + courseCode.trim() + "\" not found.");
            return false;
        }
        for (String studentId : studentsOfCourse.remove(key)) {
            coursesOfStudent.get(studentId).remove(key);
        }
        return true;
    }

    public void displayAllCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        System.out.println("---------------- Courses ----------------");
        for (Course course : courses.values()) {
            course.displayCourse();
        }
    }

    public Course findCourseByCode(String courseCode) {
        return courses.get(normaliseCode(courseCode));
    }

    public List<Course> findCoursesByName(String courseName) {
        String searchText = courseName.trim().toLowerCase();
        List<Course> matches = new ArrayList<>();
        if (searchText.isEmpty()) {
            return matches;
        }
        for (Course course : courses.values()) {
            if (course.getCourseName().toLowerCase().contains(searchText)) {
                matches.add(course);
            }
        }
        return matches;
    }

    
    public List<Course> filterCoursesByCredits(int minimumCredits, int maximumCredits) {
        List<Course> matches = new ArrayList<>();
        for (Course course : courses.values()) {
            if (course.getCredits() >= minimumCredits && course.getCredits() <= maximumCredits) {
                matches.add(course);
            }
        }
        return matches;
    }

    public void displayCoursesByDepartment(String prefix) {
        String searchPrefix = prefix.trim().toUpperCase();
        boolean found = false;
        for (Course course : courses.values()) {
            if (course.getCourseCode().startsWith(searchPrefix)) {
                course.displayCourse();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No courses found for department " + searchPrefix + ".");
        }
    }

    
    
    

    public boolean registerStudent(String studentId, String courseCode) {
        Student student = findStudentById(studentId);
        Course course = findCourseByCode(courseCode);
        if (student == null) {
            System.out.println("Student with ID \"" + studentId.trim() + "\" not found.");
            return false;
        }
        if (course == null) {
            System.out.println("Course with code \"" + courseCode.trim() + "\" not found.");
            return false;
        }
        Set<String> enrolledStudents = studentsOfCourse.get(course.getCourseCode());
        if (enrolledStudents.contains(student.getStudentId())) {
            System.out.println(student.getStudentName() + " is already registered for "
                    + course.getCourseCode() + ".");
            return false;
        }
        if (enrolledStudents.size() >= course.getMaxCapacity()) {
            System.out.println("Registration failed: " + course.getCourseCode() + " is full ("
                    + course.getMaxCapacity() + " seats).");
            return false;
        }
        enrolledStudents.add(student.getStudentId());
        coursesOfStudent.get(student.getStudentId()).add(course.getCourseCode());
        return true;
    }

    public boolean withdrawStudent(String studentId, String courseCode) {
        String id = normaliseId(studentId);
        String code = normaliseCode(courseCode);
        if (!students.containsKey(id) || !courses.containsKey(code)) {
            System.out.println("Student or course not found.");
            return false;
        }
        if (!studentsOfCourse.get(code).remove(id)) {
            System.out.println("Student " + id + " is not registered for " + code + ".");
            return false;
        }
        coursesOfStudent.get(id).remove(code);
        return true;
    }

    public List<Course> getCoursesOfStudent(String studentId) {
        List<Course> result = new ArrayList<>();
        Set<String> courseCodes = coursesOfStudent.get(normaliseId(studentId));
        if (courseCodes != null) {
            for (String courseCode : courseCodes) {
                result.add(courses.get(courseCode));
            }
        }
        return result;
    }

    public List<Student> getStudentsOfCourse(String courseCode) {
        List<Student> result = new ArrayList<>();
        Set<String> studentIds = studentsOfCourse.get(normaliseCode(courseCode));
        if (studentIds != null) {
            for (String studentId : studentIds) {
                result.add(students.get(studentId));
            }
        }
        return result;
    }

    public int getEnrolledCount(String courseCode) {
        Set<String> studentIds = studentsOfCourse.get(normaliseCode(courseCode));
        return studentIds == null ? 0 : studentIds.size();
    }

    
    
    

    public void displayStatistics() {
        System.out.println("---------------- Statistics ----------------");
        System.out.println("Total students : " + students.size());
        System.out.println("Total courses  : " + courses.size());

        Map<String, Integer> studentsPerProgram = new TreeMap<>();
        int invalidEmails = 0;
        for (Student student : students.values()) {
            studentsPerProgram.merge(student.getProgram(), 1, Integer::sum);
            if (!student.hasValidEmail()) {
                invalidEmails++;
            }
        }
        System.out.println("Students in each program:");
        for (Map.Entry<String, Integer> entry : studentsPerProgram.entrySet()) {
            System.out.printf("  %-6s : %d%n", entry.getKey(), entry.getValue());
        }

        int totalCredits = 0;
        for (Course course : courses.values()) {
            totalCredits += course.getCredits();
        }
        System.out.println("Total credits offered : " + totalCredits);
        if (!courses.isEmpty()) {
            System.out.printf("Average credits/course: %.2f%n", (double) totalCredits / courses.size());
        }
        System.out.println("Students with invalid email: " + invalidEmails);
    }

    public void displayEnrollmentStatistics() {
        System.out.println("----------- Enrollment Statistics -----------");
        System.out.printf("%-8s | %-35s | %8s | %8s | %s%n", "Code", "Course", "Enrolled", "Capacity", "Seats left");
        List<String> emptyCourses = new ArrayList<>();
        int totalRegistrations = 0;
        for (Course course : courses.values()) {
            int enrolled = getEnrolledCount(course.getCourseCode());
            totalRegistrations += enrolled;
            System.out.printf("%-8s | %-35s | %8d | %8d | %d%n", course.getCourseCode(), course.getCourseName(),
                    enrolled, course.getMaxCapacity(), course.getMaxCapacity() - enrolled);
            if (enrolled == 0) {
                emptyCourses.add(course.getCourseCode() + " (" + course.getCourseName() + ")");
            }
        }
        System.out.println("Total registrations: " + totalRegistrations);
        System.out.println("Courses with no registered students: "
                + (emptyCourses.isEmpty() ? "none" : String.join(", ", emptyCourses)));

        List<Student> notRegistered = new ArrayList<>();
        for (Student student : students.values()) {
            if (coursesOfStudent.get(student.getStudentId()).isEmpty()) {
                notRegistered.add(student);
            }
        }
        System.out.print("Students not registered in any course: ");
        if (notRegistered.isEmpty()) {
            System.out.println("none");
        } else {
            List<String> names = new ArrayList<>();
            for (Student student : notRegistered) {
                names.add(student.getStudentName());
            }
            Collections.sort(names);
            System.out.println(String.join(", ", names));
        }
    }

    private static String normaliseId(String studentId) {
        return studentId.trim().toUpperCase();
    }

    private static String normaliseCode(String courseCode) {
        return courseCode.trim().toUpperCase().replace(" ", "");
    }
}
