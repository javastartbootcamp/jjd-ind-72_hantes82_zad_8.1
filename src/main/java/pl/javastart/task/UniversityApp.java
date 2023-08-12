package pl.javastart.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UniversityApp {
    Lecturer[] lecturers = {};
    Group[] groups = {};
    Student[] students = {};
    Grade[] grades = {};

    int lecturersCounter = 0;

    int studentsCounter = 0;

    int groupsCounter = 0;

    int gradesCounter = 0;

    /**
     * Tworzy prowadzącego zajęcia.
     * W przypadku gdy prowadzący z zadanym id już istnieje, wyświetlany jest komunikat:
     * "Prowadzący z id [id_prowadzacego] już istnieje"
     *
     * @param id        - unikalny identyfikator prowadzącego
     * @param degree    - stopień naukowy prowadzącego
     * @param firstName - imię prowadzącego
     * @param lastName  - nazwisko prowadzącego
     */

    public void createLecturer(int id, String degree, String firstName, String lastName) {
        if (findLecturerById(id) == null) {
            if (lecturersCounter == lecturers.length) {
                lecturers = Arrays.copyOf(lecturers, lecturers.length + 10);
            }
            Lecturer lecturer = new Lecturer(id, degree, firstName, lastName);
            lecturers[lecturersCounter] = lecturer;
            lecturersCounter++;
        } else {
            System.out.printf("Prowadzący z id %d już istnieje\n", id);
        }
    }

    /**
     * Tworzy grupę zajęciową.
     * W przypadku gdy grupa z zadanym kodem już istnieje, wyświetla się komunikat:
     * "Grupa [kod grupy] już istnieje"
     * W przypadku gdy prowadzący ze wskazanym id nie istnieje wyświetla się komunikat:
     * "Prowadzący o id [id prowadzacego] nie istnieje"
     *
     * @param code       - unikalny kod grupy
     * @param name       - nazwa przedmiotu (np. "Podstawy programowania")
     * @param lecturerId - identyfikator prowadzącego. Musi zostać wcześniej utworzony za pomocą metody {@link #createLecturer(int,
     *                   String, String, String)}
     */

    public void createGroup(String code, String name, int lecturerId) {

        if (findLecturerById(lecturerId) == null) {
            System.out.printf("Prowadzący o id %d nie istnieje\n", lecturerId);
            return;
        }
        if (findGroupById(code) != null) {
            System.out.printf("Grupa %s już istnieje\n", code);
        } else {
            if (groupsCounter == groups.length) {
                groups = Arrays.copyOf(groups, groups.length + 10);
            }
            Group group = new Group(code, name, lecturerId);
            groups[groupsCounter] = group;
            groupsCounter++;
        }
    }

    /**
     * Dodaje studenta do grupy zajęciowej.
     * W przypadku gdy grupa zajęciowa nie istnieje wyświetlany jest komunikat:
     * "Grupa [kod grupy] nie istnieje
     *
     * @param index     - unikalny numer indeksu studenta
     * @param groupCode - kod grupy utworzonej wcześniej za pomocą {@link #createGroup(String, String, int)}
     * @param firstName - imię studenta
     * @param lastName  - nazwisko studenta
     */
    public void addStudentToGroup(int index, String groupCode, String firstName, String lastName) {
        Group group = findGroupById(groupCode);
        if (group == null) {
            System.out.printf("Grupa %s nie istnieje\n", groupCode);
            return;
        }
        if (findStudentInGroup(index, groupCode) != null) {
            System.out.printf("Student o indeksie %d jest już w grupie pp-2022\n", index);
            return;
        }
        if (studentsCounter == students.length) {
            students = Arrays.copyOf(students, students.length + 10);
        }
        Student student = new Student(index, groupCode, firstName, lastName);
        students[studentsCounter] = student;
        studentsCounter++;
    }

    /**
     * Wyświetla informacje o grupie w zadanym formacie.
     * Oczekiwany format:
     * Kod: [kod_grupy]
     * Nazwa: [nazwa przedmiotu]
     * Prowadzący: [stopień naukowy] [imię] [nazwisko]
     * Uczestnicy:
     * [nr indeksu] [imie] [nazwisko]
     * [nr indeksu] [imie] [nazwisko]
     * [nr indeksu] [imie] [nazwisko]
     * W przypadku gdy grupa nie istnieje, wyświetlany jest komunikat w postaci: "Grupa [kod] nie znaleziona"
     *
     * @param groupCode - kod grupy, dla której wyświetlić informacje
     */
    public void printGroupInfo(String groupCode) {
        if (findGroupById(groupCode) != null) {
            Group group = findGroupById(groupCode);

            System.out.printf("Kod: %s\n", groupCode);
            System.out.printf("Nazwa: %s\n", group.getName());
            Lecturer lecturer = findLecturerById(group.getLecturerId());
            System.out.printf("Prowadzący: %s %s %s\n", lecturer.getDegree(), lecturer.getFirstName(),
                    lecturer.getLastName());
            System.out.println("Uczestnicy:");
            for (int i = 0; i < students.length; i++) {
                if (students[i] != null) {
                    if (students[i].getGroupCode().equals(groupCode)) {
                        System.out.printf("%d %s %s\n", students[i].getIndex(), students[i].getFirstName(),
                                students[i].getLastName());
                    }
                }
            }
        } else {
            System.out.printf("Grupa %s nie znaleziona", groupCode);
        }
    }

    /**
     * Dodaje ocenę końcową dla wskazanego studenta i grupy.
     * Student musi być wcześniej zapisany do grupy za pomocą {@link #addStudentToGroup(int, String, String, String)}
     * W przypadku, gdy grupa o wskazanym kodzie nie istnieje, wyświetlany jest komunikat postaci:
     * "Grupa pp-2022 nie istnieje"
     * W przypadku gdy student nie jest zapisany do grupy, wyświetlany jest komunikat w
     * postaci: "Student o indeksie 179128 nie jest zapisany do grupy pp-2022"
     * W przypadku gdy ocena końcowa już istnieje, wyświetlany jest komunikat w postaci:
     * "Student o indeksie 179128 ma już wystawioną ocenę dla grupy pp-2022"
     *
     * @param studentIndex - numer indeksu studenta
     * @param groupCode    - kod grupy
     * @param grade        - ocena
     */

    public void addGrade(int studentIndex, String groupCode, double grade) {
        if (findGroupById(groupCode) == null) {
            System.out.printf("Grupa %s nie istnieje\n", groupCode);
            return;
        }

        if (findStudentInGroup(studentIndex, groupCode) == null) {
            System.out.printf("Student o indeksie %d nie jest zapisany do grupy %s\n", studentIndex, groupCode);
            return;
        }

        if (findGradeByStudentAndGroup(studentIndex, groupCode)) {
            System.out.printf("Student o indeksie %d ma już wystawioną ocenę dla grupy %s\n", studentIndex, groupCode);
        } else {
            Group group = findGroupById(groupCode);
            Student student = findStudentById(studentIndex);
            if (gradesCounter == grades.length) {
                grades = Arrays.copyOf(grades, grades.length + 10);
            }
            Grade gradeNew = new Grade(grade, student, group);
            grades[gradesCounter] = gradeNew;
            gradesCounter++;
        }
    }

    /**
     * Wyświetla wszystkie oceny studenta.
     * Przykładowy wydruk:
     * Podstawy programowania: 5.0
     * Programowanie obiektowe: 5.5
     *
     * @param index - numer indesku studenta dla którego wyświetlić oceny
     */
    public void printGradesForStudent(int index) {
        if (findStudentById(index) != null) {
            for (int i = 0; i < grades.length; i++) {
                if (grades[i] != null) {
                    if (grades[i].getStudent().getIndex() == index) {
                        System.out.println(grades[i].getGroup().getName() + ": " + grades[i].getGrade());
                    }
                }
            }
        }
    }

    /**
     * Wyświetla oceny studentów dla wskazanej grupy.
     * Przykładowy wydruk:
     * 179128 Marcin Abacki: 5.0
     * 179234 Dawid Donald: 4.5
     * 189521 Anna Kowalska: 5.5
     *
     * @param groupCode - kod grupy, dla której wyświetlić oceny
     */
    public void printGradesForGroup(String groupCode) {
        if (findGroupById(groupCode) == null) {
            System.out.printf("Grupa %s nie istnieje", groupCode);
            return;
        }
        for (Grade grade : grades) {
            if (grade != null) {
                if (grade.getGroup().getCode() == groupCode) {
                    System.out.printf("%s %s %s: %.1f\n", groupCode, grade.getStudent().getFirstName(), grade.getStudent().getLastName(),
                            grade.getGrade());
                }
            }

        }
    }

    /**
     * Wyświetla wszystkich studentów. Każdy student powinien zostać wyświetlony tylko raz.
     * Każdy student drukowany jest w nowej linii w formacie [nr_indesku] [imie] [nazwisko]
     * Przykładowy wydruk:
     * 179128 Marcin Abacki
     * 179234 Dawid Donald
     * 189521 Anna Kowalska
     */
    public void printAllStudents() {
        //Mogłem od poczatku uzyc map i list ale zrobilem to tutaj przy poprawianiu kodu dopiero,
        // a nie chcialem juz nie chcialem przepisywac wszystkiego
        List<String> studentsList = new ArrayList<>();
        for (Student student : students) {
            if (student != null) {
                studentsList.add(student.getIndex() + " " + student.getFirstName() + " " +
                        student.getLastName());
            }
            for (String studentString : studentsList) {
                System.out.println(studentString);
            }

        }
    }

    public Lecturer findLecturerById(int lecturerId) {
        for (Lecturer lecturer : lecturers) {
            if (lecturer != null) {
                if (lecturer.getId() == lecturerId) {
                    return lecturer;
                }
            }
        }
        return null;
    }

    public Group findGroupById(String code) {
        for (Group group : groups) {
            if (group != null) {
                if (group.getCode().equals(code)) {
                    return group;
                }
            }
        }
        return null;
    }

    public Student findStudentById(int studentIndex) {
        for (Student student : students) {
            if (student.getIndex() == studentIndex) {
                return student;
            }
        }
        return null;
    }

    public Student findStudentInGroup(int studentIndex, String groupCode) {
        for (Student student : students) {
            if (student != null) {
                if (student.getIndex() == studentIndex && student.getGroupCode().equals(groupCode)) {
                    return student;
                }
            }

        }
        return null;
    }

    public boolean findGradeByStudentAndGroup(int studentIndex, String groupCode) {
        for (Grade grade : grades) {
            if (grade != null) {
                if (grade.getStudent().getIndex() == studentIndex && grade.getGroup().getCode().equals(groupCode)) {
                    return true;
                }
            }
        }
        return false;
    }
}