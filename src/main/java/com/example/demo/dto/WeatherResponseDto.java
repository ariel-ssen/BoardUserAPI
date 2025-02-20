package com.example.demo.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class WeatherResponseDto {
    @JsonProperty("ListAirQualityByDistrictService")
    private AirQualityData data;

    @Data
    public static class AirQualityData {
        @JsonProperty("row")
        private List<AirQualityItem> items;
    }

    @Data
    public static class AirQualityItem {
        @JsonProperty("MSRDATE") private String date;
        @JsonProperty("MSRSTENAME") private String district;
        @JsonProperty("POLLUTANT") private String pollutant;
        @JsonProperty("PM10") private String pm10;
        @JsonProperty("PM25") private String pm25;
    }
}
