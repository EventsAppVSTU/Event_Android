package com.example.eventsapp;

public class RegistrationData {

    String name;
    String surname;
    String image;
    int organization_id;
    String login;
    String password;
    String current_event;

    public RegistrationData(String name, String surname, String image, int organization_id, String current_event, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.image = image;
        this.organization_id = organization_id;
        this.current_event = current_event;
        this.login = login;
        this.password = password;
    }
}
