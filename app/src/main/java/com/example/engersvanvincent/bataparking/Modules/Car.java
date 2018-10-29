package com.example.engersvanvincent.bataparking.Modules;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Car {
    private Date estArrivalTime;
    private Date arrivedTime;
    private String licensePlate;
    private final boolean paid;

    public Car(boolean paid) {
        this.paid = paid;
    }


    public Date getEstArrivalTime() {
        return estArrivalTime;
    }

    public void setEstArrivalTime(Date estArrivalTime) {
        this.estArrivalTime = estArrivalTime;
    }

    public Date getArrivedTime() {
        return arrivedTime;
    }

    public void setArrivedTime(Date arrivedTime) {
        this.arrivedTime = arrivedTime;
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

    /*
            Creates a map from the fields to pass to the database.
             */
    public Map<String, Object> createDbObject(){
        Map<String, Object> m = new HashMap<>();
        m.put("est_arrival_time", estArrivalTime);
        m.put("license_plate", licensePlate);
        m.put("arrived_time", arrivedTime);
        m.put("paid", paid);
        return m;
    }
}
