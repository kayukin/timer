package com.kayukin.timer.toggl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StartTimeEntry {
    @JsonProperty("time_entry")
    private StartTimeEntryData data;
}
