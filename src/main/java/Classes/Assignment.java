package Classes;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Assignment {
    private String title;
    private String course;
    private LocalDate dueDate;
    private List<Content> contents;


    public Assignment(String title, String course, LocalDate dueDate) {
        this.title = title;
        this.course = course;
        this.dueDate = dueDate;
        this.contents = new ArrayList<>();

    }


    public List<Content> getContents() {
        return contents;
    }
    public String getTitle() {
        return title;
    }

    public String getCourse() {
        return course;
    }


    public LocalDate getDueDate() {
        return dueDate;
    }
    public void addContent(Content content) {
        contents.add(content);
    }
}
