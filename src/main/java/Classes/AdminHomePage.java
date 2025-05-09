package Classes;

import Users.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AdminHomePage extends JFrame {
    private Admin admin;
    private JTabbedPane tabbedPane;
    private JPanel homePanel;
    private JPanel userManagementPanel;
    private JPanel courseManagementPanel;
    private JComboBox<String> teacherComboBox;
    private JComboBox<String> studentComboBox;
    private JList<String> courseList;

    public AdminHomePage(Admin admin) {
        this.admin = admin;

        setTitle("Admin Dashboard");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set application icon
        MainApp.setAppIcon(this);

        // Create tabbed pane
        tabbedPane = new JTabbedPane();

        // Create panels for each tab
        createHomePanel();
        createUserManagementPanel();
        createCourseManagementPanel();

        // Add panels to tabbed pane
        tabbedPane.addTab("Home", homePanel);
        tabbedPane.addTab("User Management", userManagementPanel);
        tabbedPane.addTab("Course Management", courseManagementPanel);

        // Add tabbed pane to frame
        getContentPane().add(tabbedPane);
    }

    private void createUserManagementPanel() {
        userManagementPanel = new JPanel(new BorderLayout());

        // Create form panel for creating users
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createTitledBorder("Create New User"));

        // Form fields
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
        dobField.setBorder(BorderFactory.createTitledBorder("Date of Birth (yyyy-MM-dd)"));

        // User type selection
        JPanel typePanel = new JPanel();
        typePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        ButtonGroup typeGroup = new ButtonGroup();
        JRadioButton studentBtn = new JRadioButton("Student");
        JRadioButton teacherBtn = new JRadioButton("Teacher");

        studentBtn.setSelected(true);
        typeGroup.add(studentBtn);
        typeGroup.add(teacherBtn);
        typePanel.add(studentBtn);
        typePanel.add(teacherBtn);

        JButton createUserBtn = new JButton("Create User");
        createUserBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel statusLabel = new JLabel("");
        statusLabel.setForeground(Color.RED);

        formPanel.add(nameField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(emailField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(passwordField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(dobField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(typePanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(createUserBtn);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(statusLabel);

        // Create list panels for displaying users
        JPanel listsPanel = new JPanel(new GridLayout(1, 2));

        // Students list
        DefaultListModel<String> studentListModel = new DefaultListModel<>();
        JList<String> studentList = new JList<>(studentListModel);
        JScrollPane studentScrollPane = new JScrollPane(studentList);
        studentScrollPane.setBorder(BorderFactory.createTitledBorder("Students"));

        // Teachers list
        DefaultListModel<String> teacherListModel = new DefaultListModel<>();
        JList<String> teacherList = new JList<>(teacherListModel);
        JScrollPane teacherScrollPane = new JScrollPane(teacherList);
        teacherScrollPane.setBorder(BorderFactory.createTitledBorder("Teachers"));

        listsPanel.add(studentScrollPane);
        listsPanel.add(teacherScrollPane);

        // Add action listener to create user button
        createUserBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim().toLowerCase();
            String password = new String(passwordField.getPassword());
            String dobStr = dobField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || dobStr.isEmpty()) {
                statusLabel.setText("All fields are required");
                return;
            }

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dob = dateFormat.parse(dobStr);
                String id = UUID.randomUUID().toString();

                if (studentBtn.isSelected()) {
                    // Create student
                    boolean success = UserManager.registerUser(name, email, password, dobStr, UserManager.UserType.STUDENT);
                    if (success) {
                        studentListModel.addElement(name + " (" + email + ")");
                        statusLabel.setText("Student created successfully");
                        statusLabel.setForeground(new Color(0, 150, 0));
                        // Refresh student dropdown in course management panel
                        refreshStudentDropdown();
                    } else {
                        statusLabel.setText("Failed to create student. Email may already exist.");
                        statusLabel.setForeground(Color.RED);
                    }
                } else {
                    // Create teacher
                    boolean success = UserManager.registerUser(name, email, password, dobStr, UserManager.UserType.TEACHER);
                    if (success) {
                        teacherListModel.addElement(name + " (" + email + ")");
                        statusLabel.setText("Teacher created successfully");
                        statusLabel.setForeground(new Color(0, 150, 0));
                        // Refresh teacher dropdown in course management panel
                        refreshTeacherDropdown();
                    } else {
                        statusLabel.setText("Failed to create teacher. Email may already exist.");
                        statusLabel.setForeground(Color.RED);
                    }
                }

                // Clear form fields
                nameField.setText("");
                emailField.setText("");
                passwordField.setText("");
                dobField.setText("");

            } catch (ParseException ex) {
                statusLabel.setText("Invalid date format. Use yyyy-MM-dd");
                statusLabel.setForeground(Color.RED);
            }
        });

        // Load existing users
        loadExistingUsers(studentListModel, teacherListModel);

        // Add components to panel
        userManagementPanel.add(formPanel, BorderLayout.NORTH);
        userManagementPanel.add(listsPanel, BorderLayout.CENTER);
    }

    private void loadExistingUsers(DefaultListModel<String> studentListModel, DefaultListModel<String> teacherListModel) {
        // Load students
        List<Student> students = UserManager.getAllStudents();
        for (Student student : students) {
            studentListModel.addElement(student.getName() + " (" + student.getEmail() + ")");
        }

        // Load teachers
        List<Teacher> teachers = UserManager.getAllTeachers();
        for (Teacher teacher : teachers) {
            teacherListModel.addElement(teacher.getName() + " (" + teacher.getEmail() + ")");
        }
    }

    private JPanel enrolledStudentsPanel;
    private DefaultListModel<String> enrolledStudentsListModel;

    private void createCourseManagementPanel() {
        courseManagementPanel = new JPanel(new BorderLayout());

        // Create form panel for creating courses
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createTitledBorder("Create New Course"));

        // Form fields
        JTextField courseNameField = new JTextField();
        courseNameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        courseNameField.setBorder(BorderFactory.createTitledBorder("Course Name"));

        // Teacher selection
        teacherComboBox = new JComboBox<>();
        teacherComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        teacherComboBox.setBorder(BorderFactory.createTitledBorder("Assign Teacher"));

        // Load teachers into combo box
        refreshTeacherDropdown();

        JButton createCourseBtn = new JButton("Create Course");
        createCourseBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel courseStatusLabel = new JLabel("");
        courseStatusLabel.setForeground(Color.RED);

        formPanel.add(courseNameField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(teacherComboBox);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(createCourseBtn);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(courseStatusLabel);

        // Create panel for course list and student enrollment
        JPanel coursePanel = new JPanel(new BorderLayout());

        // Course list
        DefaultListModel<String> courseListModel = new DefaultListModel<>();
        courseList = new JList<>(courseListModel);
        JScrollPane courseScrollPane = new JScrollPane(courseList);
        courseScrollPane.setBorder(BorderFactory.createTitledBorder("Courses"));

        // Create panel for enrolled students
        enrolledStudentsPanel = new JPanel(new BorderLayout());
        enrolledStudentsPanel.setBorder(BorderFactory.createTitledBorder("Enrolled Students"));
        enrolledStudentsListModel = new DefaultListModel<>();
        JList<String> enrolledStudentsList = new JList<>(enrolledStudentsListModel);
        JScrollPane enrolledStudentsScrollPane = new JScrollPane(enrolledStudentsList);
        enrolledStudentsPanel.add(enrolledStudentsScrollPane, BorderLayout.CENTER);

        // Add selection listener to course list
        courseList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = courseList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String courseName = courseListModel.getElementAt(selectedIndex);
                    String courseNameOnly = courseName.substring(0, courseName.indexOf(" (Teacher:"));

                    // Find the course by name
                    Course selectedCourse = null;
                    for (Course course : CourseRegistry.getAllCourses()) {
                        if (course.getName().equals(courseNameOnly)) {
                            selectedCourse = course;
                            break;
                        }
                    }

                    // Update enrolled students list
                    if (selectedCourse != null) {
                        updateEnrolledStudentsList(selectedCourse);
                    }
                }
            }
        });

        // Student enrollment panel
        JPanel enrollmentPanel = new JPanel();
        enrollmentPanel.setLayout(new BoxLayout(enrollmentPanel, BoxLayout.Y_AXIS));
        enrollmentPanel.setBorder(BorderFactory.createTitledBorder("Enroll Students"));

        studentComboBox = new JComboBox<>();
        studentComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        studentComboBox.setBorder(BorderFactory.createTitledBorder("Select Student"));

        // Load students into combo box
        refreshStudentDropdown();

        JComboBox<String> enrollCourseComboBox = new JComboBox<>();
        enrollCourseComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        enrollCourseComboBox.setBorder(BorderFactory.createTitledBorder("Select Course"));

        JButton enrollBtn = new JButton("Enroll Student");
        enrollBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel enrollStatusLabel = new JLabel("");
        enrollStatusLabel.setForeground(Color.RED);

        enrollmentPanel.add(studentComboBox);
        enrollmentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        enrollmentPanel.add(enrollCourseComboBox);
        enrollmentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        enrollmentPanel.add(enrollBtn);
        enrollmentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        enrollmentPanel.add(enrollStatusLabel);

        // Create a split pane for course list and enrolled students
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, courseScrollPane, enrolledStudentsPanel);
        splitPane.setResizeWeight(0.5);

        coursePanel.add(splitPane, BorderLayout.CENTER);
        coursePanel.add(enrollmentPanel, BorderLayout.SOUTH);

        // Add action listener to create course button
        createCourseBtn.addActionListener(e -> {
            String courseName = courseNameField.getText().trim();

            if (courseName.isEmpty() || teacherComboBox.getSelectedIndex() == -1) {
                courseStatusLabel.setText("Course name and teacher are required");
                courseStatusLabel.setForeground(Color.RED);
                return;
            }

            String teacherEmail = extractEmail(teacherComboBox.getSelectedItem().toString());
            Teacher teacher = (Teacher) UserManager.getUser(teacherEmail);

            if (teacher != null) {
                String courseId = UUID.randomUUID().toString();
                Course course = new Course(courseId, courseName, teacher.getId());
                CourseRegistry.addCourse(course);

                courseListModel.addElement(courseName + " (Teacher: " + teacher.getName() + ")");
                enrollCourseComboBox.addItem(courseName + " (Teacher: " + teacher.getName() + ")");

                courseStatusLabel.setText("Course created successfully");
                courseStatusLabel.setForeground(new Color(0, 150, 0));

                // Clear form field
                courseNameField.setText("");
            } else {
                courseStatusLabel.setText("Selected teacher not found");
                courseStatusLabel.setForeground(Color.RED);
            }
        });

        // Add action listener to enroll button
        enrollBtn.addActionListener(e -> {
            if (studentComboBox.getSelectedIndex() == -1 || enrollCourseComboBox.getSelectedIndex() == -1) {
                enrollStatusLabel.setText("Student and course are required");
                enrollStatusLabel.setForeground(Color.RED);
                return;
            }

            String studentEmail = extractEmail(studentComboBox.getSelectedItem().toString());
            Student student = (Student) UserManager.getUser(studentEmail);

            String courseName = enrollCourseComboBox.getSelectedItem().toString();
            String courseNameOnly = courseName.substring(0, courseName.indexOf(" (Teacher:"));

            // Find the course by name
            Course selectedCourse = null;
            for (Course course : CourseRegistry.getAllCourses()) {
                if (course.getName().equals(courseNameOnly)) {
                    selectedCourse = course;
                    break;
                }
            }

            if (student != null && selectedCourse != null) {
                // Check if student is already enrolled
                if (selectedCourse.isStudentEnrolled(student)) {
                    enrollStatusLabel.setText("This student is already enrolled in this course");
                    enrollStatusLabel.setForeground(Color.RED);
                } else {
                    selectedCourse.enrollStudent(student);
                    enrollStatusLabel.setText("Student enrolled successfully");
                    enrollStatusLabel.setForeground(new Color(0, 150, 0));

                    // Update enrolled students list if this course is currently selected
                    int selectedIndex = courseList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        String selectedCourseName = courseListModel.getElementAt(selectedIndex);
                        String selectedCourseNameOnly = selectedCourseName.substring(0, selectedCourseName.indexOf(" (Teacher:"));
                        if (selectedCourseNameOnly.equals(courseNameOnly)) {
                            updateEnrolledStudentsList(selectedCourse);
                        }
                    }
                }
            } else {
                enrollStatusLabel.setText("Student or course not found");
                enrollStatusLabel.setForeground(Color.RED);
            }
        });

        // Add components to panel
        courseManagementPanel.add(formPanel, BorderLayout.NORTH);
        courseManagementPanel.add(coursePanel, BorderLayout.CENTER);
    }

    private String extractEmail(String nameWithEmail) {
        int startIndex = nameWithEmail.indexOf("(") + 1;
        int endIndex = nameWithEmail.indexOf(")");
        return nameWithEmail.substring(startIndex, endIndex);
    }

    private void refreshTeacherDropdown() {
        teacherComboBox.removeAllItems();
        List<Teacher> teachers = UserManager.getAllTeachers();
        for (Teacher teacher : teachers) {
            teacherComboBox.addItem(teacher.getName() + " (" + teacher.getEmail() + ")");
        }
    }

    private void refreshStudentDropdown() {
        studentComboBox.removeAllItems();
        List<Student> students = UserManager.getAllStudents();
        for (Student student : students) {
            studentComboBox.addItem(student.getName() + " (" + student.getEmail() + ")");
        }
    }

    private void updateEnrolledStudentsList(Course course) {
        enrolledStudentsListModel.clear();
        List<Student> students = course.getStudents();
        if (students.isEmpty()) {
            enrolledStudentsListModel.addElement("No students enrolled");
        } else {
            for (Student student : students) {
                enrolledStudentsListModel.addElement(student.getName() + " (" + student.getEmail() + ")");
            }
        }
    }

    private void createHomePanel() {
        homePanel = new JPanel();
        homePanel.setLayout(new BorderLayout());

        // Create a split pane to divide the panel into left (info) and right (profile photo)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.7); // Left panel gets 70% of the space

        // Create panel for admin information
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Admin Information"));

        // Add admin information
        JLabel nameLabel = new JLabel("Name: " + admin.getName());
        JLabel idLabel = new JLabel("ID: " + admin.getId());
        JLabel emailLabel = new JLabel("Email: " + admin.getEmail());
        JLabel dobLabel = new JLabel("Date of Birth: " + admin.getDob());

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
                    admin.setProfilePicture(img);

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
            admin.setPassword(newPassword);

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
}
