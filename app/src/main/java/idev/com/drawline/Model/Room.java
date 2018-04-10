/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idev.com.drawline.Model;


public class Room {

    private String room_code;
    private String room_name;
    private String type;
    private Double x;
    private Double y; 
    private Double z;
    private Double lati;
    private Double longi;
    private Latitude latitude;
    private Longitude longitude;

    public Room(String room_code, String room_name, String type, Double x, Double y, Double z, Latitude lati, Longitude longi) {
        this.room_code = room_code;
        this.room_name = room_name;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.latitude = lati;
        this.longitude = longi;
        this.lati = latitude.getDegree() + latitude.getMinute() / 60 + latitude.getSecond() / 3600;
        this.longi = longitude.getDegree() + longitude.getMinute() / 60 + longitude.getSecond() / 3600;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Room() {
    }

    public String getRoom_code() {
        return room_code;
    }

    public void setRoom_code(String room_code) {
        this.room_code = room_code;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public Double getLati() {
        return lati;
    }

    public void setLati(Double lati) {
        this.lati = lati;
    }

    public Double getLongi() {
        return longi;
    }

    public void setLongi(Double longi) {
        this.longi = longi;
    }

    public Latitude getLatitude() {
        return latitude;
    }

    public void setLatitude(Latitude latitude) {
        this.latitude = latitude;
    }

    public Longitude getLongitude() {
        return longitude;
    }

    public void setLongitude(Longitude longitude) {
        this.longitude = longitude;
    }
}
