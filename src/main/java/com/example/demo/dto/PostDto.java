package com.example.demo.dto;

import lombok.*;
import com.example.demo.domain.Post;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private String weatherInfo;
    private Long userId;
//    private String msradmCode; // 지역 코드 ex) 중구 111121

    public static PostDto fromEntity(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .weatherInfo(post.getWeatherInfo())
                .userId(post.getUser().getId())
                .build();
    }

    public Post toEntity() {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .weatherInfo(this.weatherInfo)
                .build();
    }
}
