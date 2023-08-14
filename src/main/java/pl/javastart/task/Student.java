package pl.javastart.task;

class Student extends Person {
    private int index;
    private String firstName;
    private String lastName;

    public Student(int index, String firstName, String lastName) {
        super(firstName, lastName);
        this.index = index;

    }

    public int getIndex() {
        return index;
    }

}
