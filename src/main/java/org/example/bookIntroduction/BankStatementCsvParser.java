package org.example.bookIntroduction;

import com.aspose.pdf.operators.Do;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BankStatementCsvParser implements BankStatementParser{

  private static final DateTimeFormatter DATA_PATTERN = DateTimeFormatter.ofPattern("dd-MM-yyyyy");

  @Override
  public BankTransaction parseFrom(final String line){
      final String[] columns = line.split(",");

      final LocalDate date = LocalDate.parse(columns[0],DATA_PATTERN);

      final double amount = Double.parseDouble(columns[1]);
      final String description = columns[2];

      return new BankTransaction(date,amount,description);
  }


  @Override
    public List<BankTransaction> parseLinesFrom(final List<String> lines) {
        final List<BankTransaction> bankTransactionList = new ArrayList<>();

        for(final String line : lines){
            bankTransactionList.add(parseFrom(line));
        }

        return bankTransactionList;

    }


}
