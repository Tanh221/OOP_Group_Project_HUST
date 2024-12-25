package model.Products;

import model.Databases.ProductDB;

public class Book extends Product {
	
    private static final long serialVersionUID = 1L;
	private String author;
    private int numberOfPages;
    private String type = "Book";

    public Book(String name, double price, String description, String author, int numberOfPages) throws Exception {
        super(name, price, description);
        this.author = author != null ? author : "";
        this.numberOfPages = Math.max(0, numberOfPages);
        ProductDB productdb = new ProductDB();
        productdb.update(this);
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
        return this.author;
    }

    public void setAuthor(String author) throws Exception {
        this.author = author;
        ProductDB productdb = new ProductDB();
        productdb.update(this);
    }

    public int getNumberOfPages() {
        return this.numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) throws Exception {
        this.numberOfPages = numberOfPages;
        ProductDB productdb = new ProductDB();
        productdb.update(this);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // @Override
    // public boolean equals(Object otherobj) {
    //     if (this == otherobj) return true; // the same reference
    //     if (otherobj == null || getClass() != otherobj.getClass()) return false; // null or different class
    //     Book otherbook = (Book) otherobj; //
    //     return this.getProductID() == otherbook.getProductID() && 
	// 			this.getName().equals(otherbook.getName()) && 
	// 			this.getPrice() == otherbook.getPrice() && 
	// 			this.getDescription().equals(otherbook.getDescription()) && 
    //             this.getAuthor().equals(otherbook.getAuthor()) && 
    //             this.getNumberOfPages() == otherbook.getNumberOfPages()
    //             ;
    // }
}