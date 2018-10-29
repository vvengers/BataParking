package com.example.engersvanvincent.bataparking.Modules;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LotData implements Parcelable {
    private final String parkingLotId;
    private String parkingLotName;
    private int totParkingSpaces;
    private ArrayList<Car> parkedCarsId = new ArrayList<>();
    private ArrayList<Car> comingCarsId = new ArrayList<>();
    private int avaParkingTime = 0; //In minutes.
    private final String latitude;
    private final String longitude;
    private final String city;


    public LotData(String parkingLotId, String parkingLotName,  String city, int totParkingSpaces,
                   ArrayList<Car> parkedCarsId, ArrayList<Car> comingCarsId ,
                   String latitude, String longitude) {
        this.parkingLotId = parkingLotId;
        this.parkingLotName = parkingLotName;
        this.totParkingSpaces = totParkingSpaces;
        this.parkedCarsId = parkedCarsId;
        this.comingCarsId = comingCarsId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
    }


    protected LotData(Parcel in) {
        parkingLotId = in.readString();
        parkingLotName = in.readString();
        city = in.readString();
        totParkingSpaces = in.readInt();
        avaParkingTime = in.readInt();
        latitude = in.readString();
        longitude = in.readString();
    }

    public static final Creator<LotData> CREATOR = new Creator<LotData>() {
        @Override
        public LotData createFromParcel(Parcel in) {
            return new LotData(in);
        }

        @Override
        public LotData[] newArray(int size) {
            return new LotData[size];
        }
    };



    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getParkingLotId() {
        return parkingLotId;
    }

    public String getParkingLotName() {
        return parkingLotName;
    }

    public void setParkingLotName(String parkingLotName) {
        this.parkingLotName = parkingLotName;
    }

    public int getTotParkingSpaces() {
        return totParkingSpaces;
    }

    public void setTotParkingSpaces(int totParkingSpaces) {
        this.totParkingSpaces = totParkingSpaces;
    }

    public ArrayList<Car> getParkedCarsId() {
        return parkedCarsId;
    }

    public void setParkedCarsId(ArrayList<Car> parkedCarsId) {
        this.parkedCarsId = parkedCarsId;
    }

    public ArrayList<Car> getComingCarsId() {
        return comingCarsId;
    }

    public void setComingCarsId(ArrayList<Car> comingCarsId) {
        this.comingCarsId = comingCarsId;
    }

    public int getAvaParkingTime() {
        return avaParkingTime;
    }

    public void setAvaParkingTime(int avaParkingTime) {
        this.avaParkingTime = avaParkingTime;
    }


    public String getCity() {
        return city;
    }



//    /*
//        Creates a map from the fields to pass to the database.
//         */
//    public Map<String, Object> toDbObject(){
//        Map<String, Object> m = new HashMap<>();
//        m.put("parking_lot_id",parkingLotId);
//        m.put("parking_lot_name", parkingLotName);
//        m.put("tot_parking_spaces",  totParkingSpaces);
//        m.put("parked_cars_id", parkedCarsId);
//        m.put("coming_cars_id", comingCarsId);
//        m.put("ava_parking_time", avaParkingTime);
//        m.put("latitude", latitude);
//        m.put("longitude", longitude);
//        m.put("city", city);
//        return m;
//    }

    public static LotData createLdObject(Map<String, Object> m) {
        return new LotData(
                (String) m.get("parkingLotId"),
                (String) m.get("parkingLotName"),
                (String) m.get("city"),
                Integer.valueOf(String.valueOf(m.get("totParkingSpaces"))),
                (ArrayList<Car>) m.get("parkedCarsId"),
                (ArrayList<Car>) m.get("comingCarsId"),
                (String) m.get("latitude"),
                (String) m.get("longitude")
        );
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.parkingLotId);
        dest.writeString(this.parkingLotName);
        dest.writeInt(this.totParkingSpaces);
        dest.writeArray(new String[]{this.parkingLotId});
        dest.writeArray(new ArrayList[]{this.comingCarsId});
        dest.writeInt(avaParkingTime);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);

    }


}
