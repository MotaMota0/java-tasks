package org.example.bookIntroduction;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class exmplBook1 {

    private static final String RESOURCES ="src/main/resources/memory.csv";

    /*public static void main(final String... args) throws IOException {

        final Path path  = Path.of(RESOURCES + args[0]);
        final List<String> lines = Files.readAllLines(path);


        double total = 0d;

        for(String line : lines){
            final String[] columns = line.split(",");
            final double amount = Double.parseDouble(columns[1]);
            total += amount;

        }
        System.out.println("Total transactioln"+total );
    }*/
    public static void main(String[] args)  throws  IOException{
        final Path path  = Path.of(RESOURCES );
        final List<String> lines = Files.readAllLines(path);


        double total = 0d;
        final DateTimeFormatter DATA_PATTERN = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for(final String  line :lines ){
            final String [] columns = line.split(",");
            final LocalDate date = LocalDate.parse(columns[0], DATA_PATTERN);
            if(date.getMonth() == Month.JANUARY){
                final double amount = Double.parseDouble(columns[1]);
                total += amount;
            }
        }
        System.out.println(total);

    }

}
