package com.blog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.blog.domain.Post;
import com.blog.repository.PostRepository;
import com.blog.request.PostCreate;
import com.blog.response.PostResponse;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1() {
        // given
        PostCreate postCreate = PostCreate.builder()
            .title("글 제목")
            .content("글 내용")
            .build();

        // when
        postService.write(postCreate);

        // then
        Assertions.assertEquals(1, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("글 제목", post.getTitle());
        assertEquals("글 내용", post.getContent());

    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() {
        // given
        Post requestPost = Post.builder()
            .title("제목")
            .content("내용")
            .build();
        postRepository.save(requestPost);

        // when
        PostResponse response = postService.get(requestPost.getId());

        // then
        assertNotNull(response);
        assertEquals(1, postRepository.count());
        assertEquals("제목", response.getTitle());
        assertEquals("내용", response.getContent());
    }

    @Test
    @DisplayName("글 여러개 조회")
    void test3() {
        // given
        Post requestPost1 = Post.builder()
            .title("제목1")
            .content("내용1")
            .build();
        postRepository.save(requestPost1);

        Post requestPost2 = Post.builder()
            .title("제목2")
            .content("내용2")
            .build();
        postRepository.save(requestPost2);

        // when
        List<PostResponse> posts = postService.getList();

        // then
        assertEquals(2, posts.size());
    }
}