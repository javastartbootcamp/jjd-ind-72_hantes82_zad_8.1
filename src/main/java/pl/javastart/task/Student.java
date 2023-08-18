package pl.javastart.task;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return index == student.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }
}
