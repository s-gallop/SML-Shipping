package model;


import java.sql.Date;

public class Order {
    private int orderId;
    private double totalPrice;
    private Date startDate;
    private Date endDate;
    private int accountId;

    public Order(int orderId) {
        this.orderId = orderId;
    }

    public Order(int orderId, double totalPrice, Date startDate, Date endDate, int accountId) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.accountId = accountId;
    }

    public int getOrderId() {
        return orderId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getAccountId() {
        return accountId;
    }
}
