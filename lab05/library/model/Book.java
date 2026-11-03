package lab05.library.model;

public class Book {

    private static final int MAX_REVIEWS = 20;

    private String title;
    private String authorName;
    private String isbn;
    private double price;
    private String category;
    private final Review[] reviews = new Review[MAX_REVIEWS];
    private int reviewCount;

    public Book(String title, String authorName, String isbn, double price, String category) {
        this.title = title;
        this.authorName = authorName;
        this.isbn = isbn;
        this.price = price;
        this.category = category;
    }


    public class Review {

        private String reviewerName;
        private int rating; 
        private String comment;

        public Review(String reviewerName, int rating, String comment) {
            this.reviewerName = reviewerName;
            this.rating = Math.max(1, Math.min(5, rating));
            this.comment = comment;
        }

        public String getReviewerName() {
            return reviewerName;
        }

        public int getRating() {
            return rating;
        }

        public String getComment() {
            return comment;
        }

        public Book getReviewedBook() {
            return Book.this;
        }

        public void displayReview() {
            System.out.println("  Review of \"" + title + "\" by " + authorName
                    + " | " + reviewerName + " rated " + "*".repeat(rating) + " (" + rating + "/5)"
                    + " - " + comment);
        }
    }


    public boolean addReview(String reviewerName, int rating, String comment) {
        return addReview(new Review(reviewerName, rating, comment));
    }

    public boolean addReview(Review review) {
        if (review.getReviewedBook() != this) {
            System.out.println("This review belongs to a different book.");
            return false;
        }
        if (reviewCount == MAX_REVIEWS) {
            System.out.println("No more reviews can be added to this book.");
            return false;
        }
        reviews[reviewCount++] = review;
        return true;
    }

    public void displayReviews() {
        if (reviewCount == 0) {
            System.out.println("  No reviews yet for \"" + title + "\".");
            return;
        }
        for (int i = 0; i < reviewCount; i++) {
            reviews[i].displayReview();
        }
        System.out.printf("  Average rating: %.1f/5 from %d review(s)%n", getAverageRating(), reviewCount);
    }

    public double getAverageRating() {
        if (reviewCount == 0) {
            return 0;
        }
        int total = 0;
        for (int i = 0; i < reviewCount; i++) {
            total += reviews[i].getRating();
        }
        return (double) total / reviewCount;
    }

    public int getReviewCount() {
        return reviewCount;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBookType() {
        return "Book";
    }

    public void displayDetails() {
        System.out.println("[" + getBookType() + "] " + title);
        System.out.println("    Author   : " + authorName);
        System.out.println("    ISBN     : " + isbn);
        System.out.printf("    Price    : Rs.%.2f%n", price);
        System.out.println("    Category : " + category);
    }
}
