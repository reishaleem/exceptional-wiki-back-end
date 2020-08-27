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

    public ResponseEntity<?> createUniverse(CreateUniverseRequest request, String userId) {

        Universe universe = new Universe();
        universe.setOwner(request.getOwner());
        universe.setName(request.getName());
        universe.setDescription(request.getDesc());
        universe.setWikis(new ArrayList<Wiki>());
        universe.setMaps(new ArrayList<Map>());
        universe.setTimelines(new ArrayList<Timeline>());
        universe.setCalendars(new ArrayList<Calendar>());
        universe.setCharts(new ArrayList<Chart>());
        universe.setNotebook(new Notebook());

        // getting some error with saving...should we look into ditching DBRef and just
        // storing a sub document?
        // before doing that, make sure the other controllers and responses are in
        // order. Too much tech debt. Refactor the code so that IDs are being sent in
        // the URL, not through the request body, and make sure that we get all the
        // Requests and Responses in order. Right now we just send stuff in a jwt
        // response...
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Error: User not found with id " + userId));
        ; // get user id from the request getOwner.id instead
        owner.setUniverses(new ArrayList<Universe>()); // only temporary
        owner.getUniverses().add(universe);
        universeRepository.save(universe);
        return ResponseEntity.ok(new MessageResponse("Universe created!"));

    }

}