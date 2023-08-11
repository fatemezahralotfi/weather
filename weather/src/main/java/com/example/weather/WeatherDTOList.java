package com.example.weather;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WeatherDTOList {
    private String icon;
    private String description;
    private String main;
    private Integer id;
}
