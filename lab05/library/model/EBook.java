package lab05.library.model;

public class EBook extends Book {

    private double fileSizeInMb;
    private String fileFormat;

    public EBook(String title, String authorName, String isbn, double price, String category,
            double fileSizeInMb, String fileFormat) {
        super(title, authorName, isbn, price, category);
        this.fileSizeInMb = fileSizeInMb;
        this.fileFormat = fileFormat;
    }

    public double getFileSizeInMb() {
        return fileSizeInMb;
    }

    public void setFileSizeInMb(double fileSizeInMb) {
        this.fileSizeInMb = fileSizeInMb;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    @Override
    public String getBookType() {
        return "E-Book";
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.printf("    File     : %.1f MB, %s format%n", fileSizeInMb, fileFormat);
    }
}
