package com.kayukin.timer.toggl.service;

import com.kayukin.timer.model.RetrofitException;
import com.kayukin.timer.toggl.converter.StartRequestConverter;
import com.kayukin.timer.toggl.model.TimeEntry;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

@Slf4j
public class TogglService {
    private final TogglClient togglClient;
    private final StartRequestConverter requestConverter;

    private TimeEntry pausedTimer;

    public TogglService(TogglClient togglClient, StartRequestConverter requestConverter) {
        this.togglClient = togglClient;
        this.requestConverter = requestConverter;
    }

    public void pauseActiveTimers() {
        TimeEntry timeEntry = body(togglClient.getCurrentEntry());
        if (timeEntry.getData() != null) {
            execute(togglClient.stopEntry(timeEntry.getData().getId()));
            pausedTimer = timeEntry;
        } else {
            pausedTimer = null;
        }
    }

    public void resumePausedTimers() {
        if (pausedTimer != null) {
            execute(togglClient.startEntry(requestConverter.convert(pausedTimer)));
            pausedTimer = null;
        }
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
