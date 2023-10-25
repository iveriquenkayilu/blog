package com.example.blog.models.comment;

import com.example.blog.entities.Comment;

public class CommentResponse
{
    private String Id;
    private String user;
    private String blogPostId;
    private String text;

    public CommentResponse(Comment comment){
        if(comment==null)
            return;
        this.Id = comment.getId();
        this.user = comment.getCommenter().getUsername2();
        this.blogPostId = comment.getBlogPost().getId();
        this.text= comment.getText();
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBlogPostId() {
        return blogPostId;
    }

    public void setBlogPostId(String blogPostId) {
        this.blogPostId = blogPostId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}