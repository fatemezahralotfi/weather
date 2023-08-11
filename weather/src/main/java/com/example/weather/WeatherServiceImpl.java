package com.example.weather;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    public JsonOutputService jsonOutPut;

    @Autowired
    public UnixTimestampService unixTimestampService;

    private static final Logger logger = Logger.getLogger(WeatherServiceImpl.class);

    @Override
    public WeatherDTO fetchAndDisplayWeather(String city) throws Exception {

        String apiKey = "9b655e4dcd04a2ffd1efe0c6d678650b";
        String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?q=" + city + "&appid=" + apiKey;

        logger.error(apiUrl);

        Map<String, Object> params = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Map> httpEntity = new HttpEntity(params, headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        try {
            ResponseEntity<Object> responseObject = restTemplate.exchange(apiUrl, HttpMethod.POST, httpEntity, Object.class);

            WeatherDTO weatherDTO = new WeatherDTO();
            CityDTO cityDTO = new CityDTO();
            List<CityDTO> cityDTOS = new ArrayList<>();
            List<ListDTO> listDTOS = new ArrayList<>();

            Map<String, Object> result = new HashMap<>((Map<? extends String, ? extends List<LinkedHashMap<String, String>>>) (Objects.requireNonNull(responseObject.getBody())));

            try {
                String code = (String) result.get("cod");
                if (code.equals("200")) {

                    List<LinkedHashMap<String, Object>> weatherList = (List<LinkedHashMap<String, Object>>) result.get("list");

                    LinkedHashMap<String, Object> cityList = (LinkedHashMap) result.get("city");
                    cityDTO.setSunset((Integer) cityList.get("sunset"));
                    cityDTO.setSunrise((Integer) cityList.get("sunrise"));
                    cityDTO.setTimezone((Integer) cityList.get("timezone"));
                    cityDTO.setCountry((String) cityList.get("country"));
                    cityDTO.setName((String) cityList.get("name"));
                    cityDTO.setPopulation((Integer) cityList.get("population"));
                    cityDTO.setCoord((LinkedHashMap) cityList.get("coord"));
                    cityDTOS.add(cityDTO);
                    weatherDTO.setCity(cityDTOS);

                    for (LinkedHashMap<String, Object> object : weatherList) {

                        ListDTO listDTO = new ListDTO();
                        listDTO.setDt_txt((String) object.get("dt_txt"));
                        listDTO.setDt((Integer) object.get("dt"));
                        listDTO.setPop(object.get("pop") != null ? object.get("pop").toString() : "");
                        listDTO.setVisibility((Integer) object.get("visibility"));

                        List<LinkedHashMap<String, Object>> weather = (ArrayList) object.get("weather");
                        List<WeatherDTOList> weatherDTOLists = new ArrayList<>();
                        WeatherDTOList weatherDTOList = new WeatherDTOList();
                        weatherDTOList.setDescription((String) weather.get(0).get("description"));
                        weatherDTOList.setIcon((String) weather.get(0).get("icon"));
                        weatherDTOList.setMain((String) weather.get(0).get("main"));
                        weatherDTOLists.add(weatherDTOList);
                        listDTO.setWeather(weatherDTOLists);

                        LinkedHashMap<String, Object> main = (LinkedHashMap<String, Object>) object.get("main");
                        List<MainDTO> mainDTOS = new ArrayList<>();
                        MainDTO mainDTO = new MainDTO();
                        mainDTO.setTemp(main.get("temp") != null ? main.get("temp").toString() : "");
                        mainDTO.setTemp_min(main.get("temp_main") != null ? main.get("temp_main").toString() : "");
                        mainDTO.setGrnd_level((Integer) main.get("grnd_level"));
                        mainDTO.setTemp_kf(main.get("temp_kf") != null ? main.get("temp_kf").toString() : "");
                        mainDTO.setHumidity((Integer) main.get("humidity"));
                        mainDTO.setPressure((Integer) main.get("pressure"));
                        mainDTO.setSea_level((Integer) main.get("sea_level"));
                        mainDTO.setFeels_like(main.get("feels_like") != null ? main.get("feels_like").toString() : "");
                        mainDTO.setTemp_max(main.get("temp_max") != null ? main.get("temp_max").toString() : "");
                        mainDTO.setTemp_min(main.get("temp_min") != null ? main.get("temp_min").toString() : "");
                        mainDTOS.add(mainDTO);
                        listDTO.setMain(mainDTOS);

                        LinkedHashMap<String, Object> clouds = (LinkedHashMap<String, Object>) object.get("clouds");
                        List<CloudsDTO> cloudsDTOList = new ArrayList<>();
                        CloudsDTO cloudsDTO = new CloudsDTO();
                        cloudsDTO.setAll((Integer) clouds.get("all"));
                        cloudsDTOList.add(cloudsDTO);
                        listDTO.setClouds(cloudsDTOList);

                        LinkedHashMap<String, Object> sys = (LinkedHashMap<String, Object>) object.get("sys");
                        List<SysDTO> sysDTOList = new ArrayList<>();
                        SysDTO sysDTO = new SysDTO();
                        sysDTO.setPod((String) sys.get("pod"));
                        sysDTOList.add(sysDTO);
                        listDTO.setSys(sysDTOList);

                        LinkedHashMap<String, Object> wind = (LinkedHashMap<String, Object>) object.get("wind");
                        List<WindDTO> windDTOS = new ArrayList<>();
                        WindDTO windDTO = new WindDTO();
                        windDTO.setDeg((Integer) wind.get("deg"));
                        windDTO.setGust(wind.get("gust") != null ? wind.get("gust").toString() : "");
                        windDTO.setSpeed(wind.get("speed") != null ? wind.get("speed").toString() : "");
                        windDTOS.add(windDTO);
                        listDTO.setWind(windDTOS);

                        listDTOS.add(listDTO);
                    }
                    weatherDTO.setList(listDTOS);

                    return weatherDTO;
                }
            } catch (Exception e) {
                throw new Exception();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return null;
    }

    @Override
    public ResponseEntity<Object> getNowAndFuture(String city) throws Exception {

        if (city != null) {
            WeatherDTO weatherDTO = fetchAndDisplayWeather(city);
            UsefulDTO usefulDTO = new UsefulDTO();
            usefulDTO.setCountry(weatherDTO.getCity().get(0).getCountry());
            usefulDTO.setSunrise(unixTimestampService.exchange((weatherDTO.getCity().get(0).getSunrise())));
            usefulDTO.setTimezone(unixTimestampService.exchange(weatherDTO.getCity().get(0).getTimezone()));
            usefulDTO.setSunset(unixTimestampService.exchange(weatherDTO.getCity().get(0).getSunset()));
            usefulDTO.setName(weatherDTO.getCity().get(0).getName());
            usefulDTO.setPopulation(weatherDTO.getCity().get(0).getPopulation().toString());
            List<ListOfWeatherDTO> list = new ArrayList<>();
            List<ListDTO> listDTOS = weatherDTO.getList();
            for (ListDTO dto : listDTOS) {
                ListOfWeatherDTO dto1 = new ListOfWeatherDTO();
                dto1.setDate(dto.getDt_txt());
                dto1.setDescription(dto.getWeather().get(0).getDescription());
                dto1.setMainTemperature(dto.getWeather().get(0).getMain());

                double centigradeTemp = Double.parseDouble(dto.getMain().get(0).getTemp()) - 273.15;

                dto1.setTemperature(Double.toString(centigradeTemp) + " C");
                dto1.setMinTemperature(dto.getMain().get(0).getTemp_min());
                dto1.setMaxTemperature(dto.getMain().get(0).getTemp_max());
                dto1.setSeaLevel(dto.getMain().get(0).getSea_level().toString());
                dto1.setNumberOfClouds(dto.getClouds().get(0).getAll().toString());
                dto1.setWindSpeed(dto.getWind().get(0).getSpeed());
                list.add(dto1);
            }
            usefulDTO.setWeatherList(list);
            return jsonOutPut.jsonOutPut(true, "200ok", usefulDTO);
        } else {
            throw new Exception("city name is null");
        }
    }

    @Override
    public ResponseEntity<Object> pastWeather(String city) throws Exception {

        List<PastWeatherDTO> weatherList = new ArrayList<>();

        for (int i = 1; i < 6; i++) {

            PastWeatherDTO dto = new PastWeatherDTO();
            LocalDate date = LocalDate.now();
            String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&dt=" + date.minusDays(i).toString() + "&appid=" + "9b655e4dcd04a2ffd1efe0c6d678650b";
            logger.error(url);

            Map<String, Object> params = new HashMap<>();
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<Map> httpEntity = new HttpEntity(params, headers);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            try {
                ResponseEntity<Object> responseObject = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Object.class);
                Map<String, Object> result = new HashMap<>((Map<? extends String, ? extends List<LinkedHashMap<String, String>>>) (Objects.requireNonNull(responseObject.getBody())));

                try {
                    Integer code = (Integer) result.get("cod");
                    if (code.equals(200)) {

                        Integer timezone = (Integer) result.get("timezone");
                        dto.setTimezone(timezone != null ? unixTimestampService.exchange(timezone) : "");
                        dto.setName(result.get("name") != null ? result.get("name").toString() : "");
//                        dto.setCoord((LinkedHashMap) result.get("coord"));
                        dto.setDate(date.minusDays(i).toString());
                        dto.setVisibility(result.get("visibility") != null ? result.get("visibility").toString() : "");

                        List<LinkedHashMap<String, Object>> weather = (ArrayList) result.get("weather");
                        LinkedHashMap<String, Object> main = (LinkedHashMap<String, Object>) result.get("main");
                        LinkedHashMap<String, Object> clouds = (LinkedHashMap<String, Object>) result.get("clouds");
//                        LinkedHashMap<String, Object> sys = (LinkedHashMap<String, Object>) result.get("sys");
                        LinkedHashMap<String, Object> wind = (LinkedHashMap<String, Object>) result.get("wind");

                        dto.setDescription((String) weather.get(0).get("description"));
                        dto.setFeelsLike(main.get("feels_like") != null ? main.get("feels_like").toString() : "");
                        dto.setPressure(main.get("pressure") != null ? main.get("pressure").toString() : "");
                        dto.setHumidity(main.get("humidity") != null ? main.get("humidity").toString() : "");
                        dto.setTemperature(main.get("temp") != null ? main.get("temp").toString() : "");
                        dto.setMinTemperature(main.get("temp_min") != null ? main.get("temp_min").toString() : "");
                        dto.setMaxTemperature(main.get("temp_max") != null ? main.get("temp_max").toString() : "");
                        dto.setNumberOfClouds(clouds.get("all") != null ? clouds.get("all").toString() : "");
                        dto.setWindSpeed(wind.get("speed") != null ? wind.get("speed").toString() : "");
                        weatherList.add(dto);
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    throw new Exception(e.getMessage());
                }

            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new Exception(e.getMessage());
            }
        }
        return jsonOutPut.jsonOutPut(true, "200ok", weatherList);
    }
}
