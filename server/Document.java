package server;

import java.io.Serializable;

public class Document implements Serializable {
    private String name;
    private double grade;
    private double income;
    private String faculty;
    private boolean isValid;
    private int type;

    public Document(String name, double grade, double income, String faculty, boolean isValid, int type) {
        this.name = name;
        this.grade = grade;
        this.income = income;
        this.faculty = faculty;
        this.isValid = isValid;
        this.type = type;
    }

    // -всеки документ е невалитен по default и тоя метод го прави валиден
    public void setValid() {
        isValid = true;
    }

    public boolean isValid() {
        return isValid;
    }
}
