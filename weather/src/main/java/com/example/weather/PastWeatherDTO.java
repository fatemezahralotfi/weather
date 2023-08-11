package com.example.weather;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PastWeatherDTO {

    private String timezone;
    private String name;
//    private LinkedHashMap coord;
    private String date;
    private String visibility;
    private String description;
    private String temperature;
    private String minTemperature;
    private String maxTemperature;
    private String numberOfClouds;
    private String windSpeed;
    private String feelsLike;
    private String pressure;
    private String humidity;
}
