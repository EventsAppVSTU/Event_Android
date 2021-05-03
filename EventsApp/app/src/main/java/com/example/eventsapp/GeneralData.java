package com.example.eventsapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeneralData<Data> {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private GeneralData.data<Data> data;

    public GeneralData(String status, GeneralData.data<Data> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GeneralData.data<Data> getData() {
        return data;
    }

    public void setData(GeneralData.data<Data> data) {
        this.data = data;
    }

    static class data<Data>{
        @SerializedName("objects")
        @Expose
        private List<Data> APIData;

        public List<Data> getAPIData() {
            return APIData;
        }

        public void setAPIData(List<Data> APIData) {
            this.APIData = APIData;
        }
    }
}
