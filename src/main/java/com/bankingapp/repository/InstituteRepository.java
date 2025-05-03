package com.bankingapp.repository;

import com.bankingapp.model.Institute;
import com.bankingapp.model.InstituteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituteRepository extends JpaRepository<Institute, Long> {
    // get by name
    Institute getByName(String name);

    // get by type
    Institute getByType(InstituteType type);
}
