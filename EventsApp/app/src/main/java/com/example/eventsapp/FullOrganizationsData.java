package com.example.eventsapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FullOrganizationsData {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private FullOrganizationsData.data data;

    public FullOrganizationsData(String status, FullOrganizationsData.data data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FullOrganizationsData.data getData() {
        return data;
    }

    public void setData(FullOrganizationsData.data data) {
        this.data = data;
    }

    static class data{
        @SerializedName("objects")
        @Expose
        private List<OrganizationsData> organizationsData;

        public List<OrganizationsData> getOrganizationsData() {
            return organizationsData;
        }

        public void setOrganizationsData(List<OrganizationsData> organizationsData) {
            this.organizationsData = organizationsData;
        }
    }
}
