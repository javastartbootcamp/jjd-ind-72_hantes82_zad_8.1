package pl.javastart.task;

class Student extends Person {
    protected int index;
    protected String groupCode;

    public Student(int index, String groupCode, String firstName, String lastName) {
        super(firstName, lastName);
        this.index = index;
        this.groupCode = groupCode;
    }

    public int getIndex() {
        return index;
    }

    public String getGroupCode() {
        return groupCode;
    }

}
