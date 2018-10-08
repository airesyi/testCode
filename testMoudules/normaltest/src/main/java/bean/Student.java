package bean;

import java.util.ArrayList;
import java.util.List;

public class Student implements Cloneable {
    private String name;
    private int score;

    private List<Student> studentList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int hashCode() {
        return score;
    }

    @Override
    public String toString() {
        return name;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    @Override
    public Student clone() {
        Student clone = null;
        try {
            clone = (Student) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);  // won't happen
        }

        return clone;
    }
}
