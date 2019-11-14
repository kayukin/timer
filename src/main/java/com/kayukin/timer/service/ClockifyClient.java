package com.kayukin.timer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.kayukin.timer.model.AddTimeEntryRequest;
import com.kayukin.timer.model.StopTimeEntryRequest;
import com.kayukin.timer.model.TimeEntry;
import com.kayukin.timer.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface ClockifyClient {
    @GET("api/v1/user")
    Call<User> getUser();

    @GET("api/v1/workspaces/{workspaceId}/user/{userId}/time-entries")
    Call<List<TimeEntry>> getTimeEntries(@Path("workspaceId") String workspaceId, @Path("userId") String userId);

    @PATCH("api/v1/workspaces/{workspaceId}/user/{userId}/time-entries")
    Call<JsonNode> stopActiveTimers(@Path("workspaceId") String workspaceId, @Path("userId") String userId,
                                    @Body StopTimeEntryRequest request);

    @POST("api/v1/workspaces/{workspaceId}/time-entries")
    Call<JsonNode> addTimer(@Path("workspaceId") String workspaceId, @Body AddTimeEntryRequest request);
}
