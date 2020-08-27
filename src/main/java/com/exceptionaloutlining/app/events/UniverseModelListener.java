package com.exceptionaloutlining.app.events;

import com.exceptionaloutlining.app.models.Universe;
import com.exceptionaloutlining.app.services.SequenceGeneratorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class UniverseModelListener extends AbstractMongoEventListener<Universe> {

    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public UniverseModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Universe> event) {

        if (event.getSource().getId() == null || Long.parseLong(event.getSource().getId()) < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(Universe.SEQUENCE_NAME));
        }
    }

}