package org.example.srps2;

public class Applicant {
    private int number;
    private String ictNumber;
    private String fullname;
    private int totalScore;
    private String univercyCode;
    private String proffession;

    public Applicant() {
    }

    public Applicant(String ictNumber, String fullname, int totalScore, String univercyCode) {

        this.ictNumber = ictNumber;
        this.fullname = fullname;
        this.totalScore = totalScore;
        this.univercyCode = univercyCode;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getIctNumber() {
        return ictNumber;
    }

    public void setIctNumber(String ictNumber) {
        this.ictNumber = ictNumber;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public String getUnivercyCode() {
        return univercyCode;
    }

    public void setUnivercyCode(String univercyCode) {
        this.univercyCode = univercyCode;
    }

    public String getProffession() {
        return proffession;
    }

    public void setProffession(String proffession) {
        this.proffession = proffession;
    }
}
