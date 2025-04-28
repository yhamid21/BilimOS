package Classes;

public class Grade {
    private String assignment;
    private String course;
    private int score;

    public Grade(String assignment, String course, int score) {
        this.assignment = assignment;
        this.course = course;
        this.score = score;

    }

    public String getAssignment() {
        return assignment;
    }

    public String getCourse() {
        return course;
    }

    public int getScore() {
        return score;
    }
}
