package model;

import java.util.ArrayList;

public class Order {
    private String orderID;
    private String date;
    private String CustomerId;
    private ArrayList<model.OrderDetails>orderDetails;

    public Order() {
    }

    public Order(String orderID, String date, String customerId, ArrayList<model.OrderDetails> orderDetails) {
        this.orderID = orderID;
        this.date = date;
        CustomerId = customerId;
        this.orderDetails = orderDetails;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public ArrayList<model.OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<model.OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID='" + orderID + '\'' +
                ", date='" + date + '\'' +
                ", CustomerId='" + CustomerId + '\'' +
                ", orderDetails=" + orderDetails +
                '}';
    }
}
