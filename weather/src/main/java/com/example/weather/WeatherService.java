package com.example.weather;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface WeatherService {
    WeatherDTO fetchAndDisplayWeather(String city) throws Exception;
    ResponseEntity<Object> getNowAndFuture(String city) throws Exception;
    ResponseEntity<Object> pastWeather(String city) throws Exception;
}
