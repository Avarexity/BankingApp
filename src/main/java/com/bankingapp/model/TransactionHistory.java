package com.bankingapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TransactionHistory {
    private final Account account;
    private final List<Transaction> transactions = new ArrayList<>();

    public TransactionHistory(Account account) {
        this.account = account;
    }

    public Account getAccount() { return account; }
    public List<Transaction> getTransactions() { return transactions; }

    public void addTransaction(Transaction transaction) {
        Objects.requireNonNull(transaction, "Transaction cannot be null.");
        transactions.add(transaction);
    }

    public boolean removeTransaction(Transaction transaction) {
        Objects.requireNonNull(transaction, "Transaction cannot be null.");
        return transactions.remove(transaction);
    }

    public void clearHistory() { transactions.clear(); }

    public int getTransactionCount() { return transactions.size(); }

    public Transaction getTransaction(int index) {
        if (index < 0 || index >= transactions.size()) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        } else return transactions.get(index);
    }

    public List<Transaction> getTransactionsAtDates(LocalDate startDate, LocalDate endDate) {
        List<Transaction> result = new ArrayList<>();

        for (Transaction transaction : transactions) {
            LocalDate date = transaction.getTimestamp().toLocalDate();
            if ((date.isEqual(startDate) || date.isAfter(startDate)) &&
                    (date.isEqual(endDate)   || date.isBefore(endDate))) {
                result.add(transaction);
            }
        }

        return result;
    }

    public Transaction getLatestTransaction() {
        if (transactions.isEmpty()) {
            return null;
        } else return transactions.getLast();
    }

    public List<Transaction> getTransactionsByState(TransactionState state) {
        return transactions.stream()
                .filter(t -> t.getState() == state)
                .toList();
    }

    public List<Transaction> getTransactionsWithNote() {
        List<Transaction> result = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction.getNote() != null && !transaction.getNote().isBlank()) {
                result.add(transaction);
            }
        }

        return result;
    }

    public List<Transaction> getCardPayments(Institute retailer) {
        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.CARD_PAYMENT
                        && t.getInstitute().equals(retailer))
                .collect(Collectors.toList());
    }

    public List<Transaction> filterBy(Predicate<Transaction> predicate) {
        List<Transaction> result = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (predicate.test(transaction)) {
                result.add(transaction);
            }
        }

        return result;
    }
}
