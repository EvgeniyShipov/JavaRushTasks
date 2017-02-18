package com.javarush.task.task29.task2909.human;

import java.util.ArrayList;
import java.util.List;

public class University {
    private List<Student> students;
    private String name;
    private int age;

    public University(String name, int age) {
        this.students = new ArrayList<>();
        this.name = name;
        this.age = age;
    }

    public List<Student> getStudents() {
        return students;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Student getStudentWithAverageGrade(double averageGrade) {
        for (Student s: students) {
            if (s.getAverageGrade() == averageGrade) {
                return s;
            }
        }
        return null;
    }

    public Student getStudentWithMaxAverageGrade() {
        Student studentWithMaxAverageGrade = null;
        double maxAverageGrade = 0;
        for (Student s: students) {
            if (s.getAverageGrade() > maxAverageGrade) {
                maxAverageGrade = s.getAverageGrade();
                studentWithMaxAverageGrade = s;
            }
        }
        return studentWithMaxAverageGrade;
    }

    public Student getStudentWithMinAverageGrade() {
        Student studentWithMinAverageGrade = null;
        double minAverageGrade = Double.MAX_VALUE;
        for (Student s: students) {
            if (s.getAverageGrade() < minAverageGrade) {
                minAverageGrade = s.getAverageGrade();
                studentWithMinAverageGrade = s;
            }
        }
        return studentWithMinAverageGrade;
    }

    public void expel(Student student) {
        students.remove(student);
    }
}