package com.safronova.api.entity;


import java.io.Serializable;

public class Location implements Serializable {
    private Street street;
    private String latitude;
    private String longitude;

    public Location(Street street, String latitude, String longitude) {
        this.street = street;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public Location(){}

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Location {");
        sb.append(" street = ").append(street);
        sb.append(", Latitude = ").append(latitude);
        sb.append(", Longitude = ").append(longitude);
        sb.append('}');
        return sb.toString();
    }
}

