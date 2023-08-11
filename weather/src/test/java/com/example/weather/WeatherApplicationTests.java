package com.example.weather;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class WeatherApplicationTests {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private UnixTimestampService unixTimestampService;

    @Mock
    private JsonOutputService jsonOutPut;


    @Test
    public void testFetchAndDisplayWeather_Success() throws Exception {
        // Arrange
        String city = "tehran";
        MockitoAnnotations.openMocks(this);
        WeatherServiceImpl weatherService=new WeatherServiceImpl();

        String apiKey = "9b655e4dcd04a2ffd1efe0c6d678650b";
        String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?q=" + city + "&appid=" + apiKey;

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("cod", "200");
        responseBody.put("list", new ArrayList<>());
        responseBody.put("city", new HashMap<>());

        ResponseEntity<Object> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);

        when(restTemplate.exchange(eq(apiUrl), eq(HttpMethod.GET), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(responseEntity);

        // Act
        WeatherDTO result = weatherService.fetchAndDisplayWeather(city);

        // Assert
        assertNotNull(result);
        // Add more assertions based on your expected behavior
    }

    @Test
    public void testFetchAndDisplayWeather_WrongCityName() {

        // Arrange
        String city = "";
        MockitoAnnotations.openMocks(this);
        WeatherServiceImpl weatherService=new WeatherServiceImpl();

        String apiKey = "9b655e4dcd04a2ffd1efe0c6d678650b";
        String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?q=" + city + "&appid=" + apiKey;

        when(restTemplate.exchange(eq(apiUrl), eq(HttpMethod.GET), any(HttpEntity.class), eq(Object.class)))
                .thenThrow(new RuntimeException("Mocked exception"));

        // Act & Assert
        assertThrows(Exception.class, () -> weatherService.fetchAndDisplayWeather(city));
    }


    @Test
    public void testGetNowAndFuture_Success() throws Exception {

        // Arrange
        String city = "tehran";
        WeatherServiceImpl weatherService=new WeatherServiceImpl();

        WeatherDTO weatherDTO = new WeatherDTO();
        // Initialize weatherDTO and other necessary objects here

        when(unixTimestampService.exchange(any(Long.class))).thenReturn("2023-08-10 15:30:00");
        when(jsonOutPut.jsonOutPut(eq(true), eq("200ok"), any())).thenReturn(ResponseEntity.ok().build());
        when(weatherService.fetchAndDisplayWeather("tehran")).thenReturn(weatherDTO);

        // Act
        ResponseEntity<Object> response = weatherService.getNowAndFuture(city);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testGetNowAndFuture_NullCity() {

        // Arrange
        String city = null;
        WeatherServiceImpl weatherService=new WeatherServiceImpl();

        // Act & Assert
        assertThrows(Exception.class, () -> weatherService.getNowAndFuture(city));
    }
}
