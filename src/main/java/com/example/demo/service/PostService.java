// src/main/java/com/example/demo/service/PostService.java
package com.example.demo.service;

import com.example.demo.dto.PostDto;
import com.example.demo.domain.Post;
import com.example.demo.domain.User; // User 임포트
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository; // UserRepository 임포트
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final String WEATHER_API_URL = "http://localhost:8080/api/v1/weather?msradmCode=";

    private String fetchWeatherData(String msradmCode) {
        String url = WEATHER_API_URL + msradmCode;
        return restTemplate.getForObject(url, String.class);
    }

    @Transactional
    public PostDto createPost(PostDto postDto, String msradmCode) {
        User user = userRepository.findById(postDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String weatherData = fetchWeatherData(msradmCode);

        Post post = postDto.toEntity();
        post.setUser(user);
        post.setWeatherInfo(weatherData);

        postRepository.save(post);
        return PostDto.fromEntity(post);
    }

    public PostDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return PostDto.fromEntity(post);
    }

    public List<PostDto> getPosts(int page, int size) {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(PostDto::fromEntity).toList();
    }

    @Transactional
    public PostDto updatePost(Long id, PostDto postDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        return PostDto.fromEntity(post);
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        postRepository.delete(post);
    }
}
