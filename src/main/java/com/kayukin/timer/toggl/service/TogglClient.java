package com.kayukin.timer.toggl.service;

import com.kayukin.timer.toggl.model.StartTimeEntry;
import com.kayukin.timer.toggl.model.TimeEntry;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TogglClient {
    @GET("api/v8/time_entries/current")
    Call<TimeEntry> getCurrentEntry();

    @POST("api/v8/time_entries/start")
    Call<TimeEntry> startEntry(@Body StartTimeEntry timeEntry);

    @PUT("api/v8/time_entries/{id}/stop")
    Call<TimeEntry> stopEntry(@Path("id") Integer id);
}
