package com.kayukin.timer.toggl.model;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class TimeEntry {
    @Nullable
    private TimeEntryData data;
}
