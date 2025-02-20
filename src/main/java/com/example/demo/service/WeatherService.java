package com.example.demo.service;


import com.example.demo.dto.WeatherResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    @Value("${api.seoul.key}") // application.yml에 API KEY 저장
    private String apiKey;

    private final String BASE_URL = "http://openapi.seoul.go.kr:8088";

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherResponseDto getAirQuality(String msradmCode) {
        String url = BASE_URL + "/" + apiKey + "/json/ListAirQualityByDistrictService/1/5/" + msradmCode;
        return restTemplate.getForObject(url, WeatherResponseDto.class);
    }
}

