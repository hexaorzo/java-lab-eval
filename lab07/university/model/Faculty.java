package lab07.university.model;

import java.util.ArrayList;
import java.util.List;





public class Faculty extends UniversityMember implements Feedback {

    private String department;
    private String designation;
    private final List<FeedbackEntry> feedbackEntries = new ArrayList<>();

    public Faculty(String facultyId, String name, String department, String designation, String email) {
        super(facultyId, name, email);
        this.department = department.trim().toUpperCase();
        this.designation = designation.trim();
    }

    public String getFacultyId() {
        return getMemberId();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department.trim().toUpperCase();
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation.trim();
    }

    @Override
    public String getRole() {
        return "Faculty";
    }

    @Override
    public void displayProfile() {
        displayCommonProfile();
        System.out.println("| Dept.  : " + department);
        System.out.println("| Title  : " + designation);
        System.out.println("| Rating : " + getRatingSummary());
        System.out.println("+--------------------------------------------------");
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
        return "Faculty " + getName() + " (" + getMemberId() + ")";
    }
}
