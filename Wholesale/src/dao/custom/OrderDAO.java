package dao.custom;

import dao.CrudDAO;
import entity.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDAO extends CrudDAO<Order, String> {

    String getLastOrderId() throws SQLException, ClassNotFoundException, SQLException;

    List<String> getorderIds(String CustomerID) throws SQLException, ClassNotFoundException;

    boolean deleteOrderItem(String id) throws SQLException, ClassNotFoundException;

}
