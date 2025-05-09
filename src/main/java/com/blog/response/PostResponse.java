package com.blog.response;

import com.blog.domain.Post;
import lombok.Builder;
import lombok.Getter;


/**
 * 서비스 정책에 맞는 응답 클래스
 */
@Getter
@Builder
public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }

    @Builder
    public PostResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title.substring(0, Math.min(title.length(), 10)); // 요구사항에 맞게
        this.content = content;
    }
}
