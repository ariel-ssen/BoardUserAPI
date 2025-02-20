package com.example.demo.controller;


import com.example.demo.dto.WeatherResponseDto;
import com.example.demo.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/weather")
public class WeatherController {

    private final WeatherService airQualityService;

    @Autowired
    public WeatherController(WeatherService airQualityService) {
        this.airQualityService = airQualityService;
    }

    @GetMapping
    public WeatherResponseDto getAirQuality(@RequestParam(defaultValue = "111121") String msradmCode) {
        return airQualityService.getAirQuality(msradmCode);
    }
}
