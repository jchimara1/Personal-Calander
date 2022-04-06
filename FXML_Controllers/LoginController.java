package FXML_Controllers;

import util.DatabaseConnection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * creates the login controller class for this project
 */
public class LoginController implements Initializable {
    // references the username fxID
    @FXML
    private TextField username;
    // references the  location fxID
    @FXML
    public Label location;
    // references the  password fxID
    @FXML
    private PasswordField password;

    /**
     * Initializes Location
     * @param url Initializes
     * @param rb Initializes Location
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        ZoneId currentZone = ZoneId.of(TimeZone.getDefault().getID());
        location.setText("Your Location is "+ currentZone);
    }

    /**handle the login and the event of a wrong password or username
     *
     * @param event login action
     * @throws SQLException if violated
     * @throws IOException if violated
     */
    public void login(ActionEvent event) throws SQLException, IOException {
        Connection connection = DatabaseConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(Queries.getUserData(username.getText(),password.getText()));

        if (resultSet.next() && username.getText().equals(resultSet.getString(2)) && password.getText().equals(resultSet.getString(3))) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../views/MainScreenView.fxml")));
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(root, 900, 600));
            CustomerController login = new CustomerController();
            GlobalObject.user.populateUser(resultSet);
            LoginActivity.log(username.getText(),true);
            login.alertAppointments();
        }else{
            LoginActivity.log(username.getText(),false);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Denied");
            alert.setHeaderText("Invalid Username or Password");
            alert.setContentText("(Either your username or Password is incorrect)");
            alert.showAndWait();
        }
    }
}
