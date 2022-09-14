package bo.impl;

import bo.CustomerBOI;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import model.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOIImpl implements CustomerBOI {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public ArrayList<Customer> getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomer = new ArrayList<>();
        ArrayList<entity.Customer> all = customerDAO.getAll();
        for (entity.Customer customer : all) {
            allCustomer.add(new Customer(customer.getCustomerID(), customer.getCustomerTitle(), customer.getCustomerName()
                    , customer.getCustomerAddress(), customer.getCity(), customer.getProvince(), customer.getPostalCode()));
        }
        return allCustomer;
    }
}
