package com.example.weather;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ListOfWeatherDTO {
    private String date;
    private String description;
    private String mainTemperature;
    private String temperature;
    private String minTemperature;
    private String maxTemperature;
    private String seaLevel;
    private String numberOfClouds;
    private String windSpeed;
}
