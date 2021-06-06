package com.example.eventsapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChosenPerformancesData {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("user_id")
    @Expose
    private int user_id;

    @SerializedName("performances_id")
    @Expose
    private int performances_id;

    @SerializedName("performances_name")
    @Expose (serialize = false)
    private int performances_name;

    @SerializedName("description")
    @Expose (serialize = false)
    private int description;

    @SerializedName("dataPerf")
    @Expose (serialize = false)
    private int dataPerf;

    @SerializedName("startTime")
    @Expose (serialize = false)
    private int startTime;

    @SerializedName("endTime")
    @Expose (serialize = false)
    private int endTime;

    @SerializedName("speaker")
    @Expose (serialize = false)
    private int speaker;

    public ChosenPerformancesData(int user_id, int performances_id) {
        this.user_id = user_id;
        this.performances_id = performances_id;
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

    public int getPerformances_id() {
        return performances_id;
    }

    public void setPerformances_id(int performances_id) {
        this.performances_id = performances_id;
    }

    public int getPerformances_name() {
        return performances_name;
    }

    public void setPerformances_name(int performances_name) {
        this.performances_name = performances_name;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    public int getDataPerf() {
        return dataPerf;
    }

    public void setDataPerf(int dataPerf) {
        this.dataPerf = dataPerf;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getSpeaker() {
        return speaker;
    }

    public void setSpeaker(int speaker) {
        this.speaker = speaker;
    }

    public static class PerformancesArray {
        @SerializedName("performances_array")
        @Expose
        public List<ChosenPerformancesData> performances_array;
    }
}
