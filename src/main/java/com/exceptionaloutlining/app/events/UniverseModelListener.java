package com.exceptionaloutlining.app.events;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.exceptionaloutlining.app.models.Universe;
import com.exceptionaloutlining.app.services.SequenceGeneratorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class UniverseModelListener extends AbstractMongoEventListener<Universe> {

    private SequenceGeneratorService sequenceGenerator;
    private final ZoneId easternStandardTime = ZoneId.of("America/New_York"); // right now, always doing EST
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma' 'MMM dd, yyy");

    @Autowired
    public UniverseModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Universe> event) {

        LocalDateTime timestamp = LocalDateTime.now(easternStandardTime);
        // String formattedTime = timestamp.format(formatter);

        if (event.getSource().getId() == null || Long.parseLong(event.getSource().getId()) < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(Universe.SEQUENCE_NAME));
        }
        if (event.getSource().getCreatedTimestamp() == null || event.getSource().getCreatedTimestamp().equals("")) {
            event.getSource().setCreatedTimestamp(timestamp.toString());
        }

        event.getSource().setModifiedTimestamp(timestamp.toString());
    }
}