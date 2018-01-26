package com.example.narathorn.apartmentkunparparradnarjan;

/**
 * Created by narathorn on 26/1/2018 AD.
 */

public class SettingGetter {
    int electric, water, roomPrice;

    public SettingGetter() {

    }

    public SettingGetter(int electric, int water, int roomPrice) {
        this.electric = electric;
        this.water = water;
        this.roomPrice = roomPrice;
    }

    public int getElectric() {
        return electric;
    }

    public int getWater() {
        return water;
    }

    public int getRoomPrice() {
        return roomPrice;
    }
}
