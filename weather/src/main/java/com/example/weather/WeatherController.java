package com.example.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    public WeatherService weatherService;

    @GetMapping("/nowAndFuture")
    public ResponseEntity<Object> getWeather(@RequestParam String city) throws Exception {
        return weatherService.getNowAndFuture(city);
    }

    @GetMapping("/past")
    public ResponseEntity<Object> past(@RequestParam String city) throws Exception {
        return weatherService.pastWeather(city);
    }
}