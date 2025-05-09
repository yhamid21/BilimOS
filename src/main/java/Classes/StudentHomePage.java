package Classes;

import Users.Student;
import Users.Teacher;
import Users.User;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.List;

public class StudentHomePage extends JFrame {
    private Student student;
    private List<Course> enrolledCourses;
    private JTabbedPane tabbedPane;
    private JPanel homePanel;
    private JPanel coursesPanel;

    public StudentHomePage(Student student, List<Course> enrolledCourses) {
        this.student = student;
        this.enrolledCourses = enrolledCourses;

        setTitle("Home Page - Student Account");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set application icon
        MainApp.setAppIcon(this);

        // Create tabbed pane
        tabbedPane = new JTabbedPane();

        // Create panels for each tab
        createHomePanel();
        createCoursesPanel();

        // Add panels to tabbed pane
        tabbedPane.addTab("Home", homePanel);
        tabbedPane.addTab("Courses", coursesPanel);

        // Add tabbed pane to frame
        getContentPane().add(tabbedPane);
    }

    private void createHomePanel() {
        homePanel = new JPanel();
        homePanel.setLayout(new BorderLayout());

        // Create a split pane to divide the panel into left (info) and right (profile photo)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.7); // Left panel gets 70% of the space

        // Create panel for student information
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Student Information"));

        // Add student information
        JLabel nameLabel = new JLabel("Name: " + student.getName());
        JLabel idLabel = new JLabel("ID: " + student.getId());
        JLabel emailLabel = new JLabel("Email: " + student.getEmail());
        JLabel dobLabel = new JLabel("Date of Birth: " + student.getDob());

        // Style the labels
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        nameLabel.setFont(labelFont);
        idLabel.setFont(labelFont);
        emailLabel.setFont(labelFont);
        dobLabel.setFont(labelFont);

        // Add padding
        nameLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        idLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        emailLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        dobLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Add labels to info panel
        infoPanel.add(nameLabel);
        infoPanel.add(idLabel);
        infoPanel.add(emailLabel);
        infoPanel.add(dobLabel);

        // Create buttons
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(83, 109, 254));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.setBackground(new Color(83, 109, 254));
        changePasswordButton.setForeground(Color.WHITE);
        changePasswordButton.setFocusPainted(false);
        changePasswordButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Add action listener to logout button
        logoutButton.addActionListener(e -> {
            // Create and show welcome page
            WelcomePage welcomePage = new WelcomePage();
            welcomePage.setVisible(true);

            // Dispose of this frame
            dispose();
        });

        // Add action listener to change password button
        changePasswordButton.addActionListener(e -> showChangePasswordDialog());

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.add(changePasswordButton);
        buttonPanel.add(logoutButton);

        // Create a panel for the left side (info + buttons)
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(infoPanel, BorderLayout.CENTER);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Create panel for profile photo
        JPanel photoPanel = new JPanel();
        photoPanel.setLayout(new BoxLayout(photoPanel, BoxLayout.Y_AXIS));
        photoPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        photoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create a label to display the profile photo
        JLabel photoLabel = new JLabel();
        photoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        photoLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Load default profile picture
        try {
            ImageIcon defaultPhoto = new ImageIcon(getClass().getClassLoader().getResource("default_profile_picture.jpg"));
            // Scale the image to a reasonable size
            Image scaledImage = defaultPhoto.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
            photoLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception ex) {
            photoLabel.setText("No photo available");
            System.err.println("Error loading default profile picture: " + ex.getMessage());
        }

        // Create button to change profile photo
        JButton changePhotoButton = new JButton("Change Photo");
        changePhotoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        changePhotoButton.setMaximumSize(new Dimension(180, 30));

        // Add action listener to change photo button
        changePhotoButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "Image files", "jpg", "jpeg", "png", "gif"));

            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    // Load the selected image
                    java.io.File selectedFile = fileChooser.getSelectedFile();
                    BufferedImage img = javax.imageio.ImageIO.read(selectedFile);

                    // Update the user's profile picture
                    student.setProfilePicture(img);

                    // Convert BufferedImage to Image and scale it
                    Image image = img;
                    Image scaledImage = image.getScaledInstance(180, 180, Image.SCALE_SMOOTH);
                    photoLabel.setIcon(new ImageIcon(scaledImage));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, 
                            "Error loading image: " + ex.getMessage(), 
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add components to photo panel - position photo higher
        photoPanel.add(photoLabel);
        photoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        photoPanel.add(changePhotoButton);
        photoPanel.add(Box.createVerticalGlue());

        // Add panels to split pane
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(photoPanel);

        // Add split pane to home panel
        homePanel.add(splitPane, BorderLayout.CENTER);
    }

    private void createCoursesPanel() {
        coursesPanel = new JPanel(new BorderLayout());

        // Create a panel for the course list
        JPanel courseListPanel = new JPanel();
        courseListPanel.setLayout(new BoxLayout(courseListPanel, BoxLayout.Y_AXIS));
        courseListPanel.setBorder(BorderFactory.createTitledBorder("Your Courses"));

        // Add courses to the panel
        if (enrolledCourses.isEmpty()) {
            JLabel noCoursesLabel = new JLabel("You are not enrolled in any courses.");
            noCoursesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            courseListPanel.add(noCoursesLabel);
        } else {
            for (Course course : enrolledCourses) {
                // Create a panel for each course
                JPanel coursePanel = new JPanel();
                coursePanel.setLayout(new BoxLayout(coursePanel, BoxLayout.Y_AXIS));
                coursePanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(5, 5, 5, 5),
                        BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true)));
                coursePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

                // Course name
                JLabel courseNameLabel = new JLabel(course.getName());
                courseNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
                courseNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

                // Get teacher name
                String teacherId = course.getTeacherId();
                String teacherName = "Unknown";
                for (User user : UserManager.getUsersByType(UserManager.UserType.TEACHER)) {
                    if (user.getId().equals(teacherId)) {
                        teacherName = user.getName();
                        break;
                    }
                }

                // Teacher name
                JLabel teacherLabel = new JLabel("Teacher: " + teacherName);
                teacherLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                teacherLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

                // Buttons for course content
                JButton announcementsButton = new JButton("Announcements");
                announcementsButton.setAlignmentX(Component.LEFT_ALIGNMENT);
                announcementsButton.setMaximumSize(new Dimension(200, 30));

                JButton assignmentsButton = new JButton("Assignments");
                assignmentsButton.setAlignmentX(Component.LEFT_ALIGNMENT);
                assignmentsButton.setMaximumSize(new Dimension(200, 30));

                // Add placeholders for button actions
                announcementsButton.addActionListener(e -> {
                    JOptionPane.showMessageDialog(this, 
                            "Announcements feature coming soon!", 
                            "Coming Soon", JOptionPane.INFORMATION_MESSAGE);
                });

                assignmentsButton.addActionListener(e -> {
                    JOptionPane.showMessageDialog(this, 
                            "Assignments feature coming soon!", 
                            "Coming Soon", JOptionPane.INFORMATION_MESSAGE);
                });

                // Add components to course panel
                coursePanel.add(courseNameLabel);
                coursePanel.add(Box.createRigidArea(new Dimension(0, 5)));
                coursePanel.add(teacherLabel);
                coursePanel.add(Box.createRigidArea(new Dimension(0, 10)));

                // Create a panel for buttons
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                buttonPanel.add(announcementsButton);
                buttonPanel.add(assignmentsButton);

                coursePanel.add(buttonPanel);

                // Add course panel to course list panel
                courseListPanel.add(coursePanel);
                courseListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        // Add course list panel to a scroll pane
        JScrollPane scrollPane = new JScrollPane(courseListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Add scroll pane to courses panel
        coursesPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void showChangePasswordDialog() {
        // Create a dialog for changing password
        JDialog passwordDialog = new JDialog(this, "Change Password", true);
        passwordDialog.setSize(300, 200);
        passwordDialog.setLocationRelativeTo(this);

        // Create panel for dialog content
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Create password fields
        JPasswordField newPasswordField = new JPasswordField();
        newPasswordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        newPasswordField.setBorder(BorderFactory.createTitledBorder("New Password"));

        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        confirmPasswordField.setBorder(BorderFactory.createTitledBorder("Confirm New Password"));

        // Create buttons
        JButton saveButton = new JButton("Save");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Create label for status messages
        JLabel statusLabel = new JLabel("");
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusLabel.setForeground(Color.RED);

        // Add components to dialog panel
        dialogPanel.add(newPasswordField);
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        dialogPanel.add(confirmPasswordField);
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        dialogPanel.add(buttonPanel);
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        dialogPanel.add(statusLabel);

        // Add action listener to save button
        saveButton.addActionListener(e -> {
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (newPassword.isEmpty()) {
                statusLabel.setText("Password cannot be empty");
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                statusLabel.setText("Passwords do not match");
                return;
            }

            // Update the password
            student.setPassword(newPassword);

            // Close the dialog
            passwordDialog.dispose();

            // Show success message
            JOptionPane.showMessageDialog(this, 
                    "Password changed successfully", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        // Add action listener to cancel button
        cancelButton.addActionListener(e -> passwordDialog.dispose());

        // Set dialog content and show
        passwordDialog.setContentPane(dialogPanel);
        passwordDialog.setVisible(true);
    }

    private void addInfoLabel(JPanel panel, String label, String value) {
        JLabel lbl = new JLabel(label + value);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lbl.setBorder(BorderFactory.createEmptyBorder(10, 5, 0, 5));
        panel.add(lbl);
    }
}
