package com.example.eventsapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EventsAppApi {
    @GET("/robo/events/eventsInfo.php")
    public Call<EventData> getEventsData(@Header("Authorization") String authorization);

    @PUT("/robo/users/userCredentals.php")

    public Call <Object> setCurrentEvent (@Header("Authorization") String authorization, @Body UserData userData);

    @GET("/robo/users/userCredentalsInfo.php")
    public Call<EventData> getUserData(@Header("Authorization") String authorization, @Query("id") int id);

    @GET("/robo/events/eventNews.php")
    public Call<EventData> getEventNews(@Header("Authorization") String authorization, @Query("event_id") int id);

    @GET("/robo/users/userCredentals.php")
    public Call<EventData> getAuthorization(@Query("login") String login, @Query("password") String password);

    @POST("/robo/users/userCredentals.php")
    public Call <FullUserData<UserData>> createUser (@Body RegistrationData registrationData);

    @GET("/robo/performances/performances.php")
    public Call<EventData> getTimeTable(@Header("Authorization") String authorization, @Query("event_id") int id);

    @GET("/robo/events/organizations.php")
    public Call<FullOrganizationsData> getOrganizations(@Header("Authorization") String authorization);

}
