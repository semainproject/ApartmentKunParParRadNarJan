package com.example.narathorn.apartmentkunparparradnarjan;

/**
 * Created by narathorn on 26/1/2018 AD.
 */

public class UnitGetter {
    int electricOld, electricNew, waterOld, waterNew;

    public UnitGetter() {

    }

    public UnitGetter(int electricOld, int electricNew, int waterOld, int waterNew) {
        this.electricOld = electricOld;
        this.electricNew = electricNew;
        this.waterOld = waterOld;
        this.waterNew = waterNew;
    }

    public int getElectricOld() {
        return electricOld;
    }

    public int getElectricNew() {
        return electricNew;
    }

    public int getWaterOld() {
        return waterOld;
    }

    public int getWaterNew() {
        return waterNew;
    }
}
