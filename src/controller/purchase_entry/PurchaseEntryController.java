package controller.purchase_entry;

import app.utils.DBUtils;
import app.utils.HelperMethods;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Invoice;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class PurchaseEntryController implements Initializable {
    @FXML public Button addInvoiceBtn;
    @FXML public Button editInvoiceBtn;
    @FXML public Button deleteInvoiceBtn;
    @FXML public Button addProductBtn;
    @FXML public Button editProductBtn;
    @FXML public Button deleteProductBtn;

//    dashboard   //
    @FXML public Button dashboardBtn;
    @FXML public Button sellingBtn;
    @FXML public Button stockBtn;
    @FXML public Button suppliersBtn;
    @FXML public Button customersBtn;
    public Stage stage ;
    public Scene scene ;
    public Parent root;
//    dashboard   //

    //    slide menu items
    @FXML public Circle imageCircle ;

    @FXML public Pane openSliderPane;
    @FXML public Pane closeSliderPane;
    @FXML public ImageView openSliderImage;
    @FXML public AnchorPane slider;
    @FXML public AnchorPane x;
//    slide menu items


    @FXML public TableView<Invoice> invoicesTableView;
    @FXML public TableColumn<Invoice, Integer> invoiceIdCol;
    @FXML public TableColumn<Invoice, String> dateOfPurchaseCol;
    @FXML public TableColumn<Invoice, String> supplierCol;

    @FXML public TableView<Product> productsTableView;
    @FXML public TableColumn<Product, Integer> productIdCol;
    @FXML public TableColumn<Product, String> productCol;
    @FXML public TableColumn<Product, Double> priceOfUnitCol;
    @FXML public TableColumn<Product, Integer> quantityCol;
    @FXML public TableColumn<Product, String> categoryCol;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        invoiceIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        dateOfPurchaseCol.setCellValueFactory(new PropertyValueFactory<>("DateOfPurchase"));
        supplierCol.setCellValueFactory(new PropertyValueFactory<>("Supplier"));
        invoicesTableView.setItems(getInvoices());

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        productCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        priceOfUnitCol.setCellValueFactory(new PropertyValueFactory<>("PurhcasedPrice"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("Category"));

//          slide menu items (there is an error here in the url)
//        Image im = new Image("src/view/images/logo-circle.png", false);
//        imageCircle.setFill(new ImagePattern(im));

        //                  SLIDER                             //
        slider.setTranslateX(-255);
        openSliderPane.setVisible(true);
        closeSliderPane.setVisible(false);

        openSliderImage.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-255);

            slide.setOnFinished((ActionEvent e)-> {
                openSliderPane.setVisible(false);
                closeSliderPane.setVisible(true);
            });
        });

        openSliderPane.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);
            System.out.println("kasjdhwo rani nakhdem");
            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-255);

            slide.setOnFinished((ActionEvent e)-> {
                openSliderPane.setVisible(false);
                closeSliderPane.setVisible(true);
            });
        });


        closeSliderPane.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-255);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)-> {
                openSliderPane.setVisible(true);
                closeSliderPane.setVisible(false);
            });
        });

        x.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-255);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)-> {
                openSliderPane.setVisible(true);
                closeSliderPane.setVisible(false);
            });
        });

        //                      SLIDER                          //


    }

//  dashboard   //

    public void dashboardOnClick(ActionEvent actionEvent) throws IOException {
    root = FXMLLoader.load(getClass().getResource("../../view/Dashboard/dashboard.fxml"));
    stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
    scene = new Scene(root,1280,679);
    stage.setScene(scene);
    stage.show();
}

    public void sellingOnClick(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../../view/Selling_Entry/selling-entry.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root,1280,679);
        stage.setScene(scene);
        stage.show();
    }

    public void stockOnClick(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../../view/Stock/stock.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root,1280,679);
        stage.setScene(scene);
        stage.show();
    }

    public void billsOnClick(ActionEvent actionEvent) throws Exception{
        root = FXMLLoader.load(getClass().getResource("../../view/Purchase_Entry/purchase-entry.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root,1280,679);
        stage.setScene(scene);
        stage.show();
    }
    public void customersOnClick(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../../view/Customers/customers.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root,1280,679);
        stage.setScene(scene);
        stage.show();
    }
    public void suppliersOnClick(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../../view/Suppliers/suppliers.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root,1280,679);
        stage.setScene(scene);
        stage.show();
    }


    //          SLIDER BUTTONS              //

    public void logOutOnClick(ActionEvent event) throws IOException {
        Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm logout");
        alert.setHeaderText(null);
        alert.setContentText("Continue logging out?");
        Optional<ButtonType> action =alert.showAndWait();

        if (action.get()==ButtonType.OK){
            Parent root = FXMLLoader.load(getClass().getResource("../../view/Login/login.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root,1280,679);
            stage.setScene(scene);
            stage.show();
        }

    }
    public void exitOnClick(ActionEvent event) {
        Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm exit");
        alert.setHeaderText(null);
        alert.setContentText("Do you really want to exit?");
        Optional<ButtonType> action =alert.showAndWait();

        if (action.get()==ButtonType.OK){
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    public void homeOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../view/Dashboard/dashboard.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,1280,679);
        stage.setScene(scene);
        stage.show();
    }

    public void creditsOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../view/Credits/credits.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,1280,679);
        stage.setScene(scene);
        stage.show();
    }

//           FUNCTIONALITIES     //

    private ObservableList<Invoice> getInvoices() {
        ObservableList<Invoice> list = FXCollections.observableArrayList();
        Connection c = DBUtils.getConnection();
        String sqlQuery = "select * from invoices";
        Statement st;
        ResultSet rs;
        try {
            st = c.createStatement();
            rs = st.executeQuery(sqlQuery);
            Invoice invoice;

            while(rs.next()){
                invoice = new Invoice(rs.getInt("id"),
                        rs.getString("supplier"),
                        rs.getString("date_of_purchase"));

                list.add(invoice);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return list;
    }

    public void updateInvoices(){
        invoicesTableView.setItems(getInvoices());
    }

    public void addInvoiceOnClick(ActionEvent actionEvent) throws IOException {
        Stage window = HelperMethods.openWindow("Purchase_Entry/add-invoice.fxml",
                "Add Invoice");
        window.setOnHidden((e) -> {
            updateInvoices();
        });
    }

    public void editInvoiceOnclick(ActionEvent actionEvent) {
    }

    public void deleteInvoiceOnClick(ActionEvent actionEvent) {
    }

    public void addProductOnClick(ActionEvent actionEvent) throws IOException {
        Stage window = HelperMethods.openWindow("Purchase_Entry/select-product.fxml", "Select product");

    }

    public void editProductOnClick(ActionEvent actionEvent) {
    }

    public void deleteProductOnClick(ActionEvent actionEvent) {
    }


}
