package lab05.library.model;


public class PrintedBook extends Book {

    private int numberOfPages;

    public PrintedBook(String title, String authorName, String isbn, double price, String category,
            int numberOfPages) {
        super(title, authorName, isbn, price, category);
        this.numberOfPages = numberOfPages;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    @Override
    public String getBookType() {
        return "Printed Book";
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("    Pages    : " + numberOfPages);
    }
}
