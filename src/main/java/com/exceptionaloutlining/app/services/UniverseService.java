package com.exceptionaloutlining.app.services;

import java.util.ArrayList;
import java.util.List;

import com.exceptionaloutlining.app.models.Universe;
import com.exceptionaloutlining.app.models.User;
import com.exceptionaloutlining.app.models.Wiki;
import com.exceptionaloutlining.app.payload.request.CreateUniverseRequest;
import com.exceptionaloutlining.app.payload.response.MessageResponse;
import com.exceptionaloutlining.app.payload.response.WikiResponse;
import com.exceptionaloutlining.app.repositories.UniverseRepository;
import com.exceptionaloutlining.app.repositories.UserRepository;
import com.exceptionaloutlining.app.repositories.WikiRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class UniverseService {

    private final UniverseRepository universeRepository;
    private final UserRepository userRepository;

    // rmeoved to stopped circular dependencies...not sure if this is the proper
    // way.
    // private final WikiService wikiService;

    private final WikiRepository wikiRepository;

    @Autowired
    public UniverseService(UniverseRepository universeRepository, UserRepository userRepository,
            WikiRepository wikiRepository) {
        this.universeRepository = universeRepository;
        this.userRepository = userRepository;
        this.wikiRepository = wikiRepository;
    }

    public List<Universe> getAllUniverses() {
        return universeRepository.findAll();
    }

    public Universe getUniverse(String universeId) {
        return universeRepository.findById(universeId)
                .orElseThrow(() -> new RuntimeException("Error: No Universe ID found with id " + universeId));
    }

    public ResponseEntity<?> getWikiList(String id) {
        Universe universe = universeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: No Universe with ID " + id));
        List<String> wikiIds = universe.getWikis();
        List<WikiResponse> wikis = new ArrayList<WikiResponse>();

        // this functionality should probably be moved to wiki service
        for (String wikiId : wikiIds) {
            Wiki wiki = wikiRepository.findById(wikiId)
                    .orElseThrow(() -> new RuntimeException("Error: No Wiki with ID " + wikiId));
            WikiResponse response = new WikiResponse(wiki.getId(), wiki.getName(), wiki.getUniverseId(),
                    wiki.getOwnerId(), wiki.getCreatedTimestamp(), wiki.getModifiedTimestamp(), wiki.getBody());
            wikis.add(response);
        }

        return ResponseEntity.ok(wikis);
    }

    public ResponseEntity<?> createUniverse(CreateUniverseRequest request, String userId) {

        Universe universe = new Universe();
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Error: User not found with id " + userId));
        universe.setOwnerId(userId);
        universe.setName(request.getName());
        universe.setDescription(request.getDesc());
        // universe.setDateCreated(dateCreated);
        universe.setWikis(new ArrayList<String>());
        // universe.setMaps(new ArrayList<Map>());
        // universe.setTimelines(new ArrayList<Timeline>());
        // universe.setCalendars(new ArrayList<Calendar>());
        // universe.setCharts(new ArrayList<Chart>());
        // universe.setNotebook(new Notebook());

        universeRepository.save(universe);
        owner.getUniverseIds().add(universe.getId());
        userRepository.save(owner);

        return ResponseEntity.ok(new MessageResponse("Universe created!", universe.getId()));
    }

}