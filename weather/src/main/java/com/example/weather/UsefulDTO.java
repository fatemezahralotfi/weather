package com.example.weather;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsefulDTO {
    private String country;
    private String sunrise;
    private String timezone;
    private String sunset;
    private String name;
    private String population;
    List<ListOfWeatherDTO> weatherList;
}
