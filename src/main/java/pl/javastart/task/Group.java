package pl.javastart.task;

import java.util.Arrays;

class Group {
    private String code;
    private String name;
    private int lecturerId;

    public Group(String code, String name, int lecturerId) {
        this.code = code;
        this.name = name;
        this.lecturerId = lecturerId;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getLecturerId() {
        return lecturerId;
    }
}
