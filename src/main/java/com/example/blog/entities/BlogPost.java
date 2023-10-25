package com.example.blog.entities;

import com.example.blog.models.blog_post.BlogPostRequest;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

//@Entity
//@Table
@Document(collection = "blogPosts")
public class BlogPost extends  BaseEntity
{
//    @Id
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
//    private String Id;
    private String title;
    private String content;
    //private File image;
    private List<String> tags;
    private List<Comment> comments;
    private  User author;
    public BlogPost(){}
    public BlogPost(String title, String content,List<String> tags, User user) {
    this.title = title;
    this.content = content;
    this.author = user ;
    this.tags= tags;
    this.setCreatedAt(LocalDateTime.now());
   }

    public void  update(BlogPostRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.tags = request.getTags();
       // this.image = request.getImage();
    }
    public void  update(String title, String content, File image) {
        this.title = title;
        this.content = content;
        //this.category = category;
        //this.image = image;
    }

//    public String getId() {
//        return Id;
//    }
//
//    public void setId(String id) {
//        Id = id;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

//    public File getImage() {
//        return image;
//    }
//
//    public void setImage(File image) {
//        this.image = image;
//    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
