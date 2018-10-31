package com.example.engersvanvincent.bataparking.Modules;

import java.util.HashMap;
import java.util.Map;

public class Car {
    private String licensePlate;
    private final boolean paid;

    public Car(boolean paid) {
        this.paid = paid;
    }


    public boolean isPaid() {
        return paid;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
