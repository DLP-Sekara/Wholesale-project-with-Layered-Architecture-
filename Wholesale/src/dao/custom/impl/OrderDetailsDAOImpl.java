package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDetailDAO;
import entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailDAO {

    @Override
    public boolean add(OrderDetail temp) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO OrderDetail VALUES(?,?,?,?,?)", temp.getOrderID(), temp.getItemCode(), temp.getUnitPrice(), temp.getOrderQty(), temp.getDiscount());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean update(OrderDetail orderDetails) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean search(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public OrderDetail getOrderDetail(String s) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM OrderDetail WHERE OrderID=?",s);
        if (rst.next()) {
            return new OrderDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4),
                    rst.getDouble(5)
            );

        } else {
            return null;
        }
    }


    public ArrayList<OrderDetail> getItem(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst =CrudUtil.executeQuery("SELECT * FROM OrderDetail WHERE OrderID='" + code + "'");
        ArrayList<OrderDetail> orders = new ArrayList<>();
        while (rst.next()) {
            orders.add(new OrderDetail(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getDouble(3),
                            rst.getInt(4),
                            rst.getDouble(5)
                    )
            );
        }
        return orders;
    }

    public boolean updateItem(String Qty, String OrderId, String itemCode) throws SQLException, ClassNotFoundException {
       return CrudUtil.executeUpdate("UPDATE OrderDetail SET OrderQty=? Where OrderID='" + OrderId + "' AND ItemCode='" + itemCode + "'",Qty);
    }

    public boolean deleteOrderItem(String selectedItem, String item) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM OrderDetail WHERE ItemCode='" + selectedItem + "' AND OrderID='" + item + "'");
    }

}