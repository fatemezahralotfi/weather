package com.example.weather;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class UnixTimestampService {

    public String exchange(long unixTimestamp) {

        Instant instant = Instant.ofEpochSecond(unixTimestamp);
        LocalDateTime localDateTime = instant.atZone(ZoneId.of("UTC")).toLocalDateTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localDateTime.format(formatter);

        System.out.println("Unix Timestamp: " + unixTimestamp);
        System.out.println("Formatted Date and Time (UTC): " + formattedDateTime);

        return formattedDateTime;

    }
}
