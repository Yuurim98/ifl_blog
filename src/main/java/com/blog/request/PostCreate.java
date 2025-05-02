package com.blog.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PostCreate {

    @NotBlank(message = "title은 필수입니다.")
    private String title;

    @NotBlank(message = "content는 필수입니다.")
    private String content;

    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
