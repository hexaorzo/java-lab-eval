package lab07.university.model;




public class FeedbackEntry {

    private final String givenBy;
    private final int rating;
    private final String comment;

    public FeedbackEntry(String givenBy, int rating, String comment) {
        this.givenBy = givenBy.trim();
        this.rating = Feedback.validateRating(rating);
        this.comment = comment.trim();
    }

    public String getGivenBy() {
        return givenBy;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public void displayEntry() {
        System.out.println("    " + "*".repeat(rating) + " (" + rating + "/5) by " + givenBy + ": " + comment);
    }
}
