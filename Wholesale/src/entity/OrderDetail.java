package entity;

public class OrderDetail {
    private String OrderID;
    private String ItemCode;
    private double UnitPrice;
    private int OrderQty;
    private double Discount;

    public OrderDetail() {
    }

    public OrderDetail(String orderID, String itemCode, double unitPrice, int orderQty, double discount) {
        OrderID = orderID;
        ItemCode = itemCode;
        UnitPrice = unitPrice;
        OrderQty = orderQty;
        Discount = discount;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }

    public int getOrderQty() {
        return OrderQty;
    }

    public void setOrderQty(int orderQty) {
        OrderQty = orderQty;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }
}
