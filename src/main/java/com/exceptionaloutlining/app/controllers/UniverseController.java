package com.exceptionaloutlining.app.controllers;

import java.util.List;

import javax.validation.Valid;

import com.exceptionaloutlining.app.models.Universe;
import com.exceptionaloutlining.app.payload.request.CreateUniverseRequest;
import com.exceptionaloutlining.app.services.UniverseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/universes")
@CrossOrigin(origins = "*")
public class UniverseController {

    private final UniverseService service;

    @Autowired
    public UniverseController(UniverseService service) {
        this.service = service;
    }

    @GetMapping("/universes")
    public List<Universe> getAllUniverses() {
        return service.getAllUniverses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUniverse(@PathVariable String id) {
        return ResponseEntity.ok(service.getUniverse(id));
    }

    @GetMapping("/{id}/universes")
    public ResponseEntity<?> getWikiList(@PathVariable String id) {
        return service.getWikiList(id);
    }

    @PostMapping("/{userId}/create_universe")
    public ResponseEntity<?> createUniverse(@Valid @RequestBody CreateUniverseRequest universeRequest,
            @PathVariable String userId) {
        return service.createUniverse(universeRequest, userId);
    }

}