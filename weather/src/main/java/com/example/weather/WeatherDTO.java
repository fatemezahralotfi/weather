package com.example.weather;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class WeatherDTO {
    public List<CityDTO> city;
    public List<ListDTO> list;
}
