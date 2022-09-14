package bo.impl;

import bo.ManageOrderBOI;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailDAO;
import entity.OrderDetail;
import model.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageOrderBOIImpl implements ManageOrderBOI {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailDAO orderDetailsDAo = (OrderDetailDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);

    @Override
    public boolean deleteOrderItem(String selectedItem, String item) throws SQLException, ClassNotFoundException {
        return orderDetailsDAo.deleteOrderItem(selectedItem, item);
    }

    @Override
    public Object getOrderDetail(String orderId) throws SQLException, ClassNotFoundException {
        return orderDAO.getorderIds(orderId);
    }

    @Override
    public boolean updateItem(String qty, String orderId, String itemCode) throws SQLException, ClassNotFoundException {
        return orderDetailsDAo.updateItem(qty, orderId, itemCode);
    }

    @Override
    public boolean deleteOrder(String id) throws SQLException, ClassNotFoundException {
        return orderDAO.delete(id);
    }

    @Override
    public List<String> getCustIds() throws SQLException, ClassNotFoundException {
        return customerDAO.getCustIds();
    }

    @Override
    public List<String> getorderIds(String customerID) throws SQLException, ClassNotFoundException {
        return orderDAO.getorderIds(customerID);
    }

    @Override
    public ArrayList<OrderDetails> getItem(String newValue) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetails> allItems = new ArrayList<>();
        ArrayList<OrderDetail> item = orderDetailsDAo.getItem(newValue);
        for (OrderDetail orderDetail : item) {
            allItems.add(new OrderDetails(orderDetail.getOrderID(), orderDetail.getItemCode(), orderDetail.getUnitPrice(), orderDetail.getOrderQty(), orderDetail.getDiscount()));
        }
        return allItems;
    }
}
