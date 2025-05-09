package Classes;

import javax.swing.*;
import java.awt.*;

public class WelcomePage extends JFrame {
    private JPanel mainPanel;
    private JButton loginButton;
    private JButton registerButton;

    public WelcomePage() {
        setTitle("BilimOS Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);

        // Set application icon
        MainApp.setAppIcon(this);

        // Outer background panel (keeps card centered)
        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBackground(new Color(36, 37, 42)); // or UIManager.getColor("Panel.background")

        // Card panel with shadow & rounded corners
        mainPanel = new RoundedPanel(25, new Color(50, 52, 60));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(35, 35, 30, 35));

        // Rich title
        JLabel welcomeLabel = new JLabel("Welcome to the BilimOS Student-Teacher Dashboard!");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 19));
        welcomeLabel.setForeground(new Color(220, 220, 220));

        // Tagline/subtitle
        JLabel tagline = new JLabel("<html><center>Changing education.<br> One school at a time.</center></html>");
        tagline.setAlignmentX(Component.CENTER_ALIGNMENT);
        tagline.setFont(new Font("Arial", Font.PLAIN, 14));
        tagline.setForeground(new Color(160, 160, 160));

        // Buttons
        loginButton = createStyledButton("Login");
        registerButton = createStyledButton("Register");

        loginButton.addActionListener(e -> showLoginDialog());
        registerButton.addActionListener(e -> showRegisterDialog());

        mainPanel.add(welcomeLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 18)));
        mainPanel.add(tagline);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 32)));
        mainPanel.add(loginButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 16)));
        mainPanel.add(registerButton);


        // Center the card on the frame
        backgroundPanel.add(mainPanel, new GridBagConstraints());
        setContentPane(backgroundPanel);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setBackground(new Color(83, 109, 254));
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(220, 44));
        button.setMinimumSize(new Dimension(180, 40));
        button.setPreferredSize(new Dimension(200, 44));
        button.setBorder(BorderFactory.createEmptyBorder(9, 20, 9, 20));
        button.setBorder(BorderFactory.createLineBorder(new Color(83, 109, 254), 2, true));
        button.setOpaque(true);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(83, 109, 254), 1, true),
                BorderFactory.createEmptyBorder(9, 20, 9, 20)));
        return button;
    }

    static class RoundedPanel extends JPanel {
        private final int radius;
        private final Color background;

        public RoundedPanel(int radius, Color bg) {
            super();
            this.radius = radius;
            this.background = bg;
            setOpaque(false);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(background);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.fillRoundRect(5, getHeight() - 10, getWidth() - 10, 10, radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }



    private void showLoginDialog() {
        new LoginFrame(this).setVisible(true);
    }

    private void showRegisterDialog() {
        new RegisterFrame().setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        // Create and show the home page
        SwingUtilities.invokeLater(() -> {
            WelcomePage welcomePage = new WelcomePage();
            welcomePage.setVisible(true);
        });
    }
}
