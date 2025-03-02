package org.example.srsp;

import org.apache.pdfbox.pdmodel.PDDocument;
import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class examplePdf {
    public static void main(String[] args) {
        try {
            File pdfFile = new File("src/main/resources/2023grand.pdf");
            PDDocument document = PDDocument.load(pdfFile);
            ObjectExtractor extractor = new ObjectExtractor(document);
            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
            String currentHeader = ""; // Заголовок (профессия)

            for (PageIterator it = extractor.extract(); it.hasNext(); ) {
                Page page = it.next();
                List<Table> tables = sea.extract(page);

                // 1. Определяем заголовок (берем текст сверху таблицы)
                String pageText = page.getText().toString().trim();
                String[] lines = pageText.split("\n");
                for (String line : lines) {
                    if (line.matches("^[А-ЯЁ][а-яёA-Za-z\\s\\-]+$")) { // Заголовки (например, профессии)
                        currentHeader = line.trim();
                    }
                }

                // 2. Обрабатываем таблицу
                for (Table table : tables) {
                    List<List<String>> processedRows = processTable(table);
                    printTable(processedRows, currentHeader);
                }
            }
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<List<String>> processTable(Table table) {
        List<List<String>> processedRows = new ArrayList<>();
        int expectedColumns = 5; // Количество столбцов (№, ИКТ, ФИО, Баллы, ВУЗ)
        List<String> currentRow = new ArrayList<>();

        for (List<RectangularTextContainer> row : table.getRows()) {
            List<String> rowData = new ArrayList<>();
            for (RectangularTextContainer cell : row) {
                rowData.add(cell.getText().trim());
            }

            if (rowData.isEmpty()) continue;

            if (!currentRow.isEmpty() && rowData.size() < expectedColumns) {
                // Если строка неполная, дополняем предыдущую строку
                for (int i = 0; i < rowData.size(); i++) {
                    int mergeIndex = i + (expectedColumns - rowData.size());
                    if (mergeIndex < currentRow.size()) {
                        currentRow.set(mergeIndex, currentRow.get(mergeIndex) + " " + rowData.get(i));
                    }
                }
            } else {
                if (!currentRow.isEmpty() && currentRow.size() == expectedColumns) {
                    processedRows.add(new ArrayList<>(currentRow));
                }
                currentRow = rowData;
            }
        }

        if (!currentRow.isEmpty() && currentRow.size() == expectedColumns) {
            processedRows.add(currentRow);
        }
        return processedRows;
    }

    public static void printTable(List<List<String>> rows, String sectionTitle) {
        System.out.println("\n🔹 Профессия: " + sectionTitle);
        System.out.println("---------------------------------------------------");
        for (List<String> row : rows) {
            System.out.printf("%-5s | %-10s | %-40s | %-5s | %-5s%n",
                    row.get(0), row.get(1), row.get(2), row.get(3), row.get(4));
        }
        System.out.println("---------------------------------------------------\n");
    }
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


