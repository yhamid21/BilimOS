package Classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

public class RegisterFrame extends JFrame {
    public RegisterFrame() {
        setTitle("Register - Learning Management System");
        setSize(400, 430);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set application icon
        MainApp.setAppIcon(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        panel.setBackground(new Color(50, 52, 60));

        JLabel title = new JLabel("Create Account");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(new Color(210, 210, 210));

        JTextField nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        nameField.setBorder(BorderFactory.createTitledBorder("Name"));

        JTextField emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        emailField.setBorder(BorderFactory.createTitledBorder("Email"));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));

        JTextField dobField = new JTextField();
        dobField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        dobField.setBorder(BorderFactory.createTitledBorder("Date of Birth (yyyy-mm-dd)"));

        // User type selection
        JPanel typePanel = new JPanel();
        typePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        typePanel.setBackground(new Color(50, 52, 60));
        ButtonGroup typeGroup = new ButtonGroup();
        JRadioButton studentBtn = new JRadioButton("Student"), teacherBtn = new JRadioButton("Teacher"), adminBtn = new JRadioButton("Admin");
        JRadioButton[] allTypes = {studentBtn, teacherBtn, adminBtn};
        for (JRadioButton b : allTypes) {
            b.setBackground(new Color(50, 52, 60));
            typeGroup.add(b); typePanel.add(b);
        }
        studentBtn.setSelected(true);

        JButton submit = new JButton("Register");
        submit.setBackground(new Color(83, 109, 254));
        submit.setForeground(Color.WHITE);
        submit.setFocusPainted(false);
        submit.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        submit.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel msgLabel = new JLabel("");
        msgLabel.setForeground(new Color(250,90,90));
        msgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(nameField);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(emailField);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(dobField);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(typePanel);
        panel.add(Box.createRigidArea(new Dimension(0, 18)));
        panel.add(submit);
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(msgLabel);

        submit.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim().toLowerCase();
            String password = new String(passwordField.getPassword());
            String dob = dobField.getText().trim();

            UserManager.UserType userType = UserManager.UserType.STUDENT;
            if (teacherBtn.isSelected()) userType = UserManager.UserType.TEACHER;
            else if (adminBtn.isSelected()) userType = UserManager.UserType.ADMIN;

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || dob.isEmpty()) {
                msgLabel.setText("All fields required");
                return;
            }

            // Naive dob check:
            try { new SimpleDateFormat("yyyy-MM-dd").parse(dob); }
            catch (Exception x) { msgLabel.setText("DOB format must be yyyy-mm-dd."); return; }

            boolean ok = UserManager.registerUser(name, email, password, dob, userType);
            if (!ok) {
                msgLabel.setText("Email already registered!");
            } else {
                msgLabel.setForeground(new Color(90, 180, 90));
                msgLabel.setText("Registration successful!");
                Timer timer = new Timer(1450, evt -> dispose());
                timer.setRepeats(false); timer.start();
            }
        });

        setContentPane(panel);
    }
}
