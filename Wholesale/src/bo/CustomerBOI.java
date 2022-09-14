package bo;

import model.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBOI extends SuperBO{
    boolean deleteCustomer(String id)throws SQLException, ClassNotFoundException;

    ArrayList<Customer> getAllCustomer()throws SQLException, ClassNotFoundException;
}
