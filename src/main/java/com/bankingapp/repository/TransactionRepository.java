package com.bankingapp.repository;

import com.bankingapp.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Find all transactions by owner
    List<Transaction> findByMaker(User maker);

    // Find all transactions by account
    List<Transaction> findByAccount(Account account);

    // Find all transactions by type
    List<Transaction> findByType(TransactionType type);

    // Find all transactions by state
    List<Transaction> findByState(TransactionState state);

    // Find all transactions by date range
    List<Transaction> findByDateBetween(LocalDate startDate, LocalDate endDate);

    // Find all transactions by range amount
    List<Transaction> findByAmount(BigDecimal minAmount, BigDecimal maxAmount);

    // Fine all transactions by institute type
    List<Transaction> findByInstituteType(InstituteType type);
    
    boolean existsByAccount(Account account);
    
    boolean existsByMaker(User maker);
    
    boolean existsByInstituteType(InstituteType type);

    boolean existsByInstitute(Institute institute);

    List<Transaction> findByInstitute(Institute institute);

    boolean existsByDateBetween(LocalDate startDate, LocalDate endDate);

    boolean existsByAmountBetween(BigDecimal min, BigDecimal max);
}
