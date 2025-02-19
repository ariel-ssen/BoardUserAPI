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

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository; // UserRepository 추가

    // 게시글 생성
    @Transactional
    public PostDto createPost(PostDto postDto) {
        // User 객체 조회
        User user = userRepository.findById(postDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Post 객체에 User 설정
        Post post = postDto.toEntity();
        post.setUser(user); // User 설정

        postRepository.save(post); // Post 저장

        return PostDto.fromEntity(post); // PostDto 반환
    }

    // 게시글 조회 (단건)
    public PostDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return PostDto.fromEntity(post);
    }

    // 게시글 조회 (페이징)
    public List<PostDto> getPosts(int page, int size) {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(PostDto::fromEntity).toList();
    }

    // 게시글 수정
    @Transactional
    public PostDto updatePost(Long id, PostDto postDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        return PostDto.fromEntity(post);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        postRepository.delete(post);
    }
}
