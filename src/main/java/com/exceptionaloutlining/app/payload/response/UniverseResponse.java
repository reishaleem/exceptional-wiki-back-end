package com.exceptionaloutlining.app.payload.response;

import java.util.List;

import com.exceptionaloutlining.app.models.Calendar;
import com.exceptionaloutlining.app.models.Chart;
import com.exceptionaloutlining.app.models.Map;
import com.exceptionaloutlining.app.models.Notebook;
import com.exceptionaloutlining.app.models.Timeline;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UniverseResponse {

    private String id;
    private String ownerId;
    private String name;
    private String description;
    private String createdTimestamp;
    private String modifiedTimestamp;
    private List<String> wikis;
    private List<Map> maps;
    private List<Timeline> timelines;
    private List<Calendar> calendars;
    private List<Chart> charts;
    private Notebook notebook;
}