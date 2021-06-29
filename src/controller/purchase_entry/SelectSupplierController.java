package controller.purchase_entry;

import app.utils.DBUtils;
import app.utils.HelperMethods;
import app.utils.NameHolder;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Supplier;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Locale;
import java.util.ResourceBundle;

public class SelectSupplierController implements Initializable {
    @FXML public Button newBtn;
    @FXML public Button cancelBtn;
    @FXML public Button confirmBtn;
    @FXML public TextField searchTextField;
    @FXML public TableView<Supplier> suppliersTableView;
    @FXML public TableColumn<Supplier, String> supplierNameCol;
    @FXML public TableColumn<Supplier, String> wilayaCol;
    @FXML public TableColumn<Supplier, String> phoneNumberCol;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        confirmBtn.disableProperty().bind(Bindings.isEmpty(suppliersTableView.getSelectionModel().getSelectedItems()));
        supplierNameCol.setCellValueFactory(new PropertyValueFactory<>("SupplierName"));
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
        wilayaCol.setCellValueFactory(new PropertyValueFactory<>("Wilaya"));
        suppliersTableView.setItems(getSuppliers());

        FilteredList<Supplier> supplierFilteredList = new FilteredList<>(getSuppliers(), b -> true);
        searchTextField.textProperty().addListener((observable,oldValue,newValue) -> {
                supplierFilteredList.setPredicate(supplier -> {
                    if (newValue==null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (supplier.getSupplierName().toLowerCase().indexOf(lowerCaseFilter) != -1){
                        return true;
                    }
                    else  if (supplier.getPhoneNumber().toLowerCase().indexOf(lowerCaseFilter) != -1){
                        return true;
                    } else  if (supplier.getWilaya().toLowerCase().indexOf(lowerCaseFilter) != -1){
                        return true;
                    }
                    else return false;
                });
            });
        SortedList<Supplier> supplierSortedList = new SortedList<>(supplierFilteredList);

        supplierSortedList.comparatorProperty().bind(suppliersTableView.comparatorProperty());

        suppliersTableView.setItems(supplierSortedList);

    }

    public void updateSuppliers(){
        suppliersTableView.setItems(getSuppliers());
    }

    public ObservableList<Supplier> getSuppliers(){
        ObservableList<Supplier> list = FXCollections.observableArrayList();
        Connection c = DBUtils.getConnection();
        String sqlQuery = "select * from suppliers";
        Statement st;
        ResultSet rs;
        try {
            st = c.createStatement();
            rs = st.executeQuery(sqlQuery);
            Supplier supplier;

            while(rs.next()){
                supplier = new Supplier(rs.getInt("id"),
                        rs.getString("supplier_name"),
                        rs.getString("phone_number"),
                        rs.getString("wilaya"));

                list.add(supplier);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return list;
    }

    public void cancelOnClick(ActionEvent actionEvent) {
        Stage window = (Stage) cancelBtn.getScene().getWindow();
        window.close();
    }

    public void newSupplierOnClick(ActionEvent actionEvent) throws IOException {
        Stage window = HelperMethods.openWindow("Purchase_Entry/add-supplier.fxml",
                "what is iiit??");
        window.setOnHidden((e) -> {
            updateSuppliers();
        });
    }


    public void confirmOnClick(ActionEvent actionEvent) {
        NameHolder.supplierName = suppliersTableView.
                getSelectionModel().getSelectedItem().supplierName;
        Stage window = (Stage) confirmBtn.getScene().getWindow();
        window.close();
    }
}
