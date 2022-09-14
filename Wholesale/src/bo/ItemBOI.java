package bo;

import model.Items;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBOI extends SuperBO {
    ArrayList<Items> getAllItems()throws SQLException, ClassNotFoundException;

    boolean checkItems(String text)throws SQLException, ClassNotFoundException;

    boolean addItem(Items it)throws SQLException, ClassNotFoundException;

    boolean updateItem(Items i, String text)throws SQLException, ClassNotFoundException;

    boolean deleteItem(String id)throws SQLException, ClassNotFoundException;
}
