package lab05.library.service;

import lab05.library.model.Book;

public class BookCatalog {

    private static final int MAX_BOOKS = 50;

    private final Book[] books = new Book[MAX_BOOKS];
    private int bookCount;

    public boolean addBook(Book book) {
        if (bookCount == MAX_BOOKS) {
            System.out.println("Catalog is full.");
            return false;
        }
        books[bookCount++] = book;
        return true;
    }

    public int getBookCount() {
        return bookCount;
    }

    public Book getBook(int index) {
        if (index < 0 || index >= bookCount) {
            return null;
        }
        return books[index];
    }

    public void displayAllBooks() {
        for (int i = 0; i < bookCount; i++) {
            System.out.print((i + 1) + ". ");
            books[i].displayDetails();
        }
    }

    public void displayTitles() {
        for (int i = 0; i < bookCount; i++) {
            System.out.println("  " + (i + 1) + ". " + books[i].getTitle() + " [" + books[i].getBookType() + "]");
        }
    }

    public Book[] searchBook(String keyword) {
        String searchText = keyword.trim().toLowerCase();
        Book[] matches = new Book[bookCount];
        int matchCount = 0;
        if (searchText.isEmpty()) {
            return new Book[0];
        }
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getTitle().toLowerCase().contains(searchText)
                    || books[i].getAuthorName().toLowerCase().contains(searchText)) {
                matches[matchCount++] = books[i];
            }
        }
        return copyOf(matches, matchCount);
    }

    public Book[] searchBook(double maximumPrice) {
        Book[] matches = new Book[bookCount];
        int matchCount = 0;
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getPrice() <= maximumPrice) {
                matches[matchCount++] = books[i];
            }
        }
        return copyOf(matches, matchCount);
    }

    public Book[] searchBook(String authorName, String category) {
        Book[] matches = new Book[bookCount];
        int matchCount = 0;
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getAuthorName().equalsIgnoreCase(authorName.trim())
                    && books[i].getCategory().equalsIgnoreCase(category.trim())) {
                matches[matchCount++] = books[i];
            }
        }
        return copyOf(matches, matchCount);
    }

    public Book[] findBooksByAuthor(String authorName) {
        Book[] matches = new Book[bookCount];
        int matchCount = 0;
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getAuthorName().equalsIgnoreCase(authorName.trim())) {
                matches[matchCount++] = books[i];
            }
        }
        return copyOf(matches, matchCount);
    }

    public Book[] findBooksByCategory(String category) {
        Book[] matches = new Book[bookCount];
        int matchCount = 0;
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getCategory().equalsIgnoreCase(category.trim())) {
                matches[matchCount++] = books[i];
            }
        }
        return copyOf(matches, matchCount);
    }

    public String buildBookList() {
        StringBuilder bookList = new StringBuilder("----- Book List -----\n");
        for (int i = 0; i < bookCount; i++) {
            bookList.append(i + 1).append(". ")
                    .append(books[i].getTitle()).append(" by ")
                    .append(books[i].getAuthorName()).append('\n');
        }
        bookList.append("Total books: ").append(bookCount);
        return bookList.toString();
    }

    private static Book[] copyOf(Book[] source, int size) {
        Book[] result = new Book[size];
        System.arraycopy(source, 0, result, 0, size);
        return result;
    }
}
