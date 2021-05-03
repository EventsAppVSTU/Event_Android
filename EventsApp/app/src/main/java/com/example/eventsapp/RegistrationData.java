package com.example.eventsapp;

public class RegistrationData {

    String name;
    String surname;
    String image;
    String organization_id;
    String login;
    String password;
    int role;
    String current_event;
    int organization_verify;
    String phone;
    String web_link;
    String bio;

    public RegistrationData(String name, String surname, String image, String organization_id, int role,
                            String current_event, String login, String password,
                            int organization_verify, String phone, String web_link, String bio) {
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
}
