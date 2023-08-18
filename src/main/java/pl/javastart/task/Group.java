package pl.javastart.task;

import java.util.*;

class Group {
    private String code;
    private String name;
    private Lecturer lecturer;
    private List<Student> students = new ArrayList<>();
    private Map<Integer, Double> grades = new HashMap<>();

    public Group(String code, String name, Lecturer lecturer) {
        this.code = code;
        this.name = name;
        this.lecturer = lecturer;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        if (students.contains(student)) {
            System.out.printf("Student o indeksie %d jest już w grupie %s", student.getIndex(), this.code);
        } else {
            students.add(student);
        }
    }

    public boolean hasStudent(Student student) {
        return students.contains(student);
    }

    public void addGrade(int studentIndex, double grade) {
        grades.put(studentIndex, grade);
    }

    public boolean hasGrade(int studentIndex) {
        return grades.containsKey(studentIndex);
    }

    public double getGrade(int studentIndex) {
        return grades.getOrDefault(studentIndex, 0.0);
    }

    public Map<Integer, Double> getGrades() {
        return grades;
    }
}
