package com.example.eventsapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class EventData {
    class data{
        @SerializedName("objects")
        @Expose
        private List objects;
        public List getObjects(){
            return this.objects;
        }


    }

//    @SerializedName("id")
//    @Expose
//    private int id;
//    @SerializedName("name")
//    @Expose
//    private String name;
//    @SerializedName("description")
//    @Expose
//    private String description;
//    @SerializedName("startDate")
//    @Expose
//    private Date startDate;
//    @SerializedName("endDate")
//    @Expose
//    private Date endDate;
//    @SerializedName("image")
//    @Expose
//    private String image;
//    @SerializedName("place")
//    @Expose
//    private String place;
//    @SerializedName("category_id")
//    @Expose
//    private String category_id;
    @SerializedName("data")
    @Expose
    private data data;


    public List getObj(){
        return data.getObjects();
    }



}
