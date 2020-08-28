package com.exceptionaloutlining.app.services;

import java.util.ArrayList;
import java.util.List;

import com.exceptionaloutlining.app.models.Calendar;
import com.exceptionaloutlining.app.models.Chart;
import com.exceptionaloutlining.app.models.Map;
import com.exceptionaloutlining.app.models.Notebook;
import com.exceptionaloutlining.app.models.Timeline;
import com.exceptionaloutlining.app.models.Universe;
import com.exceptionaloutlining.app.models.User;
import com.exceptionaloutlining.app.models.Wiki;
import com.exceptionaloutlining.app.payload.request.CreateUniverseRequest;
import com.exceptionaloutlining.app.payload.response.MessageResponse;
import com.exceptionaloutlining.app.repositories.UniverseRepository;
import com.exceptionaloutlining.app.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class UniverseService {

    private final UniverseRepository universeRepository;
    private final UserRepository userRepository;

    @Autowired
    public UniverseService(UniverseRepository universeRepository, UserRepository userRepository) {
        this.universeRepository = universeRepository;
        this.userRepository = userRepository;
    }

    public List<Universe> getAllUniverses() {
        return universeRepository.findAll();
    }

    public Universe getUniverse(String universeId) {
        return universeRepository.findById(universeId)
                .orElseThrow(() -> new RuntimeException("Error: No Universe ID found with id " + universeId));
    }

    public ResponseEntity<?> createUniverse(CreateUniverseRequest request, String userId) {

        Universe universe = new Universe();
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Error: User not found with id " + userId));
        universe.setOwnerId(userId);
        universe.setName(request.getName());
        universe.setDescription(request.getDesc());
        // universe.setDateCreated(dateCreated);
        // universe.setWikis(new ArrayList<Wiki>());
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