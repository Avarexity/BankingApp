package com.bankingapp.repository;

import com.bankingapp.model.Institute;
import com.bankingapp.model.InstituteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituteRepository extends JpaRepository<Institute, Long> {
    // Find by name
    Institute findByName(String name);

    // Find by type
    Institute findByType(InstituteType type);
}
