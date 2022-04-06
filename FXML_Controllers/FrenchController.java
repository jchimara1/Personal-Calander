package FXML_Controllers;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.*;

/**
 * Created by justicechimara
 *
 *  creates the french login controller
 */
public class FrenchController implements Initializable {


    @FXML
    public Label yourloc;
    @FXML
    private TextField Username;

    @FXML
    private PasswordField Password;

    /**
     *  Initializing Location
     * @param url initialize
     * @param rb initialize
     */
    public void initialize(URL url, ResourceBundle rb) {
        ZoneId location = ZoneId.systemDefault();
        yourloc.setText("Votre emplacement est " + location);
    }

    /**
     * handle the login and the event of a wrong password or username
     * @param event handle the login
     * @throws SQLException if wrong password or username
     */
    @FXML
    private void LoginAPP(ActionEvent event) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(Queries.getUserData(Username.getText(), Password.getText()));

        if (resultSet.next() && Username.getText().equals(resultSet.getString(2)) && Username.getText().equals(resultSet.getString(3))) {
            ((((Button) event.getSource()).getScene().getWindow())).hide();
            GlobalObject.user.populateUser(resultSet);
            LoginActivity.log(Username.getText(), true);
            System.out.println("Check Point : " + GlobalObject.user);
            new LoadScene().loadPage(new Stage(), FxmlNames.APPOINTMENT, FxmlNames.APPOINTMENT_TITLE);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connexion refusée");
            alert.setHeaderText("Nom d'utilisateur ou mot de passe invalide");
            alert.setContentText("(Soit votre nom d'utilisateur ou votre mot de passe est incorrect)");
            alert.showAndWait();
        }
    }
}


