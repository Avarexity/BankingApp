package com.bankingapp.service;

import com.bankingapp.model.Institute;
import com.bankingapp.model.InstituteType;
import com.bankingapp.repository.InstituteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstituteService {
    private final InstituteRepository repo;

    @Autowired
    public InstituteService(InstituteRepository repo) {
        this.repo = repo;
    }

    public Institute getById(Long id) {
        if (repo.existsById(id)) {
            return repo.getReferenceById(id);
        } else {
            return null;
        }
    }

    public Institute findById(Long id) {
        if (repo.existsById(id)) {
            return repo.getById(id);
        } else {
            throw new IllegalArgumentException("Institute ID: " + id + " does not exist");
        }
    }

    public List<Institute> getByName(String name) {
        return repo.getByName(name);
    }

    public List<Institute> getByType(InstituteType type) {
        return repo.getByType(type);
    }

    public List<Institute> getAllInstitutes() {
        return repo.findAll();
    }
}
