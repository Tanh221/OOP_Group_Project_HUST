package Report;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import Products.ProductInfo;
import Users.User;

import Order.Order;
import ReceiveNote.ReceiveNote;

import Databases.OrderDB;
import Databases.ReceiveNoteDB;

public class Report implements Serializable {
	private static final long serialVersionUID = 1L;

    private double revenue;
    private double costs;
    private double profit;
    private LocalDate startDate;
    private LocalDate endDate;

    public Report(LocalDate startDate, LocalDate endDate) throws Exception {
        this.startDate = startDate;
        this.endDate = endDate;
        this.revenue = 0;
        this.costs = 0;
        this.profit = 0;
        this.calculateEveryThing();
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) throws Exception {
        this.startDate = startDate;
        this.calculateEveryThing();
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) throws Exception {
        this.endDate = endDate;
        this.calculateEveryThing();
    }

    public double getRevenue() {
        return revenue;
    }

    public double getCosts() {
        return costs;
    }

    public double getProfit() {
        return profit;
    }

    private void calculateEveryThing() throws Exception {
        OrderDB orderdb = new OrderDB();
        ReceiveNoteDB receivenotedb = new ReceiveNoteDB();
        ArrayList<Order> orderList = orderdb.getByPeriod(this.startDate, this.endDate);
        ArrayList<ReceiveNote> receivenoteList = receivenotedb.getByPeriod(this.startDate, this.endDate);
        double tmprevenue = 0;
        double tmpcosts = 0;
        double tmpprofit = 0;
        for(Order o : orderList) {
            tmprevenue += o.getTotalCost();
        }
        for(ReceiveNote rn : receivenoteList) {
            tmpcosts += rn.getTotalCost();
        }
        tmpprofit = tmprevenue - tmpcosts;
        this.revenue = tmprevenue;
        this.costs = tmpcosts;
        this.profit = tmpprofit;
    }
}
