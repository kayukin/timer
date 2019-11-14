package com.kayukin.timer.model;

import lombok.Data;

@Data
public class TimeEntry {
    private String id;

    private String description;

    private Object tagIds;

    private String userId;

    private Boolean billable;

    private Object taskId;

    private String projectId;

    private TimeInterval timeInterval;

    private String workspaceId;

    private Boolean isLocked;
}