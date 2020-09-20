package com.example.samplecountrydetails.model;

import android.os.Parcelable;

import java.io.Serializable;

public class CountryDetailsModel implements Serializable {
    private String flagImage;
    private String countryName;
    private String capital;
    private String region;
    private String subRegion;
    private Double area;
    private Integer population;

    public String getFlagImage() {
        return flagImage;
    }

    public void setFlagImage(String image) {
        this.flagImage = image;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubRegion() {
        return subRegion;
    }

    public void setSubRegion(String subRegion) {
        this.subRegion = subRegion;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }
}
