package bo.impl;

import bo.OrderBOI;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailDAO;
import db.DbConnection;
import entity.Item;
import entity.OrderDetail;
import model.Customer;
import model.Items;
import model.Order;
import model.OrderDetails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class OrderBOIImpl implements OrderBOI {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailDAO orderDetailsDAo = (OrderDetailDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);

    @Override
    public boolean saveOrder(Order order) throws SQLException, ClassNotFoundException {
        Connection con = null;

        con = DbConnection.getInstance().getConnection();
        con.setAutoCommit(false);
        boolean saveOeder = orderDAO.add(new entity.Order(order.getOrderID(), order.getDate(), order.getCustomerId()));

        if (!saveOeder) {
            con.rollback();
            con.setAutoCommit(true);
            return false;
        }

        for (OrderDetails temp : order.getOrderDetails()) {
            OrderDetail orderDetails1 = new OrderDetail(order.getOrderID(), temp.getItemCode(), temp.getUnitPrice(), temp.getOrderQty(), temp.getDiscount());
            boolean saveOrderDetails = orderDetailsDAo.add(orderDetails1);
            if (!saveOrderDetails) {
                con.rollback();
                con.setAutoCommit(true);
                return false;
            }
            boolean itemUpdate = itemDAO.updateQty(orderDetails1.getItemCode(), orderDetails1.getOrderQty());
            if (!itemUpdate) {
                con.rollback();
                con.setAutoCommit(true);
                return false;
            }

        }
        con.commit();
        con.setAutoCommit(true);
        return true;
    }

    @Override
    public String getLastOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.getLastOrderId();
    }

    @Override
    public List<String> getItemIds() throws SQLException, ClassNotFoundException {
        return itemDAO.getItemIds();
    }

    @Override
    public Items getItem(String newValue) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.getItem(newValue);
        return new Items(item.getItemCode(), item.getDescription(), item.getPackSize(), item.getUnitPrice(), item.getQtyOnHand(), item.getDiscount());
    }

    @Override
    public boolean addCustomer(Customer c) throws SQLException, ClassNotFoundException {
        return customerDAO.add(new entity.Customer(c.getCustId(), c.getCustTitle(), c.getCustName(), c.getCustAddress(), c.getCustCity(), c.getCustProvince(), c.getCustPostalCode()));
    }

    @Override
    public String getLastCustId() throws SQLException, ClassNotFoundException {
        return customerDAO.getLastCustId();
    }
}
