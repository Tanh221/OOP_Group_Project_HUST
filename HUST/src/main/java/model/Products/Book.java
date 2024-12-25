package model.Products;

import model.Databases.ProductDB;

public class Book extends Product {
	
    private static final long serialVersionUID = 1L;
    private String publisher;
	private String author;
    private int ISBN;
    private String type = "Book";

    public Book(String name, double price, String description,String publisher, String author, int ISBN) throws Exception {
        super(name, price, description);
        this.publisher = publisher;
        this.author = author != null ? author : "";
        this.ISBN = ISBN;
        ProductDB productdb = new ProductDB();
        productdb.update(this);
    }

    @Override
    public String getDetails() {
        return "Book [" + this.getProductID() + "] :\n" + 
                "Name: " + this.getName() + '\n' + 
                "Price: " + this.getPrice() + '\n' + 
                "Description: " + this.getDescription() + '\n' +
                "Publisher: " + this.getPublisher() + '\n'+
                "Author: " + this.getAuthor() + '\n' + 
                "ISBN " + this.getISBN()
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


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getISBN() {
        return ISBN;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

}