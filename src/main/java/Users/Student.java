package Users;
import Classes.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Student extends User {
    private List<Assignment> assignments;
    private List<Grade> grades;
    private List<Notification> notifications;

    public Student(String id, String name, String email, Date dob, String password) {
        super(id, name, email, dob, password);
        this.assignments = new ArrayList<>();
        this.grades = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public List<Grade> getGradesByCourse(String courseName) {
        return grades.stream()
                .filter(g -> g.getCourse().equalsIgnoreCase(courseName))
                .collect(Collectors.toList());
    }
}