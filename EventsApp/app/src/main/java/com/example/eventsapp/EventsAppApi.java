package com.example.eventsapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EventsAppApi {
    @GET("/robo/events/eventsInfo.php")
    public Call<EventData> getEventsData(@Header("Authorization") String authorization,
                                         @Query("id") Integer id,
                                         @Query("organization_id") Integer organization_id,
                                         @Query("category_id") Integer category_id);

    @PUT("/robo/users/userCredentals.php")

    public Call <Object> setCurrentEvent (@Header("Authorization") String authorization, @Body UserData userData);

    @GET("/robo/users/userCredentalsInfo.php")
    public Call<EventData> getUserData(@Header("Authorization") String authorization, @Query("id") int id);

    @GET("/robo/events/eventNews.php")
    public Call<EventData> getEventNews(@Header("Authorization") String authorization, @Query("event_id") int id);

    @GET("/robo/users/userCredentals.php")
    public Call<EventData> getAuthorization(@Query("login") String login, @Query("password") String password);

    @POST("/robo/users/userCredentals.php")
    public Call <GeneralData<UserData>> createUser (@Body RegistrationData registrationData);

    @GET("/robo/performances/performances.php")
    public Call<EventData> getTimeTable(@Header("Authorization") String authorization, @Query("event_id") int id);

    @GET("/robo/events/organizations.php")
    public Call<FullOrganizationsData> getOrganizations();

    @GET("/robo/events/bids.php")
    public Call<GeneralData<BidsData>> getBidsData(@Header("Authorization") String authorization,
                                         @Query("user_id") Integer user_id,
                                         @Query("event_id") Integer event_id,
                                         @Query("state_id") Integer state_id);

    @POST("/robo/events/bids.php")
    public Call <GeneralData<BidsData>> createBid (@Header("Authorization") String authorization,  @Body BidsData bidsData);

    @PUT("/robo/events/bids.php")
    public Call <GeneralData<BidsData>> updateBid (@Header("Authorization") String authorization,  @Body BidsData bidsData);

    @DELETE("/robo/events/bids.php")
    public Call <Object> deleteBid (@Header("Authorization") String authorization,  @Query("id") int id);

    @GET("/robo/events/bidsInfo.php")
    public Call<GeneralData<FullBidsData>> getFullBidsData(@Header("Authorization") String authorization,
                                                   @Query("user_id") Integer user_id,
                                                   @Query("event_id") Integer event_id,
                                                   @Query("state_id") Integer state_id);

    @POST("/robo/performances/choosenPerformances.php")
    public Call <Object> setChosenPerf (@Header("Authorization") String authorization,
                                        @Body ChosenPerformancesData.PerformancesArray PerfData);


    @GET("/robo/performances/choosenPerformancesInfo.php")
    public Call<EventData> getChosenPerf(@Header("Authorization") String authorization,
                                         @Query("event_id") int event_id, @Query("user_id") int user_id);

    @GET("/robo/events/choosenEventsInfo.php")
    public Call<GeneralData<ChosenEventsData>> getChosenEvents(@Header("Authorization") String authorization,
                                         @Query("event_id") int event_id, @Query("user_id") int user_id);

    @POST("/robo/events/choosenEvents.php")
    public Call <GeneralData<ChosenEventsData>> setChosenEvent(@Header("Authorization") String authorization,
                                        @Body ChosenEventsData EventData);

    @DELETE("/robo/events/choosenEvents.php")
    public Call <Object> deleteChosenEvent (@Header("Authorization") String authorization,  @Query("id") int id);

}
