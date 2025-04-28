package Classes;

import Users.Student;
import Users.Teacher;

public class AnnouncementManager {

    public static void notifyStudent(Student student, String message) {
        student.addNotification(new Notification(message));
    }

    public static void notifyTeacher(Teacher teacher, String message) {
        teacher.addNotification(new Notification(message));
    }
}
