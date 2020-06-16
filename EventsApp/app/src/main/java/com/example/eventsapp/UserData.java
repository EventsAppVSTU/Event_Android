package com.example.eventsapp;

public class UserData {
    int id;
    String name;
    String surname;
    String image;
    int organization_id;
    String current_event;
    String login;
    String password;

    public UserData(int id, String name, String surname, String image, int organization_id, String current_event, String login, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.image = image;
        this.organization_id = organization_id;
        this.current_event = current_event;
        this.login = login;
        this.password = password;
    }
}
