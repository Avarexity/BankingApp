package com.bankingapp.repository;

import com.bankingapp.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // get all transactions by owner
    List<Transaction> getByMaker(User maker);

    // get all transactions by account
    List<Transaction> getByAccount(Account account);

    // get all transactions by type
    List<Transaction> getByType(TransactionType type);

    // get all transactions by state
    List<Transaction> getByState(TransactionState state);

    // get all transactions by date range
    List<Transaction> getByDateBetween(LocalDate startDate, LocalDate endDate);

    // get all transactions by range amount
    List<Transaction> getByAmount(BigDecimal minAmount, BigDecimal maxAmount);

    // Fine all transactions by institute type
    List<Transaction> getByInstituteType(InstituteType type);
    
    boolean existsByAccount(Account account);
    
    boolean existsByMaker(User maker);
    
    boolean existsByInstituteType(InstituteType type);

    boolean existsByInstitute(Institute institute);

    List<Transaction> getByInstitute(Institute institute);

    boolean existsByDateBetween(LocalDate startDate, LocalDate endDate);

    boolean existsByAmountBetween(BigDecimal min, BigDecimal max);
}
