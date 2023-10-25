package com.example.blog.models.blog_post;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class BlogPostRequest
{
    private  String Title;
    private  String Content;
    private  List<String> Tags;
   // private  MultipartFile file;

    public BlogPostRequest(String title, String content, List<String> tags) {
        Title = title;
        Content = content;
        Tags = tags;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public List<String> getTags() {
        return Tags;
    }

    public void setTags(List<String> tags) {
        Tags = tags;
    }

//    public MultipartFile getFile() {
//        return file;
//    }
//
//    public void setFile(MultipartFile file) {
//        this.file = file;
//    }
}
