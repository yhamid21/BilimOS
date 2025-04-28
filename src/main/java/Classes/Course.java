package Classes;
import Users.*;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String id;
    private String name;
    private String teacherId;
    private List<Student> students;
    private List<Content> contents;

    public Course(String id, String name, String teacherId) {
        this.id = id;
        this.name = name;
        this.teacherId = teacherId;
        this.students = new ArrayList<>();
        this.contents = new ArrayList<>();

    }

    public void enrollStudent(Student student) {
        students.add(student);
    }
    public void addContent(Content content) { contents.add(content);}

    public List<Student> getStudents() {
        return students;
    }
    public List<Content> getContents() {
        return contents;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTeacherId() {
        return teacherId;
    }
}