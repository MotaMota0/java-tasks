package org.example.srsp;

public class Student {
    private String IIN;  // Изменил long -> String
    private String studentName;
    private int score;
    private int universityCode;

    public Student() {
    }

    public Student(String IIN, String studentName, int score, int universityCode) {
        this.IIN = IIN;
        this.studentName = studentName;
        this.score = score;
        this.universityCode = universityCode;
    }

    public String getIIN() {
        return IIN;
    }

    public void setIIN(String IIN) {
        this.IIN = IIN;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getUniversityCode() {
        return universityCode;
    }

    public void setUniversityCode(int universityCode) {
        this.universityCode = universityCode;
    }

    @Override
    public String toString() {
        return "Student{" +
                "IIN='" + IIN + '\'' +
                ", studentName='" + studentName + '\'' +
                ", score=" + score +
                ", universityCode=" + universityCode +
                '}';
    }
}
