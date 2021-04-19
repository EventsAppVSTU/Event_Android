package com.example.eventsapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FullUserData<Data> {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private data<Data> data;

    public FullUserData(String status, FullUserData.data<Data> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FullUserData.data<Data> getData() {
        return data;
    }

    public void setData(FullUserData.data<Data> data) {
        this.data = data;
    }

    static class data<Data>{
        @SerializedName("objects")
        @Expose
        private List<Data> userData;

        public List<Data> getUserData() {
            return userData;
        }

        public void setUserData(List<Data> userData) {
            this.userData = userData;
        }
    }
}
