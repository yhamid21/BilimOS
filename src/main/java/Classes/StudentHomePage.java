package Classes;

import Users.Student;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class StudentHomePage extends JFrame {
    public StudentHomePage(Student student, List<Course> enrolledCourses) {
        setTitle("Home Page - Student Account");
        setSize(880, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.35);

        // LEFT: Courses panel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createTitledBorder("Your Courses"));
        for (Course c : enrolledCourses) {
            JButton btn = new JButton(c.getName());
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            // Placeholder for course button click
            leftPanel.add(btn);
            leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        splitPane.setLeftComponent(new JScrollPane(leftPanel));

        // RIGHT: Account info panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Account Information"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        addInfoLabel(rightPanel, "Name: ", student.getName());
        addInfoLabel(rightPanel, "Student ID: ", student.getId());
       // addInfoLabel(rightPanel, "Date of Birth: ", sdf.format(student.getDob()));
        addInfoLabel(rightPanel, "Date of Birth: ", "placeholder");
        addInfoLabel(rightPanel, "Email: ", student.getEmail());
        // Optionally: addInfoLabel(rightPanel, "Password: ", student.getPassword()); // Not recommended for display

        splitPane.setRightComponent(rightPanel);

        getContentPane().add(splitPane);
    }

    private void addInfoLabel(JPanel panel, String label, String value) {
        JLabel lbl = new JLabel(label + value);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lbl.setBorder(BorderFactory.createEmptyBorder(10, 5, 0, 5));
        panel.add(lbl);
    }
}