package org.example.srsp;

import com.aspose.pdf.Document;


import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import technology.tabula.*;


import technology.tabula.extractors.BasicExtractionAlgorithm;
import technology.tabula.extractors.ExtractionAlgorithm;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.print.Doc;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class pdf {

    private static final String url = "jdbc:postgresql://localhost:5433/postgres";
    private static final String user = "postgres";
    private static final String passw = "user";

    /* File file = new File("src/main/resources/2023grand.pdf");
     try(PDDocument  document = PDDocument.load(file)) {


         PDFTextStripperByArea stripper = new PDFTextStripperByArea();
         stripper.addRegion("table", new Rectangle(50, 100, 500,-1)); // координаты области таблицы
         stripper.extractRegions(document.getPage(0));
         String tableText = stripper.getTextForRegion("table");
         System.out.println(tableText);
     }catch (IOException e){
         e.printStackTrace();
     }
*/
    public static void main(String[] args) {
        String filePath = "src/main/resources/2023grand.pdf";

        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            PDDocument document = PDDocument.load(fis);
            ObjectExtractor extractor = new ObjectExtractor(document);
            BasicExtractionAlgorithm bea = new BasicExtractionAlgorithm(); // Алгоритм извлечения

            for (int i = 0; i < document.getNumberOfPages(); i++) {
                Page page = extractor.extract(i + 1);
                List<Table> tables = bea.extract(page); // Извлекаем таблицы

                for (Table table : tables) {
                    List<List<RectangularTextContainer>> rows = table.getRows();
                    for (List<RectangularTextContainer> row : rows) {
                        if (row.size() >= 5) { // Проверяем, что в строке минимум 5 ячейки
                            boolean hasEmptyValue = false;
                            StringBuilder rowText = new StringBuilder();
                            String[] values = new String[5];

                            for (int j = 0; j < 5; j++) { // Проверяем только первые 5 столбца
                                String cellText = row.get(j).getText().trim();
                                if (cellText.isEmpty()) {
                                    hasEmptyValue = true;
                                    break;
                                }
                                values[j] = cellText;
                                rowText.append(cellText).append(" | ");
                            }


                            if (!hasEmptyValue) {
                                try {
                                    int score = Integer.parseInt(values[3]);
                                    int uniCode = Integer.parseInt(values[4]);
                                    Student student = new Student(values[1], values[2], score, uniCode);
                                    saveStudentToDB(student);
                                } catch (NumberFormatException e) {
                                    System.err.println("Ошибка парсинга чисел: " + e.getMessage());
                                }
                            }
                        } else if (row.size() <= 4) {
                            continue;
                        }
                    }
                }
            }
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void saveStudentToDB(Student student) {
        String checkQuery = "SELECT COUNT(*) FROM pdf_table WHERE IIN = ?";
        String insertQuery = "INSERT INTO pdf_table (IIN, name, score, uniCode) VALUES (?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, user, passw)) {
            // Проверяем, существует ли студент с таким IIN
            try (PreparedStatement checkStmt = con.prepareStatement(checkQuery)) {
                checkStmt.setString(1, student.getIIN());
                var rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("Студент с IIN " + student.getIIN() + " уже существует. Пропускаем.");
                    return;
                }
            }

            // Добавляем нового студента
            try (PreparedStatement insertStmt = con.prepareStatement(insertQuery)) {
                insertStmt.setString(1, student.getIIN());
                insertStmt.setString(2, student.getStudentName());
                insertStmt.setInt(3, student.getScore());
                insertStmt.setInt(4, student.getUniversityCode());

                insertStmt.executeUpdate();
                System.out.println("Студент " + student.getStudentName() + " добавлен в базу.");
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при сохранении студента " + student.getStudentName());
            e.printStackTrace();
        }
    }


}
