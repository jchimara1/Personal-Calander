package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * justicechimara
 *  method to make switching between scenes much easier
 */
public class LoadScene
{
    /**
     *
     * @param stage for the the page your switching to
     * @param name for the the page your switching to
     * @param title for the the page your switching to
     */
    final public void loadPage(Stage stage, String name, String title)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource(name));
            stage.setTitle(title);
            stage.setScene( new Scene(root));
            stage.show();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     *
     * @param stage for the the page your switching to
     * @param name for the the page your switching to
     */
    final public void loadPage(Stage stage, String name)
    {
        this.loadPage(stage, name, "");
    }
}