package bo.impl;

import bo.ItemBOI;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import entity.Item;
import model.Items;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOIImpl implements ItemBOI {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public ArrayList<Items> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Items> allItems = new ArrayList<>();
        ArrayList<Item> all = itemDAO.getAll();
        for (Item item : all) {
            allItems.add(new Items(item.getItemCode(), item.getDescription(), item.getPackSize(), item.getUnitPrice(), item.getQtyOnHand(), item.getDiscount()));
        }
        return allItems;
    }

    @Override
    public boolean checkItems(String text) throws SQLException, ClassNotFoundException {
        return itemDAO.checkItems(text);
    }

    @Override
    public boolean addItem(Items it) throws SQLException, ClassNotFoundException {
        return itemDAO.add(new Item(it.getItemCode(), it.getDescription(), it.getPackSize(), it.getUnitPrice(), it.getQtyOnHand(), it.getDiscount()));
    }

    @Override
    public boolean updateItem(Items i, String text) throws SQLException, ClassNotFoundException {
        return itemDAO.updateItem(new Item(i.getItemCode(), i.getDescription(), i.getPackSize(), i.getUnitPrice(), i.getQtyOnHand(), i.getDiscount()), text);
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(id);
    }
}
