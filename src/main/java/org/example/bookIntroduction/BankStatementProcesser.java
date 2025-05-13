package org.example.bookIntroduction;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;


public class BankStatementProcesser {

    private final List<BankTransaction> bankTransactionList;

    public BankStatementProcesser(List<BankTransaction> bankTransactionList) {
        this.bankTransactionList = bankTransactionList;
    }

    /*public double calculateTotalAmount(){
        double total = 0;
        for(final BankTransaction bankTransaction: bankTransactionList){
            total+= bankTransaction.getAmount();
        }

        return total;


    }
    public double calculateTotalMonth(final Month month){
        double total = 0;
        for(final BankTransaction bankTransaction : bankTransactionList){
            if(bankTransaction.getDate().getMonth() == month){
                total+=bankTransaction.getAmount();

            }
        }
        return total;

    }
    public double calculateTotalCategory(final String category){
        double total = 0;
        for(final BankTransaction bankTransaction : bankTransactionList){
            if(bankTransaction.getDescription().equals(category)){
                total+=bankTransaction.getAmount();

            }
        }
        return total;

    }*/

    /*public List<BankTransaction> findTransacionsGreaterThanEqual(final double amount) {

        final List<BankTransaction> result = new ArrayList<>();
        for(final BankTransaction bankTransaction: bankTransactionList){
            if(bankTransaction.getAmount() >= amount){
                result.add(bankTransaction);

            }
        }
        return result;

    }
    public List<BankTransaction> findTransacionsInMonth(final Month month) {

        final List<BankTransaction> result = new ArrayList<>();
        for(final BankTransaction bankTransaction: bankTransactionList){
            if(bankTransaction.getDate().getMonth() == month){
                result.add(bankTransaction);

            }
        }
        return result;

    }*/

    public double summarizeTransactions(final BankTransactionSummarizer bankTransactionSummarizer) {
        double result = 0;
        for (final BankTransaction bankTransaction : bankTransactionList) {
            result = bankTransactionSummarizer.summarize(result, bankTransaction);
        }

        return result;
    }

    public double calculateTotalInMonth(final Month month) {

        return summarizeTransactions((acc, bankTransaction) ->
                bankTransaction.getDate().getMonth() == month ? acc + bankTransaction.getAmount() : acc);
    }

    public List<BankTransaction> findTransacions(final BankTransactionFilter bankTransactionFilter) {

        final List<BankTransaction> result = new ArrayList<>();
        for (final BankTransaction bankTransaction : bankTransactionList) {
            if (bankTransactionFilter.test(bankTransaction)) {
                result.add(bankTransaction);

            }
        }
        return result;

    }

}

class BankTransactionIsInFebruaryAndExpensive implements BankTransactionFilter {

    @Override
    public boolean test(BankTransaction bankTransaction) {
        return bankTransaction.getDate().getMonth() == Month.FEBRUARY && bankTransaction.getAmount() >= 1_000;
    }
}
