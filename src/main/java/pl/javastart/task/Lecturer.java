package pl.javastart.task;

class Lecturer extends Person {
    private int id;
    private String degree;

    public Lecturer(int id, String degree, String firstName, String lastName) {
        super(firstName, lastName);
        this.id = id;
        this.degree = degree;

    }

    public int getId() {
        return id;
    }

    public String getDegree() {
        return degree;
    }

}