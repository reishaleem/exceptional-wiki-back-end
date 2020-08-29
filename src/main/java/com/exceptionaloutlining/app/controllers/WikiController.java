package com.exceptionaloutlining.app.controllers;

import com.exceptionaloutlining.app.models.Wiki;
import com.exceptionaloutlining.app.payload.request.CreateWikiRequest;
import com.exceptionaloutlining.app.payload.request.UpdateWikiRequest;
import com.exceptionaloutlining.app.services.WikiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/universes/{universeId}/wikis/")
@CrossOrigin(origins = "*")
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

    @GetMapping("{id}")
    public ResponseEntity<?> getWiki(@PathVariable String id) {
        return ResponseEntity.ok(service.getWiki(id));
    }

    @PostMapping("/new")
    public ResponseEntity<?> createWiki(@Valid @RequestBody CreateWikiRequest createRequest,
            @PathVariable String universeId) {
        return service.createWiki(createRequest, universeId);
    }

    @PutMapping("{id}/update")
    public ResponseEntity<?> updateWiki(@Valid @RequestBody UpdateWikiRequest updateRequest, @PathVariable String id) {
        return service.updateWiki(updateRequest, id);
    }
}
