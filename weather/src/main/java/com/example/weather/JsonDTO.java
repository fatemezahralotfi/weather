package com.example.weather;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class JsonDTO {

    private Boolean success;
    private String message;
    private Object data;

    public JsonDTO(Boolean success, String message, Object data) {
        super();
        this.success = success;
        this.message = message;
        this.data = data;
    }
}