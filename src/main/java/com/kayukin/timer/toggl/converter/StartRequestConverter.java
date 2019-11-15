package com.kayukin.timer.toggl.converter;

import com.kayukin.timer.toggl.model.StartTimeEntry;
import com.kayukin.timer.toggl.model.StartTimeEntryData;
import com.kayukin.timer.toggl.model.TimeEntry;
import org.springframework.core.convert.converter.Converter;

public class StartRequestConverter implements Converter<TimeEntry, StartTimeEntry> {
    private static final String TIMER_APP = "timer_app";

    @Override
    public StartTimeEntry convert(TimeEntry timeEntry) {
        StartTimeEntry result = new StartTimeEntry();
        if (timeEntry.getData() == null) {
            return result;
        }
        StartTimeEntryData timeEntryData = new StartTimeEntryData();
        timeEntryData.setBillable(timeEntry.getData().getBillable());
        timeEntryData.setDescription(timeEntry.getData().getDescription());
        timeEntryData.setProjectId(timeEntry.getData().getProjectId());
        timeEntryData.setTags(timeEntry.getData().getTags());
        timeEntryData.setUserId(timeEntry.getData().getUserId());
        timeEntryData.setWorkspaceId(timeEntry.getData().getWorkspaceId());
        timeEntryData.setDuronly(timeEntry.getData().getDuronly());
        timeEntryData.setCreatedWith(TIMER_APP);
        result.setData(timeEntryData);
        return result;
    }
}
