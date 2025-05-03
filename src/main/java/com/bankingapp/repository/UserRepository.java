package com.bankingapp.repository;

import com.bankingapp.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Get user by ID
    @NotNull User getById(Long id);

    // Existing method
    Optional<User> getByEmail(String email);
    
    // Get by phone number
    Optional<User> getByPhone(String phone);
    
    // Get by name and surname
    List<User> getByNameAndSurname(String name, String surname);
    
    // Get by surname
    List<User> getBySurname(String surname);
    
    // Get users born after a certain date
    List<User> getByDateOfBirthAfter(LocalDate date);
    
    // Get users born between two dates
    List<User> getByDateOfBirthBetween(LocalDate startDate, LocalDate endDate);
    
    // Check if a user with this email exists
    boolean existsByEmail(String email);
    
    // Check if a user with this phone number exists
    boolean existsByPhone(String phone);
}