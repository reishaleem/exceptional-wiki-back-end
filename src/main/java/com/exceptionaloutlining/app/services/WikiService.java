package com.exceptionaloutlining.app.services;

import com.exceptionaloutlining.app.models.Universe;
import com.exceptionaloutlining.app.models.Wiki;
import com.exceptionaloutlining.app.payload.request.CreateWikiRequest;
import com.exceptionaloutlining.app.payload.response.MessageResponse;
import com.exceptionaloutlining.app.repositories.UniverseRepository;
import com.exceptionaloutlining.app.repositories.UserRepository;
import com.exceptionaloutlining.app.repositories.WikiRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class WikiService {

    private final WikiRepository wikiRepository;
    private final UniverseService universeService;
    private final UserService userService;

    // only for now. Later,go back and reconfigure so that the UserService and
    // UniverseService has methods we can call to do small updates like when saving
    // a new wiki
    private final UniverseRepository universeRepository;
    private final UserRepository userRepository;

    @Autowired
    public WikiService(WikiRepository repository, UniverseService universeService, UserService userService,
            UniverseRepository universeRepository, UserRepository userRepository) {
        this.wikiRepository = repository;
        this.universeService = universeService;
        this.userService = userService;
        this.universeRepository = universeRepository;
        this.userRepository = userRepository;
    }

    public List<Wiki> getAllWikis() {

        return wikiRepository.findAll();
    }

    public Wiki getWiki(String id) {
        return wikiRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: No Wiki with ID " + id));
    }

    public ResponseEntity<?> createWiki(CreateWikiRequest request, String universeId) {

        Wiki wiki = new Wiki();
        wiki.setBody(request.getBody());
        wiki.setOwnerId(request.getOwnerId());
        wiki.setName(request.getName());
        wiki.setUniverseId(universeId);
        wikiRepository.save(wiki);

        Universe universe = universeService.getUniverse(universeId);
        universe.getWikis().add(wiki.getId());
        universeRepository.save(universe);

        return ResponseEntity.ok(new MessageResponse("Wiki created", wiki.getId()));
    }
}
