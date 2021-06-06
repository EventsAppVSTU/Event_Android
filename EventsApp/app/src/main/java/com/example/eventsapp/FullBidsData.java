package com.example.eventsapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FullBidsData {

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
    @SerializedName("user_name")
    @Expose
    private String user_name;
    @SerializedName("event_name")
    @Expose
    private String event_name;
    @SerializedName("state_name")
    @Expose
    private String state_name;
    @SerializedName("event_image")
    @Expose
    private String event_image;

    public FullBidsData(Integer id, int user_id, int event_id, String code, int state_id, String user_name, String event_name, String state_name, String event_image) {
        this.id = id;
        this.user_id = user_id;
        this.event_id = event_id;
        this.code = code;
        this.state_id = state_id;
        this.user_name = user_name;
        this.event_name = event_name;
        this.state_name = state_name;
        this.event_image = event_image;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getState_id() {
        return state_id;
    }

    public void setState_id(int state_id) {
        this.state_id = state_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getEvent_image() {
        return event_image;
    }

    public void setEvent_image(String event_image) {
        this.event_image = event_image;
    }
}
