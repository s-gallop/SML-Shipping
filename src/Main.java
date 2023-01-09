import UI.SplashScreen;
import database.DatabaseConnectionHandler;
import model.ShippingOffice;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        SplashScreen splashScreen = new SplashScreen();
        try {
            // Make SplashScreen appear for 5.7 seconds (one iteration length of IRDSplash.gif)
            Thread.sleep(5700);
            splashScreen.dispose();
        } catch (Exception e) {
            e.printStackTrace();
            // if splash fails, just move on
        }


        DatabaseConnectionHandler db = new DatabaseConnectionHandler();

        UI.GUI system = new UI.GUI(db);

    }
}
