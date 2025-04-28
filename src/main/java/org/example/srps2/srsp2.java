package org.example.srps2;

import com.opencsv.CSVWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.example.srsp.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import technology.tabula.ObjectExtractor;
import technology.tabula.Page;
import technology.tabula.Table;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class srsp2 {

    private static final Logger logger = LoggerFactory.getLogger(srsp2.class);
    private static final String url = "jdbc:postgresql://localhost:5433/postgres";
    private static final String user = "postgres";
    private static final String passw = "user";

    public static void main(String[] args) throws Exception {
        File pdf = new File("src/main/resources/2023grand.pdf");

        try (PDDocument document = PDDocument.load(pdf)) {


            ExecutorService executer = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            List<Runnable> tasks = new ArrayList<>();

            for (int pageNum = 1; pageNum <= document.getNumberOfPages(); pageNum++) {
                final int currentPage = pageNum;

                tasks.add(() -> {
                    PDFTextStripper stripper = null;
                    try {
                        stripper = new PDFTextStripper();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    stripper.setStartPage(currentPage);
                    stripper.setEndPage(currentPage);

                    String text = null;
                    try {
                        text = stripper.getText(document);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                    Pattern pattern = Pattern.compile("(\\d+)\\s+(\\d{9})\\s+([\\p{L}\\s\\-]+?)\\s+(\\d+)\\s+(\\d{3})", Pattern.MULTILINE);

                    Matcher matcher = pattern.matcher(text);

                    while (matcher.find()) {
                        String number = matcher.group(1);
                        String ictNumber = matcher.group(2);

                        String fullName = matcher.group(3).replaceAll("\\s+", " ").trim();

                        int totalScore = Integer.parseInt(matcher.group(4));

                        int universityCode = Integer.parseInt(matcher.group(5));
                        System.out.println("Number: " + number + ", ICT: " + ictNumber + ", Name: " + fullName + ", Score: " + totalScore + ", Code: " + universityCode);


                        logger.info("Extracted : Number:{}, ICT :{}, Name: {}, score:{}, Code:{}", number,ictNumber,fullName,totalScore,universityCode);
                        //saveStudentToDB(new Applicant(ictNumber,fullName,totalScore,universityCode));
                       try (Connection con = DriverManager.getConnection(url, user, passw)) {
                            saveStudentToDB(new Applicant(ictNumber, fullName, totalScore, universityCode), con);
                        } catch (SQLException e) {
                           logger.warn("Failed to parse record: {}",currentPage);
                            e.printStackTrace();
                        }
                    }
                });
            }
            for(Runnable task : tasks){
                executer.submit(task);

            }

            executer.shutdown();
            try {
                executer.awaitTermination(Long.MAX_VALUE,TimeUnit.SECONDS);


            } catch (InterruptedException e) {
                executer.shutdownNow();
            }



        }



    }

    private static void saveStudentToDB(Applicant student, Connection connection) {
        String checkQuery = "SELECT COUNT(*) FROM pdf_table WHERE IIN = ?";

        String insertQuery = "INSERT INTO pdf_table (IIN, name, score, uniCode) VALUES (?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, user, passw)) {
            // Проверяем, существует ли студент с таким IIN
            try (PreparedStatement checkStmt = con.prepareStatement(checkQuery)) {
                checkStmt.setString(1, student.getIctNumber());
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("Студент с IIN " + student.getIctNumber() + " уже существует. Пропускаем.");
                    return;
                }
            }

            // Добавляем нового студента
            try (PreparedStatement insertStmt = con.prepareStatement(insertQuery)) {
                insertStmt.setString(1, student.getIctNumber());
                insertStmt.setString(2, student.getFullname());
                insertStmt.setInt(3, student.getTotalScore());
                insertStmt.setInt(4, student.getUnivercyCode());

                insertStmt.executeUpdate();
                System.out.println("Студент " + student.getFullname() + " добавлен в базу.");
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при сохранении студента " + student.getFullname());
            e.printStackTrace();
        }
    }
       /* try (PDDocument document = PDDocument.load(pdf);
             CSVWriter csvWriter = new CSVWriter(new FileWriter(csvOutput))) {
            ObjectExtractor oe = new ObjectExtractor(document);
            SpreadsheetExtractionAlgorithm algt = new SpreadsheetExtractionAlgorithm();

            // Создаем пул потоков
            ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            List<Runnable> tasks = new ArrayList<>();

            // Создаем задачи для каждой страницы
            for (int pageNum = 1; pageNum <= document.getNumberOfPages(); pageNum++) {
                final int currentPage = pageNum;
                tasks.add(() -> {
                    try {
                        Page page = oe.extract(currentPage);
                        List<Table> tables = algt.extract(page);



                            for (Table table : tables) {
                                System.out.println("Tables on page: " + currentPage);
                                // Записываем заголовок таблицы (опционально)
                                csvWriter.writeNext(new String[]{"Number", "ICT Number", "Full Name", "Total Score", "University Code"});

                                for (List<technology.tabula.RectangularTextContainer> row : table.getRows()) {
                                    List<String> rowData = new ArrayList<>();
                                    for (technology.tabula.RectangularTextContainer cell : row) {
                                        String cellText = cell.getText().trim();
                                        System.out.print(cellText + "\t");
                                        rowData.add(cellText);
                                    }
                                    System.out.println();
                                    // Записываем строку в CSV
                                    csvWriter.writeNext(rowData.toArray(new String[0]));
                                }
                                System.out.println("---");
                                // Добавляем пустую строку между таблицами в CSV

                            }
                        }
                    } catch (Exception e) {
                        System.err.println("Error on page " + currentPage + ": " + e.getMessage());
                    }
                });
            }

            // Запускаем задачи
            for (Runnable task : tasks) {
                executorService.submit(task);
            }

            // Завершаем пул потоков
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
            }

            oe.close();
        } catch (IOException e) {
            throw new RuntimeException("Error processing PDF: " + e.getMessage(), e);
        }*/


}