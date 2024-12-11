package bookstore;

public class Book extends Product {
	
	private static final long serialVersionUID = 6561481160391173190L;
	private String author;
    private String publisher;
    private String publishedDate;
    private String language;
    private String genre;
    private String format;
    private String series;
    private int numberOfPages;

    public Book(String name, String category, double price, int quantity, String description, boolean isActive, String manufacturer, double weight, String features, String warranty,
                String author, String publisher, String publishedDate, String language, String genre, String format, String series, int numberOfPages) {
        super(name, category, price, quantity, description, isActive, manufacturer, weight, features, warranty);
        this.author = author != null ? author : "";
        this.publisher = publisher != null ? publisher : "";
        this.publishedDate = publishedDate != null ? publishedDate : "";
        this.language = language != null ? language : "";
        this.genre = genre != null ? genre : "";
        this.format = format != null ? format : "";
        this.series = series != null ? series : "";
        this.numberOfPages = Math.max(0, numberOfPages);
    }

    public void updateBookDetails(String author, String publisher, String publishedDate, String language, String genre, String format, String series, int numberOfPages) {
        if (!author.equals(this.author)) {
            this.author = author;
        }
        if (!publisher.equals(this.publisher)) {
            this.publisher = publisher;
        }
        if (!publishedDate.equals(this.publishedDate)) {
            this.publishedDate = publishedDate;
        }
        if (!language.equals(this.language)) {
            this.language = language;
        }
        if (!genre.equals(this.genre)) {
            this.genre = genre;
        }
        if (!format.equals(this.format)) {
            this.format = format;
        }
        if (!series.equals(this.series)) {
            this.series = series;
        }
        if (numberOfPages != this.numberOfPages) {
            this.numberOfPages = numberOfPages;
        }
    }

    @Override
    public String getDetails() {
        return getProductID() + ". Book: " + getName() + ", Author: " + getAuthor() + ", Publisher: " + getPublisher();
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getLanguage() {
        return language;
    }

    public String getGenre() {
        return genre;
    }

    public String getFormat() {
        return format;
    }

    public String getSeries() {
        return series;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }
}