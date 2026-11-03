package lab05.library.app;

import java.util.Scanner;

import lab05.library.model.Book;
import lab05.library.model.EBook;
import lab05.library.model.PrintedBook;
import lab05.library.service.BookCatalog;

public class LibraryApp {

    private static final BookCatalog CATALOG = new BookCatalog();
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        loadBooks();

        boolean running = true;
        while (running) {
            System.out.println("\n============ Library Menu ============");
            System.out.println(" 1. Display all books (runtime polymorphism)");
            System.out.println(" 2. List books by author");
            System.out.println(" 3. List books by category");
            System.out.println(" 4. searchBook(keyword)");
            System.out.println(" 5. searchBook(maximum price)");
            System.out.println(" 6. searchBook(author, category)");
            System.out.println(" 7. Show book list (StringBuilder)");
            System.out.println(" 8. Demonstrate String immutability");
            System.out.println(" 9. Add a review to a book");
            System.out.println("10. Display reviews of a book");
            System.out.println(" 0. Exit");
            System.out.print("Select option: ");

            switch (readLine()) {
                case "1":
                    CATALOG.displayAllBooks();
                    break;
                case "2":
                    System.out.print("Enter author name: ");
                    printTitles(CATALOG.findBooksByAuthor(readLine()));
                    break;
                case "3":
                    System.out.print("Enter category: ");
                    printTitles(CATALOG.findBooksByCategory(readLine()));
                    break;
                case "4":
                    System.out.print("Enter keyword: ");
                    printTitles(CATALOG.searchBook(readLine()));
                    break;
                case "5":
                    System.out.print("Enter maximum price: ");
                    try {
                        printTitles(CATALOG.searchBook(Double.parseDouble(readLine())));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid price.");
                    }
                    break;
                case "6":
                    System.out.print("Enter author name: ");
                    String authorName = readLine();
                    System.out.print("Enter category: ");
                    printTitles(CATALOG.searchBook(authorName, readLine()));
                    break;
                case "7":
                    System.out.println(CATALOG.buildBookList());
                    break;
                case "8":
                    demonstrateImmutability(CATALOG.getBook(0));
                    break;
                case "9":
                    addReview();
                    break;
                case "10":
                    Book book = chooseBook();
                    if (book != null) {
                        book.displayReviews();
                    }
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

    private static void loadBooks() {
        Book[] initialBooks = {
            new PrintedBook("The Guide", "R. K. Narayan", "978-0143039648", 299.0, "Fiction", 220),
            new EBook("Malgudi Days", "R. K. Narayan", "978-0143039655", 199.0, "Fiction", 2.4, "EPUB"),
            new PrintedBook("Wings of Fire", "A. P. J. Abdul Kalam", "978-8173711466", 350.0, "Autobiography", 196),
            new EBook("Ignited Minds", "A. P. J. Abdul Kalam", "978-0143029823", 175.0, "Non-Fiction", 1.8, "PDF"),
            new PrintedBook("The Story of My Experiments", "M. K. Gandhi", "978-8172345662", 150.0, "Autobiography", 480),
            new PrintedBook("The God of Small Things", "Arundhati Roy", "978-0006550686", 399.0, "Fiction", 340),
            new EBook("Head First Java", "Kathy Sierra", "978-0596009205", 450.0, "Programming", 35.6, "PDF"),
            new PrintedBook("Effective Java", "Joshua Bloch", "978-0134685991", 899.0, "Programming", 412)
        };
        for (Book book : initialBooks) {
            CATALOG.addBook(book);
        }

        Book firstBook = CATALOG.getBook(0);
        firstBook.addReview("Anita", 5, "A timeless classic.");
        Book.Review review = firstBook.new Review("Vikram", 4, "Simple yet deep.");
        firstBook.addReview(review);
        CATALOG.getBook(6).addReview("Neha", 5, "Best book to start Java.");
    }

    private static void printTitles(Book[] books) {
        if (books.length == 0) {
            System.out.println("No books found.");
            return;
        }
        for (Book book : books) {
            System.out.printf("  %-30s | %-22s | %-13s | Rs.%.2f | %s%n", book.getTitle(),
                    book.getAuthorName(), book.getCategory(), book.getPrice(), book.getBookType());
        }
    }

    private static Book chooseBook() {
        CATALOG.displayTitles();
        System.out.print("Choose book number: ");
        try {
            Book book = CATALOG.getBook(Integer.parseInt(readLine()) - 1);
            if (book == null) {
                System.out.println("No such book.");
            }
            return book;
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
            return null;
        }
    }

    private static void addReview() {
        Book book = chooseBook();
        if (book == null) {
            return;
        }
        System.out.print("Reviewer name: ");
        String reviewerName = readLine();
        System.out.print("Rating (1-5) : ");
        int rating;
        try {
            rating = Integer.parseInt(readLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid rating.");
            return;
        }
        System.out.print("Comment      : ");
        String comment = readLine();

        Book.Review review = book.new Review(reviewerName, rating, comment);
        if (book.addReview(review)) {
            System.out.println("Review added. The review knows its book: \""
                    + review.getReviewedBook().getTitle() + "\"");
            review.displayReview();
        }
    }

    private static void demonstrateImmutability(Book book) {
        String originalTitle = book.getTitle();
        String upperCaseTitle = originalTitle.toUpperCase();
        String extendedTitle = originalTitle.concat(" (Special Edition)");

        System.out.println("Original title after operations : " + originalTitle);
        System.out.println("toUpperCase() returned          : " + upperCaseTitle);
        System.out.println("concat() returned               : " + extendedTitle);
        System.out.println("Title stored in Book object     : " + book.getTitle());
        System.out.println("=> The original String is unchanged; every operation created a new String.");
    }

    private static String readLine() {
        if (!SCANNER.hasNextLine()) {
            System.out.println("\nInput ended.");
            System.exit(0);
        }
        return SCANNER.nextLine().trim();
    }
}
