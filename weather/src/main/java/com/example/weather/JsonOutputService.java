package com.example.weather;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class JsonOutputService {

    public ResponseEntity<Object> jsonOutPut(Boolean success, String message, Object data) {
        final JsonDTO responseDTO = new JsonDTO(success, message, data);
        return new ResponseEntity<Object>(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }
}