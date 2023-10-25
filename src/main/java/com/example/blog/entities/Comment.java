package com.example.blog.entities;

import com.example.blog.models.comment.CommentRequest;
import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

//@Entity
//@Table
@Document(collection = "comments")
public class Comment extends  BaseEntity
{
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    private String Id;

    private User commenter;
    private BlogPost blogPost;
    private String text;

    public Comment(){}

    public Comment(User commenter, BlogPost blogPost, String text) {
        this.commenter = commenter;
        this.blogPost = blogPost;
        this.text = text;
    }

    public void Update(String text) {
        this.text = text;
    }

//    public String getId() {
//        return Id;
//    }
//
//    public void setId(String id) {
//        Id = id;
//    }

    public User getCommenter() {
        return commenter;
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }

    public BlogPost getBlogPost() {
        return blogPost;
    }

    public void setBlogPost(BlogPost blogPost) {
        this.blogPost = blogPost;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
