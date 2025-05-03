package com.bankingapp.service;

import com.bankingapp.model.Institute;
import com.bankingapp.model.InstituteType;
import com.bankingapp.repository.InstituteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstituteService {
    private final InstituteRepository repo;

    @Autowired
    public InstituteService(InstituteRepository repo) {
        this.repo = repo;
    }

    public Institute findByName(String name) {
        return repo.getByName(name);
    }

    public Institute getByType(InstituteType type) {
        return repo.getByType(type);
    }
}
