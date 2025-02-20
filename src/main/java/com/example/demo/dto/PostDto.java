package com.example.demo.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import com.example.demo.domain.Post;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private Map<String, String> weatherInfo;
    private Long userId;

    public static PostDto fromEntity(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .weatherInfo(extractWeatherInfo(post.getWeatherInfo()))
                .userId(post.getUser().getId())
                .build();
    }

    public Post toEntity() {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .weatherInfo(convertToJsonString(this.weatherInfo))
                .build();
    }
    // extractWeatherInfo() → JSON 문자열에서 필요한 값만 추출하여 Map<String, String>으로 변환
    private static Map<String, String> extractWeatherInfo(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonString);

            // row 라는 0번째 가져오기
            JsonNode rowNode = root.path("ListAirQualityByDistrictService").path("row").get(0);

            if (rowNode != null) {
                Map<String, String> weatherData = new HashMap<>();
                weatherData.put("MSRDATE", rowNode.path("MSRDATE").asText()); // 날짜
                weatherData.put("MSRSTENAME", rowNode.path("MSRSTENAME").asText());// 지역
                weatherData.put("POLLUTANT", rowNode.path("POLLUTANT").asText()); // 주요 오염물질
                weatherData.put("PM10", rowNode.path("PM10").asText()); // 미세먼지
                weatherData.put("PM25", rowNode.path("PM25").asText()); // 초미세먼지
                return weatherData;
            }
        } catch (IOException e) {
            return null; // JSON 파싱 실패 시 null 반환
        }
        return null;
    }

    // extractWeatherInfo() -> Map<String, String>을 JSON 문자열로 변환하여 DB에 저장
    private static String convertToJsonString(Map<String, String> weatherInfo) {
        if (weatherInfo == null) return null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(weatherInfo);
        } catch (Exception e) {
            return "{}"; // 변환 실패 시 빈 JSON 반환
        }
    }
}

