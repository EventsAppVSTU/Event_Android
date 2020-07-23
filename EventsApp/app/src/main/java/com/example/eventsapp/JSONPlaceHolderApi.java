package com.example.eventsapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JSONPlaceHolderApi {
    @GET("/robo/events/events.php")
    public Call<EventData> getEventsData(@Header("Authorization") String authorization);

    @PUT("/robo/users/userCredentals.php")

    public Call <Object> setCurrentEvent (@Header("Authorization") String authorization, @Body UserData userData);

    @GET("/robo/users/userCredentals.php")
    public Call<EventData> getUserData(@Header("Authorization") String authorization, @Query("id") int id);

    @GET("/robo/events/eventNews.php")
    public Call<EventData> getEventNews(@Header("Authorization") String authorization, @Query("event_id") int id);


}
