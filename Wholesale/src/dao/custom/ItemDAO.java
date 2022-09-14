package dao.custom;

import dao.CrudDAO;
import entity.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemDAO extends CrudDAO<Item, String> {
    boolean checkItems(String code) throws SQLException, ClassNotFoundException;

    Item getItem(String s) throws SQLException, ClassNotFoundException;

    boolean updateItem(Item i, String ItemId) throws SQLException, ClassNotFoundException;

    List<String> getItemIds() throws SQLException, ClassNotFoundException;

    boolean updateQty(String itemCode, int orderQty) throws SQLException, ClassNotFoundException;
}