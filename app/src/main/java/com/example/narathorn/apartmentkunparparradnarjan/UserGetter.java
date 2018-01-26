package com.example.narathorn.apartmentkunparparradnarjan;

/**
 * Created by narathorn on 26/1/2018 AD.
 */

public class UserGetter {
    String name, password;

    public UserGetter() {

    }

    public UserGetter(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
