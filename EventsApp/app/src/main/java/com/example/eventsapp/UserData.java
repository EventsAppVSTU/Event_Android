package com.example.eventsapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("surname")
    @Expose
    private String surname;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("organization_id")
    @Expose
    private String organization_id;
    @SerializedName("role")
    @Expose
    private int role;
    @SerializedName("current_event")
    @Expose
    private String current_event;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("organization_verify")
    @Expose
    private int organization_verify;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("web_link")
    @Expose
    private String web_link;
    @SerializedName("bio")
    @Expose
    private String bio;


    public UserData(int id, String name, String surname, String image, String organization_id, int role,
                    String current_event, String login, String password, int organization_verify,
                    String phone, String web_link, String bio) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.image = image;
        this.organization_id = organization_id;
        this.role = role;
        this.current_event = current_event;
        this.login = login;
        this.password = password;
        this.organization_verify = organization_verify;
        this.phone = phone;
        this.web_link = web_link;
        this.bio = bio;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(String organization_id) {
        this.organization_id = organization_id;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {this.role = role;}

    public String getCurrent_event() {
        return current_event;
    }

    public void setCurrent_event(String current_event) {
        this.current_event = current_event;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getOrganization_verify() {
        return organization_verify;
    }

    public void setOrganization_verify(int organization_verify) {
        this.organization_verify = organization_verify;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWeb_link() {
        return web_link;
    }

    public void setWeb_link(String web_link) {
        this.web_link = web_link;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

}
