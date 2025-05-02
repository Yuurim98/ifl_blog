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
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청 시 hello를 출력한다")
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/posts"))
            .andExpect(status().isOk())
            .andExpect(content().string("hello"))
            .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청 시 빈값을 출력한다")
    void post() throws Exception {
        // given
        PostCreate postCreate = new PostCreate("글 제목", "글 내용~!");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(postCreate);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .contentType(APPLICATION_JSON)
                .content(json)
            )
            .andExpect(status().isOk())
            .andExpect(content().string("{}"))
            .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청 시 title 값은 필수이다")
    void post2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .contentType(APPLICATION_JSON)
                .content("{\"title\" : \"\", \"content\" : \"글 내용~!\"}")
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
        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .contentType(APPLICATION_JSON)
                .content("{\"title\" : \"글 제목\", \"content\" : \"글 내용~!\"}")
            )
            .andExpect(status().isOk())
            .andDo(print());

        // then
        assertEquals(1, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("글 제목", post.getTitle());
        assertEquals("글 내용~!", post.getContent());
    }


}