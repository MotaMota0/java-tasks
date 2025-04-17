package org.example.trash;

import com.aspose.pdf.*;
import com.aspose.pdf.Page;
import org.apache.pdfbox.pdmodel.PDDocument;
import technology.tabula.*;



import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class examplePdf {
    public static void main(String[] args) {
        // Укажите путь к PDF-файлу
        String pdfPath = "src/main/resources/2023grand.pdf";

        // Извлекаем данные
        List<String[]> extractedData = extractTablesFromPDF(pdfPath);

        // Сохраняем в БД
        //saveToDatabase(extractedData);

        if(extractedData != null){
            System.out.println("Not empty");
            for(String[] row : extractedData){
                System.out.println(String.join(" | ",row));
            }
        }

            System.out.println("empty");




    }


    public static List<String[]> extractTablesFromPDF(String pdfPath) {
        List<String[]> tableData = new ArrayList<>();

        // Загружаем PDF-документ
        Document pdfDocument = new Document(pdfPath);

        for (Page page : pdfDocument.getPages()) {
            // 🟢 Извлекаем заголовки (можно адаптировать)
            /*TextFragmentAbsorber absorber = new TextFragmentAbsorber(".*"); // Регулярка для всех текстов
            page.accept(absorber);*/

            /*String header = "";
            for (TextFragment text : absorber.getTextFragments()) {
                if (text.getText().matches("[A-Za-zА-Яа-я\\s]+")) { // Если текст похож на заголовок
                    header = text.getText();
                    break;
                }
            }*/

            // 🟢 Ищем таблицы
            TableAbsorber tableAbsorber = new TableAbsorber();
            tableAbsorber.visit(page);

            for (AbsorbedTable table : tableAbsorber.getTableList()) {
                for (AbsorbedRow row : table.getRowList()) {
                    List<String> rowData = new ArrayList<>();
                   // rowData.add(header); // Добавляем заголовок в первую колонку
                    for (AbsorbedCell cell : row.getCellList()) {
                        rowData.add(cell.getTextFragments().get_Item(1).getText());
                    }
                    tableData.add(rowData.toArray(new String[0]));
                }
            }
        }

        return tableData;
    }

    // 📌 Метод для сохранения данных в БД
    /*public static void saveToDatabase(List<String[]> tableData) {
        String url = "jdbc:postgresql://localhost:5432/mydb";
        String user = "postgres";
        String password = "password";

        String sql = "INSERT INTO applicants (header, column1, column2, column3) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (String[] row : tableData) {
                pstmt.setString(1, row[0]);  // Заголовок
                pstmt.setString(2, row.length > 1 ? row[1] : "");
                pstmt.setString(3, row.length > 2 ? row[2] : "");
                pstmt.setString(4, row.length > 3 ? row[3] : "");

                pstmt.addBatch();
            }

            pstmt.executeBatch();
            System.out.println("✅ Данные успешно загружены в БД");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}


    /*public static void main(String[] args) throws InterruptedException {
        try {
            File pdfFile = new File("src/main/resources/2023grand.pdf");
            PDDocument document = PDDocument.load(pdfFile);
            ObjectExtractor extractor = new ObjectExtractor(document);
            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();

            for (PageIterator it = extractor.extract(); it.hasNext(); ) {
                Page page = it.next();
                List<Table> tables = sea.extract(page);

                for (Table table : tables) {
                    processTable(table);
                }
            }
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void processTable(Table table) throws InterruptedException {
        List<List<String>> processedRows = new ArrayList<>();
        List<String> currentRow = new ArrayList<>();
        int expectedColumns = 5; // №, ИКТ, ФИО, Баллы, ВУЗ

        for (List<RectangularTextContainer> row : table.getRows()) {
            List<String> rowData = new ArrayList<>();
            for (RectangularTextContainer cell : row) {
                rowData.add(cell.getText().trim());
            }

            // Skip completely empty rows
            if (rowData.stream().allMatch(String::isEmpty)) {
                continue;
            }

            // Handle incomplete rows (merge with the previous row)
            if (!currentRow.isEmpty() && rowData.size() < expectedColumns) {
                // Merge rowData into currentRow
                int mergeIndex = currentRow.size();
                for (String cell : rowData) {
                    if (mergeIndex < expectedColumns) {
                        currentRow.set(mergeIndex, currentRow.get(mergeIndex) + " " + cell.trim());
                        mergeIndex++;
                    } else {
                        // If there are extra cells, append them to the end
                        currentRow.add(cell.trim());
                    }
                }
            } else {
                // If the current row is complete, save it and start a new row
                if (!currentRow.isEmpty() && currentRow.size() == expectedColumns) {
                    processedRows.add(new ArrayList<>(currentRow));
                }
                currentRow = rowData;
            }
        }

        // Add the last row if it's complete
        if (!currentRow.isEmpty() && currentRow.size() == expectedColumns) {
            processedRows.add(currentRow);
        }

        // Print the processed rows
        for (List<String> row : processedRows) {
            System.out.println(String.join(" | ", row));
        }
        Thread.sleep(1000);
    }*/


