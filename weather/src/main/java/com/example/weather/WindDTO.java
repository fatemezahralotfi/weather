package com.example.weather;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WindDTO {
    private Integer deg;
    private String speed;
    private String gust;
}
