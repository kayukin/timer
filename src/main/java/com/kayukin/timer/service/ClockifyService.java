package com.kayukin.timer.service;

import com.kayukin.timer.model.AddTimeEntryRequest;
import com.kayukin.timer.model.RetrofitException;
import com.kayukin.timer.model.StopTimeEntryRequest;
import com.kayukin.timer.model.TimeEntry;
import com.kayukin.timer.model.User;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ClockifyService {
    private final ClockifyClient clockifyClient;
    private final List<TimeEntry> pausedTimers;
    private final String userId;
    private final String workspace;

    public ClockifyService(ClockifyClient clockifyClient) {
        this.clockifyClient = clockifyClient;
        this.pausedTimers = new ArrayList<>();
        User user = body(clockifyClient.getUser());
        this.userId = user.getId();
        this.workspace = user.getDefaultWorkspace();
    }

    public void pauseActiveTimers() {
        List<TimeEntry> timeEntries = body(clockifyClient.getTimeEntries(workspace, userId));
        List<TimeEntry> activeEntries = timeEntries.stream()
                .filter(timeEntry -> timeEntry.getTimeInterval().getEnd() == null)
                .collect(Collectors.toList());
        if (!activeEntries.isEmpty()) {
            execute(clockifyClient.stopActiveTimers(workspace, userId, createStopRequest()));
            pausedTimers.addAll(activeEntries);
        }
    }

    public void resumePausedTimers() {
        pausedTimers.forEach(timeEntry -> {
            String description = timeEntry.getDescription();
            AddTimeEntryRequest request = new AddTimeEntryRequest();
            request.setDescription(description);
            request.setStart(LocalDateTime.now(Clock.systemUTC()));
            execute(clockifyClient.addTimer(workspace, request));
        });
        pausedTimers.clear();
    }

    private StopTimeEntryRequest createStopRequest() {
        return new StopTimeEntryRequest(LocalDateTime.now(ZoneOffset.UTC));
    }

    private <T> Response<T> execute(Call<T> call) {
        try {
            return call.execute();
        } catch (IOException e) {
            throw new RetrofitException(e);
        }
    }

    private <T> T body(Call<T> call) {
        return execute(call).body();
    }
}
