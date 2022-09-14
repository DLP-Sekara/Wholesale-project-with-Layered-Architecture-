package controller;

import bo.BoFactory;
import bo.ManageOrderBOI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.OrderDetails;
import view.tm.OrderTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageOrderFormController {
    public AnchorPane lblManageOrderPage;
    public Label lblOrderId;
    public ComboBox<String> cmbCustomerId;
    public TextArea txtOrderId;
    public MenuButton menubtnOrderIds;
    public ComboBox<String> cmbOrderIds;
    public TableView<OrderTm> tblOrderDetails;
    public TableColumn colItemCode;
    public TableColumn colUnitPrice;
    public TableColumn colQuantity;
    public TableColumn colDiscount;
    public TableColumn colAmount;
    public TextField txtTempOrderId;
    public Label lblPackSize;
    public Label lblQtyHand;
    public Label lblDescription;
    public Label lblItemCode;
    public TextField txtDiscount;
    public TextField txtUnitPrice;
    public TextField txtQuantity;
    public TextField txtItemCode;
    public Button confirmButton;
    public Button deleteItemButton;
    public Button deleteOrderButton;
    String tempOrderID = null;
    ObservableList<OrderTm> obList = FXCollections.observableArrayList();
    String tempOrderId;
    ManageOrderBOI manageOrderBOI = (ManageOrderBOI) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.MANAGE_ORDER);

    public void initialize() throws SQLException, ClassNotFoundException {

        deleteItemButton.setDisable(true);
        deleteOrderButton.setDisable(true);
        confirmButton.setDisable(true);

        txtItemCode.setDisable(true);
        txtUnitPrice.setDisable(true);
        txtDiscount.setDisable(true);

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        loadCustomerid();

        cmbOrderIds.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                tempOrderId = newValue;
                setItemData(getItem(newValue));
                deleteOrderButton.setDisable(false);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }));
        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                loadOrderid(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        tblOrderDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                deleteItemButton.setDisable(false);
                confirmButton.setDisable(false);
                txtItemCode.setText(newValue.getItemCode());
                txtUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
                txtQuantity.setText(String.valueOf(newValue.getOrderQty()));
                txtDiscount.setText(String.valueOf(newValue.getDiscount()));
            }
        });
    }

    private void setItemData(ArrayList<OrderDetails> orders) throws SQLException, ClassNotFoundException {
        tblOrderDetails.getItems().clear();
        orders.forEach(e -> {
            double amount = (e.getUnitPrice() * e.getOrderQty()) - (e.getUnitPrice() * e.getOrderQty() * e.getDiscount() / 100);
            obList.add(new OrderTm(e.getOrderID(), e.getItemCode(), e.getUnitPrice(), e.getOrderQty(), e.getDiscount(), amount));
        });
        tblOrderDetails.setItems(obList);

    }

    private ArrayList<OrderDetails> getItem(String newValue) throws SQLException, ClassNotFoundException {

        return manageOrderBOI.getItem(newValue);
    }

    private void loadOrderid(String newValue) throws SQLException, ClassNotFoundException {
        List<String> orderid = getorderIds(newValue);
        cmbOrderIds.getItems().clear();
        cmbOrderIds.getItems().addAll(orderid);
    }

    private List<String> getorderIds(String CustomerID) throws SQLException, ClassNotFoundException {
        return manageOrderBOI.getorderIds(CustomerID);
    }

    private void loadCustomerid() throws SQLException, ClassNotFoundException {
        List<String> itemId = getIds();
        cmbCustomerId.getItems().addAll(itemId);
    }

    private List<String> getIds() throws SQLException, ClassNotFoundException {

        return manageOrderBOI.getCustIds();
    }

    public void backToDashBoard(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/DashBoard.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) lblManageOrderPage.getScene().getWindow();
        window.setTitle("WHOLESALE");
        window.setScene(new Scene(load));
    }

    public void deleteorderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (deleteOrder(cmbOrderIds.getSelectionModel().getSelectedItem())) {
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
            tblOrderDetails.getItems().clear();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    private boolean deleteOrder(String id) throws SQLException, ClassNotFoundException {
        return manageOrderBOI.deleteOrder(id);
    }

    public void UpdateOrderDetails(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (updateItem(txtQuantity.getText(), cmbOrderIds.getSelectionModel().getSelectedItem(), txtItemCode.getText())) {
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
            tblOrderDetails.getItems().clear();
            setItemData(getItem(tempOrderId));
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    private boolean updateItem(String Qty, String OrderId, String itemCode) throws SQLException, ClassNotFoundException {
        return manageOrderBOI.updateItem(Qty, OrderId, itemCode);
    }

    public void setOnOrderDetails(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String orderId = txtTempOrderId.getText();
        OrderDetails o1 = (OrderDetails) getOrderDetails(orderId);
        if (o1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(o1);
        }
    }

    private void setData(OrderDetails o) {
        txtTempOrderId.setText(o.getOrderID());
        txtUnitPrice.setText(String.valueOf(o.getUnitPrice()));
        txtQuantity.setText(String.valueOf(o.getOrderQty()));
        txtDiscount.setText(String.valueOf(o.getDiscount()));

    }

    private Object getOrderDetails(String orderId) throws SQLException, ClassNotFoundException {
        return manageOrderBOI.getOrderDetail(orderId);
    }

    public void deleteorderItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (deleteOrderItem(txtItemCode.getText(), cmbOrderIds.getSelectionModel().getSelectedItem())) {
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
            tblOrderDetails.getItems().clear();
            setItemData(getItem(tempOrderId));

        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    private boolean deleteOrderItem(String selectedItem, String item) throws SQLException, ClassNotFoundException {
        return manageOrderBOI.deleteOrderItem(selectedItem, item);
    }
}
