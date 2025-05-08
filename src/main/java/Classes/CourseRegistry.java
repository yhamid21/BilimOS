package Classes;

import Users.Student;
import java.util.ArrayList;
import java.util.List;

public class CourseRegistry {
    private static List<Course> allCourses = new ArrayList<>();

    public static void addCourse(Course course) {
        allCourses.add(course);
    }

    public static List<Course> getEnrolledCourses(Student student) {
        List<Course> enrolled = new ArrayList<>();
        for (Course c : allCourses) {
            if (c.getStudents().contains(student)) {
                enrolled.add(c);
            }
        }
        return enrolled;
    }

    public static List<Course> getAllCourses() {
        return new ArrayList<>(allCourses);
    }
}
