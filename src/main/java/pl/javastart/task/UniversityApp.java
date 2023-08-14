package pl.javastart.task;

import java.util.*;

public class UniversityApp {
    private Map<Integer, Lecturer> lecturers = new HashMap<>();
    private Map<String, Group> groups = new HashMap<>();
    private Map<Integer, Student> students = new HashMap<>();

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
        if (!lecturers.containsKey(id)) {
            lecturers.put(id, new Lecturer(id, degree, firstName, lastName));
        } else {
            System.out.println("Prowadzący z id " + id + " już istnieje");
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
        if (!groups.containsKey(code)) {
            if (lecturers.containsKey(lecturerId)) {
                groups.put(code, new Group(code, name, lecturers.get(lecturerId)));
            } else {
                System.out.println("Prowadzący o id " + lecturerId + " nie istnieje");
            }
        } else {
            System.out.println("Grupa " + code + " już istnieje");
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
        if (groups.containsKey(groupCode)) {
            Student student = new Student(index, firstName, lastName);
            students.put(index, student);
            groups.get(groupCode).addStudent(student);
        } else {
            System.out.println("Grupa " + groupCode + " nie istnieje");
        }
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
        if (groups.containsKey(groupCode)) {
            Group group = groups.get(groupCode);
            System.out.println("Kod: " + group.getCode());
            System.out.println("Nazwa: " + group.getName());
            System.out.println("Prowadzący: " + group.getLecturer().getDegree() + " " + group.getLecturer().getFirstName()
                    + " " + group.getLecturer().getLastName());
            System.out.println("Uczestnicy:");
            for (Student student : group.getStudents()) {
                System.out.println(student.getIndex() + " " + student.getFirstName() + " " + student.getLastName());
            }
        } else {
            System.out.println("Grupa " + groupCode + " nie znaleziona");
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
        if (students.containsKey(studentIndex) && groups.containsKey(groupCode)) {
            Student student = students.get(studentIndex);
            Group group = groups.get(groupCode);
            if (group.hasStudent(student)) {
                if (!group.hasGrade(studentIndex)) {
                    group.addGrade(studentIndex, grade);
                } else {
                    System.out.println("Student o indeksie " + studentIndex + " ma już wystawioną ocenę dla grupy "
                            + groupCode);
                }
            } else {
                System.out.println("Student o indeksie " + studentIndex + " nie jest zapisany do grupy " + groupCode);
            }
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
        if (students.containsKey(index)) {
            Student student = students.get(index);
            for (Group group : groups.values()) {
                if (group.hasStudent(student)) {
                    if (group.hasGrade(index)) {
                        System.out.println(group.getName() + ": " + group.getGrade(index));
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
        if (groups.containsKey(groupCode)) {
            Group group = groups.get(groupCode);
            for (Map.Entry<Integer, Double> entry : group.getGrades().entrySet()) {
                Student student = students.get(entry.getKey());
                System.out.println(student.getIndex() + " " + student.getFirstName() + " " + student.getLastName() + ": " + entry.getValue());
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
        Set<Student> uniqueStudents = new HashSet<>(students.values());
        for (Student student : uniqueStudents) {
            System.out.println(student.getIndex() + " " + student.getFirstName() + " " + student.getLastName());
        }
    }
}
