package com.example.blog.services;

import com.example.blog.entities.BlogPost;
import com.example.blog.entities.User;
import com.example.blog.models.blog_post.BlogPostRequest;
import com.example.blog.models.blog_post.BlogPostResponse;
import com.example.blog.repositories.BlogPostRepository;
import com.example.blog.shared.exceptions.BlogException;
import com.example.blog.shared.helpers.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogPostService
{
    private  final BlogPostRepository BlogPostRepository;
    private  final  UserService userService;

    private static final Logger logger= LoggerFactory.getLogger(BlogPostService.class);
    @Autowired
    public BlogPostService(BlogPostRepository BlogPostRepository, UserService userService){

        this.BlogPostRepository = BlogPostRepository;
        this.userService = userService;
    }

    public List<BlogPostResponse> GetBlogPosts(){
        List<BlogPost> BlogPosts= BlogPostRepository.findAll();
        List<BlogPostResponse> result = BlogPosts.stream()
                .map(u -> new BlogPostResponse(u))
                .toList();
        return result;
    }

    public  BlogPostResponse GetBlogPost(String id) throws BlogException {
        logger.info("Getting BlogPost {}", id);
        var BlogPost= BlogPostRepository.findById(id)
                .orElseThrow(()-> new BlogException("BlogPost not found"));
        return  new BlogPostResponse(BlogPost);
    }

    public  BlogPostResponse CreateBlogPost(BlogPostRequest BlogPostRequest) throws BlogException {
        logger.info("User adding BlogPost with title {}, content {}"
                , BlogPostRequest.getTitle(),BlogPostRequest.getContent());

        ValidateBlogPostRequest(BlogPostRequest);

//        var BlogPost= BlogPostRepository.findByEnglishAndHindiAndFrench(BlogPostRequest.getEnglish(),BlogPostRequest.getHindi(),BlogPostRequest.getFrench());
//        if(BlogPost!=null){
//            logger.error("BlogPost already exists");
//            throw  new BlogException("BlogPost already exists");
//        }
        var user= userService.GetUserContext();
                //(User)SecurityContextHolder.getContext().getAuthentication().getDetails();
        var BlogPost= new BlogPost(BlogPostRequest.getTitle(),BlogPostRequest.getContent(),BlogPostRequest.getTags(),user);

        BlogPostRepository.save(BlogPost);
        return  new BlogPostResponse(BlogPost);
    }

    public  BlogPostResponse UpdateBlogPost(String id,BlogPostRequest BlogPostRequest) throws BlogException {
//        logger.info("User updating BlogPost {} with english {}, hindi {}, french {}"
//            ,id, BlogPostRequest.getEnglish(),BlogPostRequest.getHindi());

        ValidateBlogPostRequest(BlogPostRequest);

        var BlogPost= BlogPostRepository.findById(id)
                .orElseThrow(()-> new BlogException("BlogPost not find"));

        BlogPost.update(BlogPostRequest);
        BlogPostRepository.save(BlogPost);
        return new BlogPostResponse(BlogPost);
    }

    public  void Delete(String id) throws BlogException {
        logger.info("User deleting BlogPost {}",id);
        var BlogPost= BlogPostRepository.findById(id)
                .orElseThrow(()-> new BlogException("BlogPost not found"));
        //BlogPost.setDeletedAt(LocalDateTime.now()) ; BlogPostRepository.save(BlogPost);
        BlogPostRepository.delete(BlogPost);
        logger.info("BlogPost deleted successfully");
    }

    private  void ValidateBlogPostRequest(BlogPostRequest BlogPostRequest) throws BlogException {
        if(StringHelper.StringIsNullOrEmpty(BlogPostRequest.getTitle()))
            throw  new BlogException("Title is empty");
        if(StringHelper.StringIsNullOrEmpty(BlogPostRequest.getContent()))
            throw  new BlogException("Content is empty");
//        if(StringHelper.StringIsNullOrEmpty(BlogPostRequest.()))
//            throw new BlogException("French is empty");
    }
}
