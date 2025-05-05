package com.blog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.blog.domain.Post;
import com.blog.repository.PostRepository;
import com.blog.request.PostCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청 시 빈값을 출력한다")
    void post() throws Exception {
        // given
        PostCreate postCreate = PostCreate.builder()
            .title("글 제목")
            .content("글 내용~!")
            .build();

        String json = objectMapper.writeValueAsString(postCreate);

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .contentType(APPLICATION_JSON)
                .content(json)
            )
            .andExpect(status().isOk())
            .andExpect(content().string(""))
            .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청 시 title 값은 필수이다")
    void post2() throws Exception {
        // given
        PostCreate postCreate = PostCreate.builder()
            .content("글 내용~!")
            .build();

        String json = objectMapper.writeValueAsString(postCreate);

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .contentType(APPLICATION_JSON)
                .content(json)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
            .andExpect(jsonPath("$.validation.title").value("title은 필수입니다."))
            .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청 시 db에 값이 저장된다")
    void post3() throws Exception {
        // given
        PostCreate postCreate = PostCreate.builder()
            .title("글 제목")
            .content("글 내용~!")
            .build();

        String json = objectMapper.writeValueAsString(postCreate);

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .contentType(APPLICATION_JSON)
                .content(json)
            )
            .andExpect(status().isOk())
            .andDo(print());

        // then
        assertEquals(1, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("글 제목", post.getTitle());
        assertEquals("글 내용~!", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception {
        // given
        Post post = Post.builder()
            .title("제목")
            .content("내용")
            .build();
        postRepository.save(post);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}", post.getId())
                .contentType(APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(post.getId()))
            .andExpect(jsonPath("$.title").value("제목"))
            .andExpect(jsonPath("$.content").value("내용"))
            .andDo(print());
    }


}