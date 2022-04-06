
import util.DatabaseConnection;
import javafx.application.Application;
import javafx.stage.Stage;
import util.FxmlNames;
import util.LoadScene;


import java.text.ParseException;
import java.util.Locale;

public class Main extends Application {
    /**
     *
     * @param args Get Connection
     * @throws ParseException if error occurs
     */
    public static void main(String[] args) throws ParseException {
        DatabaseConnection.openConnection();
        launch(args);
        DatabaseConnection.closeConnection();
        System.out.println();

    }

    /**
     *
     * @param stage Start the Application and switch to either the french controller or english controller
     * @throws Exception if error occurs
     */
    public void start(Stage stage) throws Exception {

        Locale locale = Locale.getDefault();
        String locales = locale.toString();
        String fxml = (locales.contains("en") ? FxmlNames.LOGIN_ENGLISH : FxmlNames.LOGIN_FRENCH);
        String title = (locales.contains("en") ? FxmlNames.LOGIN_ENGLISH_TITLE : FxmlNames.LOGIN_FRENCH_TITLE);
        new LoadScene().loadPage(stage, fxml, title);
    }
}
