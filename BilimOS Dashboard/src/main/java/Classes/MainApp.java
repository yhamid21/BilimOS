package Classes;

import Classes.*;
import Users.*;

import java.time.LocalDate;
import java.util.Date;

public class MainApp {
    public static void main(String[] args) {
        Admin admin = new Admin("A001", "Alice Admin", "alice@school.com", new Date());

        Student student1 = admin.createStudent("S001", "John Doe", "john@school.com", new Date());
        Student student2 = admin.createStudent("S002", "Jane Smith", "jane@school.com", new Date());

        Teacher teacher1 = admin.createTeacher("T001", "Mr. Brown", "brown@school.com", new Date());

        Course course1 = admin.createCourse("C001", "Introduction to Programming", teacher1.getId());

        course1.enrollStudent(student1);
        course1.enrollStudent(student2);

        Content syllabus = new Content("Syllabus.pdf", "pdf", "/files/introprog/Syllabus.pdf");
        course1.addContent(syllabus);

        Assignment assignment1 = new Assignment("Homework 1", course1.getName(), LocalDate.of(2025, 5, 5));
        Content assignmentFile = new Content("Homework1.docx", "docx", "/files/introprog/Homework1.docx");
        assignment1.addContent(assignmentFile);

        for (Student student : course1.getStudents()) {
            student.addAssignment(assignment1);
            AnnouncementManager.notifyStudent(student, "New assignment posted: " + assignment1.getTitle());
        }

        for (Student student : course1.getStudents()) {
            AnnouncementManager.notifyStudent(student, "Reminder: Midterm exam is next week!");
        }

        AnnouncementManager.notifyTeacher(teacher1, "You have been assigned to teach: " + course1.getName());

        AnnouncementManager.notifyTeacher(teacher1, student1.getName() + " submitted " + assignment1.getTitle());
        AnnouncementManager.notifyTeacher(teacher1, student2.getName() + " submitted " + assignment1.getTitle());

        Grade grade1 = new Grade(assignment1.getTitle(), course1.getName(), 95);
        Grade grade2 = new Grade(assignment1.getTitle(), course1.getName(), 88);

        student1.addGrade(grade1);
        student2.addGrade(grade2);

        AnnouncementManager.notifyStudent(student1, "You received a grade: " + grade1.getScore() + " on " + grade1.getAssignment());
        AnnouncementManager.notifyStudent(student2, "You received a grade: " + grade2.getScore() + " on " + grade2.getAssignment());

        System.out.println("=== Student Notifications ===");
        for (Notification n : student1.getNotifications()) {
            System.out.println(student1.getName() + ": " + n.getMessage() + " (" + n.getTimestamp() + ")");
        }
        for (Notification n : student2.getNotifications()) {
            System.out.println(student2.getName() + ": " + n.getMessage() + " (" + n.getTimestamp() + ")");
        }

        System.out.println("\n=== Teacher Notifications ===");
        for (Notification n : teacher1.getNotifications()) {
            System.out.println(teacher1.getName() + ": " + n.getMessage() + " (" + n.getTimestamp() + ")");
        }

        System.out.println("\n=== Course Content ===");
        for (Content c : course1.getContents()) {
            System.out.println("Course: " + course1.getName() + " Content: " + c.getFileName());
        }

        System.out.println("\n=== Assignment Content ===");
        for (Content c : assignment1.getContents()) {
            System.out.println("Assignment: " + assignment1.getTitle() + " Content: " + c.getFileName());
        }
    }
}
