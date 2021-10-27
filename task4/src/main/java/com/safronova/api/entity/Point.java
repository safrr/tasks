package com.safronova.api.entity;

public class Point {
    private double longitude;
    private double latitude;

    public Point() {
    }

    public Point(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.longitude, longitude) == 0 &&
                Double.compare(point.latitude, latitude) == 0;
    }

    @Override
    public int hashCode() {
        int result = (int) longitude;
        result = (int) (31 * result + latitude);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Point {");
        sb.append(" Latitude = ").append(latitude);
        sb.append(", Longitude = ").append(longitude);
        sb.append('}');
        return sb.toString();
    }
}
