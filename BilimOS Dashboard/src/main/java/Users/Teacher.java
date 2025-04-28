package Users;
import Classes.*;

import java.util.Date;
import java.util.List;


import java.util.ArrayList;

public class Teacher extends User {
    private List<Course> courses;
    private List<Notification> notifications;


    public Teacher(String id, String name, String email, Date dob) {
        super(id, name, email, dob);
        this.courses = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }


    public List<Notification> getNotifications() {
        return notifications;
    }
    public List<Course> getCourses() {
        return courses;
    }
    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

}