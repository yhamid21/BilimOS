package Classes;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        setTitle("Login - Learning Management System");
        setSize(370, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));
        panel.setBackground(new Color(50, 52, 60));

        JLabel title = new JLabel("Login");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(200, 200, 200));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        emailField.setBorder(BorderFactory.createTitledBorder("Email"));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));

        JButton submit = new JButton("Login");
        submit.setBackground(new Color(83, 109, 254));
        submit.setForeground(Color.WHITE);
        submit.setFocusPainted(false);
        submit.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        submit.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel msgLabel = new JLabel("");
        msgLabel.setForeground(new Color(230,70,70));
        msgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(emailField);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 18)));
        panel.add(submit);
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(msgLabel);

        submit.addActionListener(e -> {
            String email = emailField.getText().trim().toLowerCase();
            String password = new String(passwordField.getPassword());
            UserManager.UserSessionEntry user = UserManager.authenticate(email, password);
            if (user != null) {
                msgLabel.setText("Welcome, " + user.name + " (" + user.type + ")");
                msgLabel.setForeground(new Color(90, 180, 90));
                Timer t = new Timer(1200, evt -> dispose());
                t.setRepeats(false); t.start();
            } else {
                msgLabel.setText("Invalid email or password.");
                msgLabel.setForeground(new Color(230,70,70));
            }
        });

        setContentPane(panel);
    }
}