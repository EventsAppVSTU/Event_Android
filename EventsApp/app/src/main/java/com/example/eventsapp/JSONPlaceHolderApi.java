package com.example.eventsapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface JSONPlaceHolderApi {
    @GET("/robo/events/events.php")
    public Call<EventData> getEventsData(@Header("Authorization") String authorization);
}
