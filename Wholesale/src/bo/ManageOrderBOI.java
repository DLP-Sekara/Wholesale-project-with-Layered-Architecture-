package bo;

import model.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ManageOrderBOI extends SuperBO {
    boolean deleteOrderItem(String selectedItem, String item) throws SQLException, ClassNotFoundException;

    Object getOrderDetail(String orderId) throws SQLException, ClassNotFoundException;

    boolean updateItem(String qty, String orderId, String itemCode) throws SQLException, ClassNotFoundException;

    boolean deleteOrder(String id) throws SQLException, ClassNotFoundException;

    List<String> getCustIds() throws SQLException, ClassNotFoundException;

    List<String> getorderIds(String customerID) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDetails> getItem(String newValue) throws SQLException, ClassNotFoundException;
}
