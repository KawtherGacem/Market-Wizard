package controller.login;

import app.utils.DBUtils;
import com.sun.prism.paint.ImagePattern;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.*;

public class LoginController implements Initializable {

    //    slide menu items
    @FXML
    public Circle imageCircle ;

    @FXML public Pane openSliderPane;
    @FXML public Pane closeSliderPane;
    @FXML public ImageView openSliderImage;
    @FXML public AnchorPane slider;
    @FXML public AnchorPane x;
//    slide menu items

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private Button LogInBtn;



    public void initialize(URL location, ResourceBundle resources) {

//              slide menu items (there is an error here in the url)
//        Image im = new Image("src/view/images/logo-circle.png", false);
//        imageCircle.setFill(new ImagePattern(im));

    //                  SLIDER                             //
    slider.setTranslateX(-255);
    openSliderPane.setVisible(true);
    closeSliderPane.setVisible(false);

    openSliderImage.setOnMouseClicked(event -> {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.2));
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

    //                      SLIDER BUTTONS                  //


        //                      SLIDER BUTTONS                  //

}
    //                      SLIDER BUTTONS                  //
    public void creditsOnClick(ActionEvent event){

    }

    public void exitOnClick(ActionEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    //                      SLIDER BUTTONS                  //
    public void logInOnClick (ActionEvent event) throws IOException {
    if (logIn().equals("success")) {
        Parent root = FXMLLoader.load(getClass().getResource("../../view/Dashboard/dashboard.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,1280,679);
        stage.setScene(scene);
        stage.show();
    }
    }


    private String logIn() {

        String status = "success";
        String username = usernameTextField.getText().trim();
        String password = passwordTextField.getText();
        if(username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Empty credentials");
            errorLabel.setTextFill(BLUE);
            status = "Error";
        } else {
            String sql = "SELECT * FROM users Where username = ? and password = ?";
            try (Connection c = DBUtils.getConnection();){
                PreparedStatement preparedStatement = c.prepareStatement(sql);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    errorLabel.setText("Enter correct username/password");
                    errorLabel.setTextFill(BLUE);
                    status = "Error";
                } else {
                    errorLabel.setText("Login Successful..Redirecting..");
                    errorLabel.setTextFill(GREEN);
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
        }

        return status;
    }

    public void signUpOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../view/Login/sign-up.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,1280,679);
        stage.setScene(scene);
        stage.show();
    }
}
