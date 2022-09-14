package controller;

import Validation.Validation;
import bo.BoFactory;
import bo.ItemBOI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Items;
import view.tm.ItemsTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;


public class ManageItemsFormController {
    public AnchorPane lblManageItemsPage;
    public TextField txtItemCode;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQuantityOnHand;
    public TextField txtPackSize;
    public TextField txtDiscount;
    public TableView<ItemsTm> tblItems;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public TableColumn colPackSize;
    public TableColumn colDiscount;
    public Button saveButton;
    public Button updateButton;
    public Button DeleteButton;
    ItemsTm cartSelectedRowForRemove = null;

    ItemBOI itemBOI = (ItemBOI) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ITEM);

    ObservableList<ItemsTm> obList = FXCollections.observableArrayList();

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern ItemCodePattern = Pattern.compile("^[A-z0-9]{4,5}$");
    Pattern DescriptionPattern = Pattern.compile("^[A-z0-9/ ]{3,30}$");
    Pattern packSizePattern = Pattern.compile("^[0-9 ]{0,10000}$");
    Pattern UnitPricePattern = Pattern.compile("^[0-9 ]{0,100000}$");
    Pattern QtyOnHandPattern = Pattern.compile("^[0-9 ]{0,100}$");
    Pattern DiscountPattern = Pattern.compile("^[0-9 ]{0,100}$");

    public void backToDashBoard(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/DashBoard.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) lblManageItemsPage.getScene().getWindow();
        window.setTitle("WHOLESALE");
        // window.setMaximized(true);
        window.setScene(new Scene(load));
    }

    public void initialize() throws SQLException, ClassNotFoundException {
        DeleteButton.setDisable(true);
        updateButton.setDisable(true);
        saveButton.setDisable(true);
        storeValidations();

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        try {
            setAllItemsToTable(getAllitems());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        tblItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cartSelectedRowForRemove = newValue;
                txtItemCode.setText(newValue.getItemCode());
                txtDescription.setText(newValue.getDescription());
                txtPackSize.setText(newValue.getPackSize());
                txtQuantityOnHand.setText(String.valueOf(newValue.getQtyOnHand()));
                txtUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
                txtDiscount.setText(String.valueOf(newValue.getDiscount()));
                DeleteButton.setDisable(false);
                updateButton.setDisable(false);
            }
        });
    }

    private void storeValidations() {
        map.put(txtItemCode, ItemCodePattern);
        map.put(txtDescription, DescriptionPattern);
        map.put(txtPackSize, packSizePattern);
        map.put(txtUnitPrice, UnitPricePattern);
        map.put(txtQuantityOnHand, QtyOnHandPattern);
        map.put(txtDiscount, DiscountPattern);
    }

    private void setAllItemsToTable(ArrayList<Items> items) {
        items.forEach(e -> {
            obList.add(new ItemsTm(e.getItemCode(), e.getDescription(), e.getPackSize(), e.getUnitPrice(), e.getQtyOnHand(), e.getDiscount()));
        });
        tblItems.setItems(obList);
    }

    private ArrayList<Items> getAllitems() throws SQLException, ClassNotFoundException {

        return itemBOI.getAllItems();

    }

    public void saveItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (checkItem(txtItemCode.getText())) {
            new Alert(Alert.AlertType.WARNING, "Item already exists..").show();
        } else {
            Items it = new Items(txtItemCode.getText(), txtDescription.getText(), txtPackSize.getText(), Double.parseDouble(txtUnitPrice.getText()), Integer.parseInt(txtQuantityOnHand.getText()), Double.parseDouble(txtDiscount.getText()));
            if (saveItems(it)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                tblItems.getItems().clear();
                setAllItemsToTable(getAllitems());
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        }

    }

    private boolean checkItem(String text) throws SQLException, ClassNotFoundException {

        return itemBOI.checkItems(text);

    }

    private boolean saveItems(Items it) throws SQLException, ClassNotFoundException {

        return itemBOI.addItem(it);

    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = Validation.validate(map, saveButton);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                //new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
                //txtDescription.requestFocus();
            }
        }
    }

    public void updateItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Items i1 = new Items(txtItemCode.getText(), txtDescription.getText(), txtPackSize.getText(), Double.parseDouble(txtUnitPrice.getText()), Integer.parseInt(txtQuantityOnHand.getText()), Double.parseDouble(txtDiscount.getText()));
        if (updateItem(i1)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
            tblItems.getItems().clear();
            setAllItemsToTable(getAllitems());
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    private boolean updateItem(Items i) throws SQLException, ClassNotFoundException {

        return itemBOI.updateItem(i, txtItemCode.getText());

    }

    public void deleteItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (deleteItem(txtItemCode.getText())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deleted");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                obList.remove(cartSelectedRowForRemove);
                System.out.println("OK chosen");
            }

        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    private boolean deleteItem(String id) throws SQLException, ClassNotFoundException {

        return itemBOI.deleteItem(id);

    }

}
