package com.bankingapp.service;

import com.bankingapp.model.User;
import com.bankingapp.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    private final UserRepository repo;

    @Autowired
    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User register(String name, String surname, LocalDate dateOfBirth,
                         String email, String phone, char[] password) {
        User user = new User(generateId(), name, surname, dateOfBirth, email, phone, password);
        repo.save(user);
        return user;
    }

    public boolean close(User user) {
        try {
            repo.delete(user);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public User getById(Long id) {
        if (repo.existsById(id)) {
            return repo.getReferenceById(id);
        } else {
            return null;
        }
    }

    public User getByEmail(String email) {
        if (repo.existsByEmail(email)) {
            return repo.getByEmail(email).get();
        } else {
            return null;
        }
    }

    public User getByPhone(String phone) {
        if (repo.existsByPhone(phone)) {
            return repo.getByPhone(phone).get();
        } else {
            return null;
        }
    }

    private @NotNull Long generateId() {
        return repo.count() + 1;
    }

    public boolean existsByEmail(String email) {
        return repo.existsByEmail(email);
    }

    public boolean existsByPhone(String phone) {
        return repo.existsByPhone(phone);
    }

    public boolean existsById(Long id) {
        return repo.existsById(id);
    }

    public List<User> getByNameAndSurname(String name, String surname) {
        return repo.getByNameAndSurname(name, surname);
    }

    public List<User> getBySurname(String surname) {
        return repo.getBySurname(surname);
    }

    public List<User> getByDateOfBirthAfter(LocalDate date) {
        return repo.getByDateOfBirthAfter(date);
    }

    public List<User> getByDateOfBirthBetween(LocalDate startDate, LocalDate endDate) {
        return repo.getByDateOfBirthBetween(startDate, endDate);
    }
}
