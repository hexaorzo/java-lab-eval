package lab06.university.model;





public class Course {

    private String courseCode;
    private String courseName;
    private int credits;
    private int maxCapacity;

    public Course(String courseCode, String courseName, int credits, int maxCapacity) {
        this.courseCode = courseCode.trim().toUpperCase().replace(" ", "");
        this.courseName = courseName.trim();
        this.credits = credits;
        this.maxCapacity = maxCapacity;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName.trim();
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    
    public String getDepartmentPrefix() {
        int index = 0;
        while (index < courseCode.length() && Character.isLetter(courseCode.charAt(index))) {
            index++;
        }
        return courseCode.substring(0, index);
    }

    public void displayCourse() {
        System.out.printf("%-8s | %-35s | %d credits | capacity %d%n", courseCode, courseName, credits,
                maxCapacity);
    }
}
