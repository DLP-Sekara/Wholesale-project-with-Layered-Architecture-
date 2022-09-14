package dao.custom;

import dao.CrudDAO;
import entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer, String> {

    String getLastCustId() throws SQLException, ClassNotFoundException;

    List<String> getCustIds() throws SQLException, ClassNotFoundException;
}
