package lab07.university.model;

import java.util.ArrayList;
import java.util.List;





public class Course implements Feedback {

    private final String courseCode;
    private String courseName;
    private int credits;
    private int maxCapacity;
    private Faculty instructor; 
    private final List<FeedbackEntry> feedbackEntries = new ArrayList<>();

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

    public Faculty getInstructor() {
        return instructor;
    }

    
    public void setInstructor(Faculty instructor) {
        this.instructor = instructor;
    }

    public boolean hasInstructor() {
        return instructor != null;
    }

    public String getDepartmentPrefix() {
        int index = 0;
        while (index < courseCode.length() && Character.isLetter(courseCode.charAt(index))) {
            index++;
        }
        return courseCode.substring(0, index);
    }

    public void displayCourse() {
        String instructorName = hasInstructor() ? instructor.getName() : "-- not assigned --";
        System.out.printf("%-8s | %-30s | %d cr | cap %2d | %s%n", courseCode, courseName, credits, maxCapacity,
                instructorName);
    }

    

    @Override
    public void addFeedback(String givenBy, int rating, String comment) {
        feedbackEntries.add(new FeedbackEntry(givenBy, rating, comment));
    }

    @Override
    public double getAverageRating() {
        if (feedbackEntries.isEmpty()) {
            return 0;
        }
        int total = 0;
        for (FeedbackEntry entry : feedbackEntries) {
            total += entry.getRating();
        }
        return (double) total / feedbackEntries.size();
    }

    @Override
    public int getFeedbackCount() {
        return feedbackEntries.size();
    }

    @Override
    public void displayFeedback() {
        System.out.println("Feedback for " + getFeedbackTargetName() + " - " + getRatingSummary());
        for (FeedbackEntry entry : feedbackEntries) {
            entry.displayEntry();
        }
    }

    @Override
    public String getFeedbackTargetName() {
        return "Course " + courseCode + " (" + courseName + ")";
    }
}
