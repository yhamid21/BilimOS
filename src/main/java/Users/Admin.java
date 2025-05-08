package Users;

import Classes.Course;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Admin extends User {

    private List<Course> createdCourses;
    private List<Student> createdStudents;
    private List<Teacher> createdTeachers;

    public Admin(String id, String name, String email, Date dob, String password) {
        super(id, name, email, dob, password);
        this.createdCourses = new ArrayList<>();
        this.createdStudents = new ArrayList<>();
        this.createdTeachers = new ArrayList<>();
    }

    public Student createStudent(String id, String name, String email, Date dob, String password) {
        Student student = new Student(id, name, email, dob, password);
        createdStudents.add(student);
        return student;
    }

    public Teacher createTeacher(String id, String name, String email, Date dob, String password) {
        Teacher teacher = new Teacher(id, name, email, dob, password);
        createdTeachers.add(teacher);
        return teacher;
    }

    public Course createCourse(String id, String name, String teacherId) {
        Course course = new Course(id, name, teacherId);
        createdCourses.add(course);
        return course;
    }

    public List<Course> getCreatedCourses() {
        return createdCourses;
    }

    public List<Student> getCreatedStudents() {
        return createdStudents;
    }

    public List<Teacher> getCreatedTeachers() {
        return createdTeachers;
    }
}
