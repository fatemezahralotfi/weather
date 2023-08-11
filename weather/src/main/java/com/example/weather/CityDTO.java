package com.example.weather;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;

@Getter
@Setter
public class CityDTO {

    private String country;
    private LinkedHashMap coord;
    private Integer sunrise;
    private Integer timezone;
    private Integer sunset;
    private String name;
//    private Integer id;
    private Integer population;
}
