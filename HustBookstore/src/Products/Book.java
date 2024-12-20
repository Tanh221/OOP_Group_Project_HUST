package Products;

public class Book extends Product {
	
    private static final long serialVersionUID = 1L;
	private String author;
    private int numberOfPages;

    public Book(String name, double price, String description, String author, int numberOfPages) {
        super(name, price, description);
        this.author = author != null ? author : "";
        this.numberOfPages = Math.max(0, numberOfPages);
    }

    @Override
    public String getDetails() {
        return "Book [" + this.getProductID() + "] :\n" + 
                "Name: " + this.getName() + '\n' + 
                "Price: " + this.getPrice() + '\n' + 
                "Description: " + this.getDescription() + '\n' + 
                "Author: " + this.getAuthor() + '\n' + 
                "Number Of Pages: " + this.getNumberOfPages()
                ;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
    @Override
    public boolean equals(Object otherobj) {
        if (this == otherobj) return true; // the same reference
        if (otherobj == null || getClass() != otherobj.getClass()) return false; // null or different class
        Book otherbook = (Book) otherobj; //
        return this.getProductID() == otherbook.getProductID() && 
				this.getName().equals(otherbook.getName()) && 
				this.getPrice() == otherbook.getPrice() && 
				this.getDescription().equals(otherbook.getDescription()) && 
                this.getAuthor().equals(otherbook.getAuthor()) && 
                this.getNumberOfPages() == otherbook.getNumberOfPages()
                ;
    }
}