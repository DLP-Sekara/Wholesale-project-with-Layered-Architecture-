package controller;

import bo.BoFactory;
import bo.CustomerBOI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Customer;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClearCustomerFormController implements Initializable {
    public TextField txtCustomerId;
    public AnchorPane clearCustomerPage;
    public TableView<Customer> tblCustomers;
    public TableColumn colCustomerID;
    public TableColumn ColCustomerName;
    String tempCustomerName = null;
    ObservableList<Customer> obList = FXCollections.observableArrayList();
    CustomerBOI customerBOI = (CustomerBOI) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.CUSTOMER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("custId"));
        ColCustomerName.setCellValueFactory(new PropertyValueFactory<>("custName"));
        try {
            loadCustomers(GetAllCustomers());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tblCustomers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                tempCustomerName = newValue.getCustId();
            }
        });
    }

    public void deleteCustomer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (tempCustomerName == null) {
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        } else {
            if (deleteCustomerOnAction(tempCustomerName)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
                tblCustomers.getItems().clear();
                loadCustomers(GetAllCustomers());
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        }
    }

    private boolean deleteCustomerOnAction(String id) throws SQLException, ClassNotFoundException {

        return customerBOI.deleteCustomer(id);

    }

    private void loadCustomers(ArrayList<Customer> getAllCustomers) {
        getAllCustomers.forEach(e -> {
            obList.add(new Customer(e.getCustId(), e.getCustTitle(), e.getCustName(), e.getCustAddress(), e.getCustCity(), e.getCustProvince(), e.getCustPostalCode()));
        });
        tblCustomers.setItems(obList);
    }

    private ArrayList<Customer> GetAllCustomers() throws SQLException, ClassNotFoundException {

        return customerBOI.getAllCustomer();

    }
}