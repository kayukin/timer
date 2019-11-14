package com.kayukin.timer.model;

import lombok.Data;

@Data
public class Settings {
    private String weekStart;

    private String timeZone;

    private String timeFormat;

    private String dateFormat;

    private Boolean sendNewsletter;

    private Boolean weeklyUpdates;

    private Boolean longRunning;

    private SummaryReportSettings summaryReportSettings;

    private Boolean isCompactViewOn;

    private String dashboardSelection;

    private String dashboardViewType;

    private Boolean dashboardPinToTop;

    private Integer projectListCollapse;

    private Boolean collapseAllProjectLists;

    private Boolean groupSimilarEntriesDisabled;

    private String myStartOfDay;

    private Boolean timeTrackingManual;
}
