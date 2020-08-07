package com.exceptionaloutlining.app.services;

import com.exceptionaloutlining.app.models.User;
import com.exceptionaloutlining.app.models.Wiki;
import com.exceptionaloutlining.app.repositories.UserRepository;
import com.exceptionaloutlining.app.repositories.WikiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class WikiService {

    private final WikiRepository repository;

    @Autowired
    public WikiService(WikiRepository repository) {
        this.repository = repository;
    }

    public List<Wiki> getAllWikis() {

        return repository.findAll();
    }
}
