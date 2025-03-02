package org.example.srsp;


//import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FindTOTable {
   /* private static final String pdf = "src/main/resources/2023grand.pdf";
    private static final String url = "jdbc:postgresql://localhost:5433/postgres";
    private static final String user = "postgres";
    private static final String passw  = "user";


    public static void saveStudentToDB(Student student){
        String sqlRequest = "Insert into pdf_table(IIN, name,score,uniCode) values (?,?,?,?)";

        try(Connection con = DriverManager.getConnection(url,user,passw);
        PreparedStatement preparedStatement  =con.prepareStatement(sqlRequest)){

            preparedStatement.setString(1,student.getIIN());
            preparedStatement.setString(2,student.getStudentName());
            preparedStatement.setInt(3,student.getScore());
            preparedStatement.setInt(4,student.getUniversityCode());

            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static List<Student> parseTable(String txt ){

        String regex = "(\\d+)\\s*\\|\\s*([A-Za-zА-Яа-я]+\\s[A-Za-zА-Яа-я]+)\\s*\\|\\s*(\\d+)\\s*\\|\\s*(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(txt);

        List<Student> students = new ArrayList<>();
        while(matcher.find()){
            String IIN = matcher.group(1);
            String studentName = matcher.group(2);
            int score = Integer.parseInt(matcher.group(3));
            int universityCode = Integer.parseInt(matcher.group(4));


            students.add(new Student (IIN,studentName,score,universityCode));
        }
        return students;


    }
    public static String extracteTextToPdf(String path ){
        try {
            PDDocument pdDocument = Loader.loadPDF(new File(path));
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            return pdfTextStripper.getText(pdDocument);

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {

        String pdfText = extracteTextToPdf(pdf);
        if(pdfText == null || pdfText.isEmpty()){
            System.out.println("Pdf is empty");
            return;
        }


        List<Student> students = parseTable(pdfText);


        if(students.isEmpty()){
            System.out.println("student not found");
        }
        else {
            for(Student student: students){
                saveStudentToDB(student);
            }
        }*/

    //}
}
