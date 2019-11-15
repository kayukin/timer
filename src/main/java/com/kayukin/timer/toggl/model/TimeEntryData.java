package com.kayukin.timer.toggl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;

@Data
public class TimeEntryData {
    private Integer id;

    private String guid;

    @JsonProperty("wid")
    private Integer workspaceId;

    @JsonProperty("pid")
    private Integer projectId;

    private Boolean billable;

    private ZonedDateTime start;

    private Duration duration;

    private String description;

    private Boolean duronly;

    @JsonProperty("at")
    private ZonedDateTime updatedAt;

    @JsonProperty("uid")
    private Integer userId;

    private List<String> tags;
}
