package com.example.weather;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListDTO {

    private Integer dt;
    private String pop;
    private Integer visibility;
    private String dt_txt;
    private List<WeatherDTOList> weather;
    private List<MainDTO> main;
    private List<CloudsDTO> clouds;
    private List<SysDTO> sys;
    private List<WindDTO> wind;
}
