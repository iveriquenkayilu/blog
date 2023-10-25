package com.example.blog.models.blog_post;

import com.example.blog.entities.BlogPost;

import java.util.List;

public class BlogPostResponse
{
    private String Id;
    private  String Title;
    private  String Content;
    private  List<String> Tags;
    private String author;

    public BlogPostResponse(String id, String title, String content,List<String> tags) {
        Id = id;
        Title = title;
        Tags = tags;
        Content = content;
    }
    public BlogPostResponse(BlogPost blogPost){
        Id= blogPost.getId();
        Title=blogPost.getTitle();
        Tags=blogPost.getTags();
        Content=blogPost.getContent();
        author= blogPost.getAuthor().getUsername2();
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public List<String> getTags() {
        return Tags;
    }

    public void setTags(List<String> tags) {
        Tags = tags;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
