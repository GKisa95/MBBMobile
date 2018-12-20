package com.mbb.gk.mbbmobile.EczanePackage;

public class Eczane {
    private String name;
    private String address;
    private String phone;
    private String district;
    private double latitude;
    private double longitude;

    public Eczane(String name, String address, String phone, String district) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.district = district;
    }

    public Eczane(String name, String address, String phone, String district, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.district = district;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
