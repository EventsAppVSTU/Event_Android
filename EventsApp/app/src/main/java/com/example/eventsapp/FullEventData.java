package com.example.eventsapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FullEventData {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("place")
    @Expose
    private String place;
    @SerializedName("category_id")
    @Expose
    private int category_id;
    @SerializedName("private")
    @Expose
    private int privateEvent;
    @SerializedName("organization_event")
    @Expose
    private int organization_event;
    @SerializedName("organization_id")
    @Expose
    private int organization_id;
    @SerializedName("category_name")
    @Expose
    private String category_name;
    @SerializedName("organization_name")
    @Expose
    private String organization_name;

    public FullEventData(int id, String name, String description, String startDate, String endDate,
                         String image, String place, int category_id, int privateEvent, int organization_event,
                         int organization_id, String category_name, String organization_name) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.image = image;
        this.place = place;
        this.category_id = category_id;
        this.privateEvent = privateEvent;
        this.organization_event = organization_event;
        this.organization_id = organization_id;
        this.category_name = category_name;
        this.organization_name = organization_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getPrivateEvent() {
        return privateEvent;
    }

    public void setPrivateEvent(int privateEvent) {
        this.privateEvent = privateEvent;
    }

    public int getOrganization_event() {
        return organization_event;
    }

    public void setOrganization_event(int organization_event) {
        this.organization_event = organization_event;
    }

    public int getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(int organization_id) {
        this.organization_id = organization_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getOrganization_name() {
        return organization_name;
    }

    public void setOrganization_name(String organization_name) {
        this.organization_name = organization_name;
    }
}
