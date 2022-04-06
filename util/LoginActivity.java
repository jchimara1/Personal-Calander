package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;

/**
 *
 * class that creates a LoginActivity
 */
public class LoginActivity {   private static final String FILENAME = "login_activity.txt";

    public LoginActivity() {}


    // Saves all login activity

    /**
     * class that creates a login_activity.txt
     * @param username to be written after the user logs in
     * @param success written after a successful login
     */
    public static void log (String username, boolean success) {
        try (FileWriter newattempt = new FileWriter(FILENAME, true);
             BufferedWriter wnewattempt = new BufferedWriter(newattempt);
             PrintWriter pwVariable = new PrintWriter(wnewattempt)) {
            pwVariable.println(ZonedDateTime.now() + " " + username + (success ? " Success" : " Failure"));
        } catch (IOException e) {
            System.out.println("Logg Error: " + e.getMessage());
        }
    }
}
