package com.bankingapp.repository;

import com.bankingapp.model.Institute;
import com.bankingapp.model.InstituteType;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstituteRepository extends JpaRepository<Institute, Long> {
    // Get an institute by its ID
    @NotNull Institute getById(@NotNull Long id);

    // Get all institutes by name
    List<Institute> getByName(String name);

    // Get all institutes by type
    List<Institute> getByType(InstituteType type);
}
