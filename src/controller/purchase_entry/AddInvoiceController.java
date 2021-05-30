package controller.purchase_entry;

import app.utils.HelperMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.io.IOException;

public class AddInvoiceController {

    @FXML
    public Button selectBtn;
    @FXML
    public DatePicker dateOfPurchaseDatePicker;
    @FXML
    public Button confirmBtn;
    @FXML
    public Button cancelBtn;


    public void selectOnClick(ActionEvent actionEvent) throws IOException {
        HelperMethods helper = new HelperMethods();
        helper.openWindow("../../view/select-supplier.fxml", "Select The Supplier");
    }


    public void cancelOnClick(ActionEvent actionEvent) {
        Stage window = (Stage) cancelBtn.getScene().getWindow();
        window.close();
    }
}