package Classes;

import Users.*;
import com.formdev.flatlaf.FlatDarkLaf;

import java.time.LocalDate;
import java.util.Date;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainApp {
    public static ImageIcon appIcon;

    // Method to set icon for a JFrame
    public static void setAppIcon(JFrame frame) {
        if (appIcon != null) {
            try {
                frame.setIconImage(appIcon.getImage());
                System.out.println("Icon set for " + frame.getClass().getSimpleName());
            } catch (Exception e) {
                System.err.println("Failed to set icon for " + frame.getClass().getSimpleName() + ": " + e.getMessage());
            }
        } else {
            System.err.println("Cannot set icon for " + frame.getClass().getSimpleName() + " - appIcon is null");
        }
    }

    // Method to set the dock icon on macOS
    private static void setDockIcon(Image image) {
        try {
            // Check if we're on Mac OS
            if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                // Try using Taskbar class (Java 9+)
                try {
                    Class<?> taskbarClass = Class.forName("java.awt.Taskbar");
                    Object taskbar = taskbarClass.getMethod("getTaskbar").invoke(null);
                    taskbarClass.getMethod("setIconImage", Image.class).invoke(taskbar, image);
                    System.out.println("Dock icon set using Taskbar API");
                } catch (ClassNotFoundException e) {
                    // Fallback to older Apple-specific API for Java 8 and below
                    try {
                        Class<?> applicationClass = Class.forName("com.apple.eawt.Application");
                        Object application = applicationClass.getMethod("getApplication").invoke(null);
                        applicationClass.getMethod("setDockIconImage", Image.class).invoke(application, image);
                        System.out.println("Dock icon set using Apple API");
                    } catch (Exception ex) {
                        System.err.println("Failed to set dock icon using Apple API: " + ex.getMessage());
                    }
                } catch (Exception e) {
                    System.err.println("Failed to set dock icon using Taskbar API: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Error setting dock icon: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        FlatDarkLaf.setup();

        // Load application icon
        try {
            // Try multiple ways to load the icon
            java.net.URL iconURL = MainApp.class.getClassLoader().getResource("BilimOS_Black_Logo.png");
            if (iconURL != null) {
                appIcon = new ImageIcon(iconURL);
                System.out.println("Icon loaded successfully from ClassLoader");
            } else {
                // Try alternative method
                java.io.File iconFile = new java.io.File("src/main/resources/BilimOS_Black_Logo.png");
                if (iconFile.exists()) {
                    appIcon = new ImageIcon(iconFile.getAbsolutePath());
                    System.out.println("Icon loaded successfully from file path");
                } else {
                    System.err.println("Icon file not found at: " + iconFile.getAbsolutePath());
                }
            }

            if (appIcon != null) {
                // Set the dock icon for macOS
                setDockIcon(appIcon.getImage());

                // Set the icon for all JOptionPane dialogs
                UIManager.put("OptionPane.icon", appIcon);
                System.out.println("Icon set for OptionPane");
            }
        } catch (Exception e) {
            System.err.println("Failed to load application icon: " + e.getMessage());
            e.printStackTrace();
        }

        javax.swing.SwingUtilities.invokeLater(() -> {
            WelcomePage welcomePage = new WelcomePage();
            welcomePage.setVisible(true);
        });
    }
}
