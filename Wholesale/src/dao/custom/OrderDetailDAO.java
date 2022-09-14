package dao.custom;

import dao.CrudDAO;
import entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailDAO extends CrudDAO<OrderDetail, String> {

    OrderDetail getOrderDetail(String s) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDetail> getItem(String code) throws SQLException, ClassNotFoundException;

    boolean updateItem(String Qty, String OrderId, String itemCode) throws SQLException, ClassNotFoundException;

    boolean deleteOrderItem(String selectedItem, String item) throws SQLException, ClassNotFoundException;
}
