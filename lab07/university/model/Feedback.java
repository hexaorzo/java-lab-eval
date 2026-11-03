package lab07.university.model;





public interface Feedback {

    int MIN_RATING = 1;
    int MAX_RATING = 5;

    void addFeedback(String givenBy, int rating, String comment);

    double getAverageRating();

    int getFeedbackCount();

    void displayFeedback();

    
    String getFeedbackTargetName();

    
    default String getRatingSummary() {
        if (getFeedbackCount() == 0) {
            return "No feedback yet";
        }
        return String.format("%.1f/%d from %d feedback", getAverageRating(), MAX_RATING, getFeedbackCount());
    }

    
    static int validateRating(int rating) {
        if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new IllegalArgumentException("Rating must be between " + MIN_RATING + " and " + MAX_RATING);
        }
        return rating;
    }
}
