package pl.javastart.task;

public class Grade {
    private double grade;
    private Student student;
    private Group group;

    public Grade(double grade, Student student, Group group) {
        this.grade = grade;
        this.student = student;
        this.group = group;
    }

    public double getGrade() {
        return grade;
    }

    public Student getStudent() {
        return student;
    }

    public Group getGroup() {
        return group;
    }
}
