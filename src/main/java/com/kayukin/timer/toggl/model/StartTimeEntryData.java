package com.kayukin.timer.toggl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class StartTimeEntryData {
    private Integer id;

    private String guid;

    @JsonProperty("wid")
    private Integer workspaceId;

    @JsonProperty("pid")
    private Integer projectId;

    private Boolean billable;

    private String description;

    private Boolean duronly;

    @JsonProperty("uid")
    private Integer userId;

    private List<String> tags;

    @JsonProperty("created_with")
    private String createdWith;
}
