package controller;

import Validation.Validation;
import bo.BoFactory;
import bo.OrderBOI;
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
import model.Customer;
import model.Items;
import model.Order;
import model.OrderDetails;
import view.tm.CartTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class MakeOrderFormController {
    private final OrderBOI orderBOI = (OrderBOI) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ORDER);
    public AnchorPane lblPlaceOrderPage;
    public TextField txtCustomerPostalCode;
    public TextField txtCustomerProvince;
    public TextField txtCustomerCity;
    public TextField txtCustomerAddress;
    public TextField txtCustomerName;
    public TextField txtCustomerTitle;
    public Label lblCustomerId;
    public ComboBox<String> cmbItemCode;
    public TextField txtQuantity;
    public TextField txtQtyOnHand;
    public TextField txtUnitPrice;
    public TextField txtPackSize;
    public TextField txtDescription;
    public TableView<CartTm> tblItemData;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQuantity;
    public TableColumn colDiscount;
    public TableColumn colAmount;
    public TextField txtDiscount;
    public Label txtTotal2;
    public Label txtTotal1;
    public Label txtDate;
    public Label lblOrderId;
    public Label lblWarning;
    public Button clearButton;
    public Button saveButton;
    public Button addToCartButton;
    int cartSelectedRowForRemove = -1;
    int tempId = 0;
    String tempCustID = null;
    ObservableList<CartTm> obList = FXCollections.observableArrayList();
    LinkedHashMap<TextField, Pattern> map1 = new LinkedHashMap();
    Pattern CustTitlePattern = Pattern.compile("^[A-z0-9]{4,50}$");
    Pattern CustnamePattern = Pattern.compile("^[A-z0-9]{4,50}$");
    Pattern CusrAddressPattern = Pattern.compile("^[A-z0-9]{4,50}$");
    Pattern CustCityPattern = Pattern.compile("^[A-z0-9]{4,50}$");
    Pattern CustProvincePattern = Pattern.compile("^[A-z0-9]{4,50}$");
    Pattern CustPostalCodePattern = Pattern.compile("^[A-z0-9]{4,50}$");
    LinkedHashMap<TextField, Pattern> map2 = new LinkedHashMap();
    Pattern QuantityPattern = Pattern.compile("^[0-9]{1,50}$");

    public void initialize() throws SQLException, ClassNotFoundException {
        //clearButton.setDisable(true);
        saveButton.setDisable(true);
        storeValidations();

        addToCartButton.setDisable(true);
        storeValidations2();

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        loadItem();

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                setItemData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }));

        tblItemData.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
        });

    }

    public void backToDashBoard(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/DashBoard.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) lblPlaceOrderPage.getScene().getWindow();
        window.setTitle("WHOLESALE");
        window.setScene(new Scene(load));
    }

    public void saveCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String cid = getLastCustId();
        String finalId = "C-001";
        if (cid != null) {
            String[] splitString = cid.split("-");
            int id = Integer.parseInt(splitString[1]);
            id++;

            if (id < 10) {
                finalId = "C-00" + id;
            } else if (id < 100) {
                finalId = "C-0" + id;
            } else {
                finalId = "C-" + id;
            }
            lblCustomerId.setText(finalId);
        } else {
            lblCustomerId.setText(finalId);
        }

        Customer c1 = new Customer(finalId, txtCustomerTitle.getText(), txtCustomerName.getText(), txtCustomerAddress.getText(),
                txtCustomerCity.getText(), txtCustomerProvince.getText(), txtCustomerPostalCode.getText());
        if (saveCustomer(c1)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
            tblItemData.getItems().clear();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }

    private String getLastCustId() throws SQLException, ClassNotFoundException {

        return orderBOI.getLastCustId();
    }

    boolean saveCustomer(Customer c) throws SQLException, ClassNotFoundException {

        return orderBOI.addCustomer(c);
    }

    private void storeValidations2() {
        map2.put(txtQuantity, QuantityPattern);
    }

    private void storeValidations() {
        map1.put(txtCustomerTitle, CustTitlePattern);
        map1.put(txtCustomerName, CustnamePattern);
        map1.put(txtCustomerAddress, CusrAddressPattern);
        map1.put(txtCustomerCity, CustCityPattern);
        map1.put(txtCustomerProvince, CustProvincePattern);
        map1.put(txtCustomerPostalCode, CustPostalCodePattern);
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = Validation.validateCustomer(map1, saveButton);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
                lblCustomerId.requestFocus();
            } else if (response instanceof Boolean) {

            }
        }
    }

    public void checkKeyReleased(KeyEvent keyEvent) {
        Object response = Validation.validateCustomer(map2, addToCartButton);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
                lblCustomerId.requestFocus();
            } else if (response instanceof Boolean) {

            }
        }
    }

    private void setItemData(String newValue) throws SQLException, ClassNotFoundException {
        Items i1 = getItem(newValue);
        if (i1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtDescription.setText(i1.getDescription());
            txtPackSize.setText(i1.getPackSize());
            txtUnitPrice.setText(String.valueOf(i1.getUnitPrice()));
            txtQtyOnHand.setText(String.valueOf(i1.getQtyOnHand()));
            txtDiscount.setText(String.valueOf(i1.getDiscount()));
        }
    }

    private void loadDate() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy.MM.dd");
        txtDate.setText(f.format(date));
    }

    private Items getItem(String newValue) throws SQLException, ClassNotFoundException {

        return orderBOI.getItem(newValue);
    }

    public void loadItem() throws SQLException, ClassNotFoundException {
        System.out.println("done");
        List<String> itemDes = getIds();
        cmbItemCode.getItems().addAll(itemDes);
    }

    private List<String> getIds() throws SQLException, ClassNotFoundException {

        return orderBOI.getItemIds();

    }

    public void addToCartOnAction(ActionEvent actionEvent) {
        String itemCode = cmbItemCode.getSelectionModel().getSelectedItem();
        String description = txtDescription.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());
        double discount = Double.parseDouble(txtDiscount.getText());
        double amount = (unitPrice * quantity) - (unitPrice * quantity * discount / 100);

        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        if (qtyOnHand < quantity) {
            new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
            return;
        }
        CartTm tm = new CartTm(itemCode, description, unitPrice, quantity, discount, amount);

        int rowNumber = isExists(tm);
        if (rowNumber == -1) {// new Add
            obList.add(tm);
        } else {
            CartTm temp = obList.get(rowNumber);
            CartTm newTm = new CartTm(temp.getItemCode(), temp.getDescription(), temp.getUnitPrice(),
                    temp.getQuantity() + quantity, temp.getDiscount(), amount + temp.getAmount());

            if (qtyOnHand <= temp.getQuantity()) {
                new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
                return;
            }

            obList.remove(rowNumber);
            obList.add(newTm);
        }
        tblItemData.setItems(obList);
        calculateCost();

    }

    private int isExists(CartTm tm) {
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getItemCode().equals(obList.get(i).getItemCode())) {
                return i;
            }
        }
        return -1;
    }

    void calculateCost() {
        double ttl = 0;
        for (CartTm tm : obList
        ) {
            ttl += tm.getAmount();
        }
        txtTotal1.setText(ttl + " /=");
        txtTotal2.setText(ttl + " /=");
    }

    public void clearItemOnAction(ActionEvent actionEvent) {
        if (cartSelectedRowForRemove == -1) {
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        } else {
            obList.remove(cartSelectedRowForRemove);
            calculateCost();
            tblItemData.refresh();
        }
    }

    public void confirmOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        loadDate();
        String cid = getLastOrderId();
        String finalId = "O-001";
        if (cid != null) {
            String[] splitString = cid.split("-");
            int id = Integer.parseInt(splitString[1]);
            id++;

            if (id < 10) {
                finalId = "O-00" + id;
            } else if (id < 100) {
                finalId = "O-0" + id;
            } else {
                finalId = "O-" + id;
            }
            lblOrderId.setText(finalId);
        } else {
            lblOrderId.setText(finalId);
        }

        ArrayList<OrderDetails> orderDetails = new ArrayList<>();
        for (CartTm tempTm : obList) {
            orderDetails.add(new OrderDetails(lblOrderId.getText(), tempTm.getItemCode(), tempTm.getUnitPrice(), tempTm.getQuantity(), tempTm.getDiscount()));
        }

        if (lblCustomerId.getText().equals("0")) {
            new Alert(Alert.AlertType.WARNING, "Enter New Customer").show();
        } else {
            Order order = new Order(lblOrderId.getText(), txtDate.getText(), lblCustomerId.getText(), orderDetails);

            try {
                if (saveOrder(order)) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Success").show();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again").show();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private String getLastOrderId() throws SQLException, ClassNotFoundException {

        return orderBOI.getLastOrderId();
    }

    private boolean saveOrder(Order order) throws SQLException, ClassNotFoundException {
        return orderBOI.saveOrder(order);
    }

    public void clearCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/ClearCustomerForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void checkAllorderDetailsonAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageOrderForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) lblPlaceOrderPage.getScene().getWindow();
        window.setTitle("Manage customer order");
        window.setMaximized(true);
        window.setScene(new Scene(load));
    }
}