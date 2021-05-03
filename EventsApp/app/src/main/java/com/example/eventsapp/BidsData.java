package com.example.eventsapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BidsData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private int user_id;
    @SerializedName("event_id")
    @Expose
    private int event_id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("state_id")
    @Expose
    private int state_id;

    public BidsData(Integer id, int user_id, int event_id, String code, int state_id) {
        this.id = id;
        this.user_id = user_id;
        this.event_id = event_id;
        this.code = code;
        this.state_id = state_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getState_id() {
        return state_id;
    }

    public void setState_id(int state_id) {
        this.state_id = state_id;
    }
}
