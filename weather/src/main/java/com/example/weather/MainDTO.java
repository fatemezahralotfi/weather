package com.example.weather;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainDTO {
    private String temp;
    private String temp_min;
    private Integer grnd_level;
    private String temp_kf;
    private Integer humidity;
    private Integer pressure;
    private Integer sea_level;
    private String feels_like;
    private String temp_max;
}
