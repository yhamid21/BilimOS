package Classes;

import Users.*;
import com.formdev.flatlaf.FlatDarkLaf;

import java.time.LocalDate;
import java.util.Date;
import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        FlatDarkLaf.setup();

        javax.swing.SwingUtilities.invokeLater(() -> {


            WelcomePage welcomePage = new WelcomePage();
            welcomePage.setVisible(true);
        });
    }
}