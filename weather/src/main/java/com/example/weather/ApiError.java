package com.example.weather;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Lotfi.Fatemehzahra
 */
@Getter
@Setter
public class ApiError {
    private Boolean success;
    private String message;
    private Object data;
    public ApiError(Boolean status, String message, Object data) {
        super();
        this.success = status;
        this.message = message;
        this.data = data;
    }
}
