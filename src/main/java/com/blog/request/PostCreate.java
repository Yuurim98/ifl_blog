package com.blog.request;

import lombok.Setter;

@Setter
public class PostCreate {

    private String title;
    private String content;
    

    @Override
    public String toString() {
        return "PostCreate{" +
            "title='" + title + '\'' +
            ", content='" + content + '\'' +
            '}';
    }
}
