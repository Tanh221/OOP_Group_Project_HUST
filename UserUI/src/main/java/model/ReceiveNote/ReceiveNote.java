package model.ReceiveNote;

import model.Databases.ReceiveNoteDB;
import model.Databases.UserDB;
import model.Products.Product;
import model.Products.ReceiveProductInfo;
import model.Users.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReceiveNote implements Serializable {

    private static final long serialVersionUID = 1L;

    private static int idCounter = 0;

    private int receiveNoteID;
    private User user;
    private LocalDate receiveNoteDate;
    private ReceiveProductInfo receiveProductInfo;
    private double totalCost;

    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_RESET = "\u001B[0m";

    public ReceiveNote(User user, Product product, double receivePrice) throws Exception {
        ReceiveNoteDB receivenotedb = new ReceiveNoteDB();
        ArrayList<ReceiveNote> allreceivenote = receivenotedb.getAllReceiveNotes();
        for(ReceiveNote o : allreceivenote) {
            ReceiveNote.idCounter = Math.max(ReceiveNote.idCounter, o.getReceiveNoteID());
        }
        this.receiveNoteID = ++ReceiveNote.idCounter;
        this.user = user;
        this.receiveNoteDate = LocalDate.now();
        ReceiveProductInfo receiveProductInfo = new ReceiveProductInfo(product, 1, receivePrice);
        this.receiveProductInfo = receiveProductInfo;
        this.totalCost = receiveProductInfo.getReceivePrice() * receiveProductInfo.getQuantity();
        receivenotedb.update(this);
    }

    public ReceiveNote(User user, Product product, int quantity, double receivePrice) throws Exception {
        ReceiveNoteDB receivenotedb = new ReceiveNoteDB();
        ArrayList<ReceiveNote> allreceivenote = receivenotedb.getAllReceiveNotes();
        for(ReceiveNote o : allreceivenote) {
            ReceiveNote.idCounter = Math.max(ReceiveNote.idCounter, o.getReceiveNoteID());
        }
        this.receiveNoteID = ++ReceiveNote.idCounter;
        this.user = user;
        this.receiveNoteDate = LocalDate.now();
        ReceiveProductInfo receiveProductInfo = new ReceiveProductInfo(product, quantity, receivePrice);
        this.receiveProductInfo = receiveProductInfo;
        this.totalCost = receiveProductInfo.getReceivePrice() * receiveProductInfo.getQuantity();
        receivenotedb.update(this);
    }

    public int getReceiveNoteID() {
        return this.receiveNoteID;
    }

    public User getUser() throws Exception {
        this.syncUserWithDB();
        return this.user;
    }

    public LocalDate getReceiveNoteDate() throws Exception {
        return this.receiveNoteDate;
    }

    public ReceiveProductInfo getReceiveProductInfo() throws Exception {
        return this.receiveProductInfo;
    }

    public double getTotalCost() throws Exception {
        return this.totalCost;
    }

    public void syncUserWithDB() throws Exception {
        UserDB userdb = new UserDB();
        this.user = userdb.getByUserID(this.user.getUserID());
    }

    public String getDetails() throws Exception {
        String optstr = "";
        optstr += "Receive Note ID : " + this.getReceiveNoteID() + '\n';
        optstr += ANSI_BLUE + "Staff/Admin: "+ this.getUser().getUsername() + ANSI_RESET + '\n';
        optstr += "Date ordered: " + this.getReceiveNoteDate() + '\n';
        optstr += this.getReceiveProductInfo().getDetails() + '\n';
        optstr += ("Total cost : " + this.getTotalCost()) + '\n';
        return optstr;
    }

    public void print() throws Exception {
        System.out.println(this.getDetails());
    }
}