package UI;

import javax.swing.*;
import java.awt.*;


// Code is modelled off of tutorialspoint AND @ilovegregor from
// Project https://github.students.cs.ubc.ca/CPSC210-2020W-T1/project_p7v2b, credits goes to them
// https://www.tutorialspoint.com/how-can-we-implement-a-splash-screen-using-jwindow-in-java
public class SplashScreen extends JWindow {

    Image splashScreen;
    ImageIcon imageIcon;

    // Creates a new SplashScreen with provided gif
    public SplashScreen() {
        splashScreen = Toolkit.getDefaultToolkit().getImage("./extra/IRDSplash.gif");
        imageIcon = new ImageIcon(splashScreen);
        setSize(imageIcon.getIconWidth(),imageIcon.getIconHeight());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // locations
        int x = (screenSize.width - getSize().width) / 2;
        int y = (screenSize.height - getSize().height) / 2;
        setLocation(x,y);


        setVisible(true);
    }

    // MODIFIES: super
    // EFFECTS: Paint graphic (gif) onto JWindow
    // CONSTRAINT (Assumes): g MUST be provided splash screen image.
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(splashScreen, 0, 0, this);
    }

}
