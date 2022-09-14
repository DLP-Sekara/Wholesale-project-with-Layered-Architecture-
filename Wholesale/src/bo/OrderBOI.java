package bo;

import model.Customer;
import model.Items;
import model.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderBOI extends SuperBO{
     boolean saveOrder(Order order) throws SQLException, ClassNotFoundException;

     String getLastOrderId()throws SQLException, ClassNotFoundException;

     List<String> getItemIds()throws SQLException, ClassNotFoundException;

     Items getItem(String newValue)throws SQLException, ClassNotFoundException;

     boolean addCustomer(Customer c)throws SQLException, ClassNotFoundException;

     String getLastCustId()throws SQLException, ClassNotFoundException;
}
