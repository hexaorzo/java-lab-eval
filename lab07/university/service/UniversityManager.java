package lab07.university.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import lab07.university.model.Course;
import lab07.university.model.Faculty;
import lab07.university.model.Feedback;
import lab07.university.model.Student;
import lab07.university.model.UniversityMember;











public class UniversityManager {

    private final Map<String, UniversityMember> members = new LinkedHashMap<>();
    private final Map<String, Course> courses = new LinkedHashMap<>();
    private final Map<String, Set<String>> coursesOfStudent = new LinkedHashMap<>();
    private final Map<String, Set<String>> studentsOfCourse = new LinkedHashMap<>();

    
    
    

    
    public boolean addMember(UniversityMember member) {
        if (members.containsKey(member.getMemberId())) {
            System.out.println("A member with ID " + member.getMemberId() + " already exists.");
            return false;
        }
        members.put(member.getMemberId(), member);
        if (member instanceof Student) {
            coursesOfStudent.put(member.getMemberId(), new TreeSet<>());
        }
        return true;
    }

    public UniversityMember findMemberById(String memberId) {
        return members.get(normaliseId(memberId));
    }

    
    public List<UniversityMember> searchMembersByName(String name) {
        List<UniversityMember> matches = new ArrayList<>();
        for (UniversityMember member : members.values()) {
            if (member.matchesName(name)) {
                matches.add(member);
            }
        }
        return matches;
    }

    
    public List<UniversityMember> searchMembers(String memberId, String name) {
        List<UniversityMember> matches = new ArrayList<>();
        String idText = memberId.trim();
        String nameText = name.trim();
        for (UniversityMember member : members.values()) {
            boolean idMatches = idText.isEmpty() || member.getMemberId().equalsIgnoreCase(idText);
            boolean nameMatches = nameText.isEmpty() || member.matchesName(nameText);
            if (idMatches && nameMatches && !(idText.isEmpty() && nameText.isEmpty())) {
                matches.add(member);
            }
        }
        return matches;
    }

    
    public void displayAllProfiles() {
        for (UniversityMember member : members.values()) {
            member.displayProfile();
        }
    }

    public void displayAllMembers() {
        System.out.println("-------------- All Members --------------");
        for (UniversityMember member : members.values()) {
            member.displaySummary();
        }
    }

    public boolean removeMember(String memberId) {
        String key = normaliseId(memberId);
        UniversityMember removed = members.remove(key);
        if (removed == null) {
            System.out.println("Member with ID \"" + memberId.trim() + "\" not found.");
            return false;
        }
        if (removed instanceof Student) {
            for (String courseCode : coursesOfStudent.remove(key)) {
                studentsOfCourse.get(courseCode).remove(key);
            }
        } else if (removed instanceof Faculty) {
            for (Course course : getCoursesOfFaculty(key)) {
                course.setInstructor(null);
                System.out.println("  " + course.getCourseCode() + " no longer has an instructor.");
            }
        }
        return true;
    }

    
    
    

    public boolean addStudent(Student student) {
        return addMember(student);
    }

    public boolean removeStudent(String studentId) {
        if (findStudentById(studentId) == null) {
            System.out.println("Student with ID \"" + studentId.trim() + "\" not found.");
            return false;
        }
        return removeMember(studentId);
    }

    public Student findStudentById(String studentId) {
        UniversityMember member = findMemberById(studentId);
        return member instanceof Student ? (Student) member : null;
    }

    public List<Student> getAllStudents() {
        List<Student> result = new ArrayList<>();
        for (UniversityMember member : members.values()) {
            if (member instanceof Student) {
                result.add((Student) member);
            }
        }
        return result;
    }

    public void displayAllStudents() {
        List<Student> students = getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.println("---------------- Students ----------------");
        for (Student student : students) {
            student.displayStudent();
        }
    }

    public List<Student> findStudentsByName(String name) {
        List<Student> matches = new ArrayList<>();
        for (Student student : getAllStudents()) {
            if (student.matchesName(name)) {
                matches.add(student);
            }
        }
        return matches;
    }

    public List<Student> filterStudentsByProgram(String program) {
        List<Student> matches = new ArrayList<>();
        for (Student student : getAllStudents()) {
            if (student.getProgram().equalsIgnoreCase(program.trim())) {
                matches.add(student);
            }
        }
        return matches;
    }

    public void displayStudentsSortedByName() {
        List<Student> sorted = getAllStudents();
        sorted.sort(Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER));
        System.out.println("------- Students sorted by name -------");
        for (Student student : sorted) {
            student.displayStudent();
        }
    }

    
    
    

    public boolean addFaculty(Faculty faculty) {
        return addMember(faculty);
    }

    public Faculty findFacultyById(String facultyId) {
        UniversityMember member = findMemberById(facultyId);
        return member instanceof Faculty ? (Faculty) member : null;
    }

    public List<Faculty> getAllFaculty() {
        List<Faculty> result = new ArrayList<>();
        for (UniversityMember member : members.values()) {
            if (member instanceof Faculty) {
                result.add((Faculty) member);
            }
        }
        return result;
    }

    public void displayAllFaculty() {
        List<Faculty> facultyMembers = getAllFaculty();
        if (facultyMembers.isEmpty()) {
            System.out.println("No faculty members found.");
            return;
        }
        System.out.println("------------- Faculty Members -------------");
        for (Faculty faculty : facultyMembers) {
            System.out.printf("%-8s | %-20s | %-5s | %-20s | %s%n", faculty.getMemberId(), faculty.getName(),
                    faculty.getDepartment(), faculty.getDesignation(), faculty.getRatingSummary());
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

    
    
    

    
    public boolean assignInstructor(String courseCode, String facultyId) {
        Course course = findCourseByCode(courseCode);
        Faculty faculty = findFacultyById(facultyId);
        if (course == null) {
            System.out.println("Course with code \"" + courseCode.trim() + "\" not found.");
            return false;
        }
        if (faculty == null) {
            System.out.println("Faculty member with ID \"" + facultyId.trim() + "\" not found.");
            return false;
        }
        Faculty previous = course.getInstructor();
        if (previous == faculty) {
            System.out.println(faculty.getName() + " already teaches " + course.getCourseCode() + ".");
            return false;
        }
        course.setInstructor(faculty);
        if (previous == null) {
            System.out.println(faculty.getName() + " assigned to " + course.getCourseCode() + ".");
        } else {
            System.out.println("Instructor of " + course.getCourseCode() + " changed from "
                    + previous.getName() + " to " + faculty.getName() + ".");
        }
        return true;
    }

    public boolean unassignInstructor(String courseCode) {
        Course course = findCourseByCode(courseCode);
        if (course == null || !course.hasInstructor()) {
            System.out.println("Course not found or it has no instructor.");
            return false;
        }
        course.setInstructor(null);
        return true;
    }

    public List<Course> getCoursesOfFaculty(String facultyId) {
        String key = normaliseId(facultyId);
        List<Course> result = new ArrayList<>();
        for (Course course : courses.values()) {
            if (course.hasInstructor() && course.getInstructor().getMemberId().equals(key)) {
                result.add(course);
            }
        }
        return result;
    }

    public List<Course> getCoursesWithoutInstructor() {
        List<Course> result = new ArrayList<>();
        for (Course course : courses.values()) {
            if (!course.hasInstructor()) {
                result.add(course);
            }
        }
        return result;
    }

    
    
    

    



    public Feedback findFeedbackTarget(String idOrCode) {
        Faculty faculty = findFacultyById(idOrCode);
        if (faculty != null) {
            return faculty;
        }
        return findCourseByCode(idOrCode);
    }

    public List<Feedback> getAllFeedbackTargets() {
        List<Feedback> targets = new ArrayList<>(getAllFaculty());
        targets.addAll(courses.values());
        return targets;
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
        if (enrolledStudents.contains(student.getMemberId())) {
            System.out.println(student.getName() + " is already registered for " + course.getCourseCode() + ".");
            return false;
        }
        if (enrolledStudents.size() >= course.getMaxCapacity()) {
            System.out.println("Registration failed: " + course.getCourseCode() + " is full ("
                    + course.getMaxCapacity() + " seats).");
            return false;
        }
        enrolledStudents.add(student.getMemberId());
        coursesOfStudent.get(student.getMemberId()).add(course.getCourseCode());
        return true;
    }

    public boolean withdrawStudent(String studentId, String courseCode) {
        String id = normaliseId(studentId);
        String code = normaliseCode(courseCode);
        if (findStudentById(id) == null || !courses.containsKey(code)) {
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
                result.add((Student) members.get(studentId));
            }
        }
        return result;
    }

    public int getEnrolledCount(String courseCode) {
        Set<String> studentIds = studentsOfCourse.get(normaliseCode(courseCode));
        return studentIds == null ? 0 : studentIds.size();
    }

    
    
    

    public void displayStatistics() {
        List<Student> students = getAllStudents();
        List<Faculty> facultyMembers = getAllFaculty();
        System.out.println("---------------- Statistics ----------------");
        System.out.println("Total members  : " + members.size());
        System.out.println("Total students : " + students.size());
        System.out.println("Total faculty  : " + facultyMembers.size());
        System.out.println("Total courses  : " + courses.size());

        Map<String, Integer> studentsPerProgram = new TreeMap<>();
        int invalidEmails = 0;
        for (Student student : students) {
            studentsPerProgram.merge(student.getProgram(), 1, Integer::sum);
        }
        for (UniversityMember member : members.values()) {
            if (!member.hasValidEmail()) {
                invalidEmails++;
            }
        }
        System.out.println("Students in each program:");
        for (Map.Entry<String, Integer> entry : studentsPerProgram.entrySet()) {
            System.out.printf("  %-6s : %d%n", entry.getKey(), entry.getValue());
        }

        Map<String, Integer> facultyPerDepartment = new TreeMap<>();
        for (Faculty faculty : facultyMembers) {
            facultyPerDepartment.merge(faculty.getDepartment(), 1, Integer::sum);
        }
        System.out.println("Faculty in each department:");
        for (Map.Entry<String, Integer> entry : facultyPerDepartment.entrySet()) {
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
        System.out.println("Courses without instructor: " + getCoursesWithoutInstructor().size());
        System.out.println("Members with invalid email: " + invalidEmails);
    }

    public void displayEnrollmentStatistics() {
        System.out.println("----------- Enrollment Statistics -----------");
        System.out.printf("%-8s | %-30s | %8s | %8s | %s%n", "Code", "Course", "Enrolled", "Capacity", "Seats left");
        List<String> emptyCourses = new ArrayList<>();
        int totalRegistrations = 0;
        for (Course course : courses.values()) {
            int enrolled = getEnrolledCount(course.getCourseCode());
            totalRegistrations += enrolled;
            System.out.printf("%-8s | %-30s | %8d | %8d | %d%n", course.getCourseCode(), course.getCourseName(),
                    enrolled, course.getMaxCapacity(), course.getMaxCapacity() - enrolled);
            if (enrolled == 0) {
                emptyCourses.add(course.getCourseCode() + " (" + course.getCourseName() + ")");
            }
        }
        System.out.println("Total registrations: " + totalRegistrations);
        System.out.println("Courses with no registered students: "
                + (emptyCourses.isEmpty() ? "none" : String.join(", ", emptyCourses)));

        List<String> notRegistered = new ArrayList<>();
        for (Student student : getAllStudents()) {
            if (coursesOfStudent.get(student.getMemberId()).isEmpty()) {
                notRegistered.add(student.getName());
            }
        }
        Collections.sort(notRegistered);
        System.out.println("Students not registered in any course: "
                + (notRegistered.isEmpty() ? "none" : String.join(", ", notRegistered)));
    }

    private static String normaliseId(String memberId) {
        return memberId.trim().toUpperCase();
    }

    private static String normaliseCode(String courseCode) {
        return courseCode.trim().toUpperCase().replace(" ", "");
    }
}
