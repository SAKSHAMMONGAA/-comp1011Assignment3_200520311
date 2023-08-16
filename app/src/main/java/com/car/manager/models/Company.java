package com.car.manager.models;

public class Company {
    private String name;
    private String logoImage;
    private String streetName;
    private String city;
    private String province;
    private String postalCode;
    private int carsSold;
    private double totalProfit;

    public Company(String name, String logoImage, String streetName, String city, String province, String postalCode, int carsSold, double totalProfit) {
        this.name = name;
        this.logoImage = logoImage;
        this.streetName = streetName;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.carsSold = carsSold;
        this.totalProfit = totalProfit;
    }

    public Company() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public int getCarsSold() {
        return carsSold;
    }

    public void setCarsSold(int carsSold) {
        this.carsSold = carsSold;
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", logoImage='" + logoImage + '\'' +
                ", streetName='" + streetName + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", carsSold=" + carsSold +
                ", totalProfit=" + totalProfit +
                '}';
    }
}
