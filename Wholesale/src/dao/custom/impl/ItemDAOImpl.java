package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ItemDAO;
import entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public boolean add(Item it) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Item VALUES(?,?,?,?,?,?)", it.getItemCode(), it.getDescription(), it.getPackSize(), it.getUnitPrice(), it.getQtyOnHand(), it.getDiscount());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Item WHERE ItemCode='" + s + "'");
    }

    @Override
    public boolean update(Item items) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean search(String s) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT * FROM Item WHERE ItemCode=?", s).next();
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Item> allItems = new ArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item");
        while (rst.next()) {
            allItems.add(new Item(
                    rst.getString("itemCode"),
                    rst.getString("description"),
                    rst.getString("packSize"),
                    rst.getDouble("unitPrice"),
                    rst.getInt("qtyOnHand"),
                    rst.getDouble("discount")));
        }
        return allItems;
    }

    public boolean checkItems(String code) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT * FROM Item WHERE ItemCode=?", code).next();
    }

    public Item getItem(String s) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE Description='" + s + "'");
        if (rst.next()) {
            return new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getDouble(6)
            );
        } else {
            return null;
        }
    }

    public boolean updateItem(Item i, String ItemId) throws SQLException, ClassNotFoundException {

        return CrudUtil.executeUpdate("UPDATE Item SET ItemCode=?,Description=?, PackSize=?,UnitPrice=?,QtyOnHand=?,Discount=? Where ItemCode='" + ItemId + "'",
                i.getItemCode(), i.getDescription(), i.getPackSize(), i.getUnitPrice(), i.getQtyOnHand(), i.getDiscount());
    }

    public List<String> getItemIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item");
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(2)
            );
        }
        return ids;
    }

    public boolean updateQty(String itemCode, int orderQty) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET qtyOnHand=(QtyOnHand-'" + orderQty + "')WHERE Description='" + itemCode + "'");
    }

}

