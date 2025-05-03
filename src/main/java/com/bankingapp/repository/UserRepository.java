package com.bankingapp.repository;

import com.bankingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Existing method
    Optional<User> findByEmail(String email);
    
    // Find by phone number
    Optional<User> findByPhone(String phone);
    
    // Find by name and surname
    List<User> findByNameAndSurname(String name, String surname);
    
    // Find by surname
    List<User> findBySurname(String surname);
    
    // Find users born after a certain date
    List<User> findByDateOfBirthAfter(LocalDate date);
    
    // Find users born between two dates
    List<User> findByDateOfBirthBetween(LocalDate startDate, LocalDate endDate);
    
    // Check if a user with this email exists
    boolean existsByEmail(String email);
    
    // Check if a user with this phone number exists
    boolean existsByPhone(String phone);
}