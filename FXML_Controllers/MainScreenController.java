package FXML_Controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import util.FxmlNames;
import util.LoadScene;

/**
 * Created by Justice Chimara
 * Creates the Main Screen Controller
 */
public class MainScreenController {
    /**
     * Method to move to Appointment View method
     * @param event appointment
     */
    public void Appointment(ActionEvent event) {
        ((((Button) event.getSource()).getScene().getWindow())).hide();
        new LoadScene().loadPage(new Stage(), FxmlNames.APPOINTMENT, FxmlNames.APPOINTMENT_TITLE);
    }
    /**
     * Customer controller method to move between scenes
     * @param event customer
     */
    public void Customer(ActionEvent event) {
        ((((Button) event.getSource()).getScene().getWindow())).hide();
        new LoadScene().loadPage(new Stage(), FxmlNames.CUSTOMER, FxmlNames.CUSTOMER_TITLE);
    }
    /**
     * Method to move to Reports View method
     * @param event reports
     */
    public void Reports(ActionEvent event) {
        ((((Button) event.getSource()).getScene().getWindow())).hide();
        new LoadScene().loadPage(new Stage(), FxmlNames.REPORTS, FxmlNames.REPORTS_TITLE);
    }
}
