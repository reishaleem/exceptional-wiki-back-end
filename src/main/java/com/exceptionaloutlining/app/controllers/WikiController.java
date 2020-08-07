package com.exceptionaloutlining.app.controllers;

import com.exceptionaloutlining.app.models.User;
import com.exceptionaloutlining.app.models.Wiki;
import com.exceptionaloutlining.app.services.UserService;
import com.exceptionaloutlining.app.services.WikiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WikiController {

    private final WikiService service;

    @Autowired
    public WikiController(WikiService service) {
        this.service = service;
    }

    @GetMapping("/wikis")
    public List<Wiki> getAllUsers() {
        return service.getAllWikis();
    }
}
