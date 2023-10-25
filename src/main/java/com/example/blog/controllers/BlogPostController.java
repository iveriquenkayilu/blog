package com.example.blog.controllers;

import com.example.blog.models.ResponseModel;
import com.example.blog.models.blog_post.BlogPostRequest;
import com.example.blog.services.BlogPostService;
import com.example.blog.shared.exceptions.BlogException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path="api/v1/blogPosts")
public class BlogPostController
{
    private  final BlogPostService blogPostService;
    private static final Logger logger= LoggerFactory.getLogger(BlogPostController.class);
    @Autowired
    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping()
    public ResponseEntity<ResponseModel> Get()
    {
        var blogPost=  blogPostService.GetBlogPosts();
        return blogPost==null? ResponseModel.Ok("No blog post found"):
                ResponseModel.Ok("Blog post fetched", blogPost);
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseModel> GetBlogPost(@PathVariable String id)
    {
        try{
            var blogPost= blogPostService.GetBlogPost(id);
            return ResponseModel.Ok("Blog post fetched", blogPost);
        }
        catch (BlogException e){
            logger.error(e.getMessage(),e);
            return  ResponseModel.Fail(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            return  ResponseModel.Fail("Error occured", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @RequestMapping(
//           // path = "/api/v2/blogPosts",
//            method = RequestMethod.POST,
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
//    )
    public ResponseEntity<ResponseModel> CreateWithFile(@ModelAttribute BlogPostRequest blogPostRequest)//, @ModelAttribute MultipartFile file)
    {
        try{
            var blogPost= blogPostService.CreateBlogPost(blogPostRequest);
            return ResponseModel.Ok("Blog post created successfully", blogPost);
        }
        catch (BlogException e){
            logger.error(e.getMessage(),e);
            return  ResponseModel.Fail(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            return  ResponseModel.Fail("Error occured", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<ResponseModel> Create(@RequestBody BlogPostRequest blogPostRequest)
    {
        try{
            var blogPost= blogPostService.CreateBlogPost(blogPostRequest);
            return ResponseModel.Ok("Blog post created successfully", blogPost);
        }
        catch (BlogException e){
            logger.error(e.getMessage(),e);
            return  ResponseModel.Fail(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            return  ResponseModel.Fail("Error occured", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseModel> Update(@RequestBody BlogPostRequest blogPostRequest,@PathVariable String id)
    {
        try{
            var blogPost= blogPostService.UpdateBlogPost(id,blogPostRequest);
            return ResponseModel.Ok("Blog post updated successfully", blogPost);
        }
        catch (BlogException e){
            return  ResponseModel.Fail(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            return  ResponseModel.Fail("Error occured", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseModel> Delete(@PathVariable String id)
    {
        try{
            blogPostService.Delete(id);
            return ResponseModel.Ok("Blog post deleted successfully", id);
        }
        catch (BlogException e){
            return  ResponseModel.Fail(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            return  ResponseModel.Fail("Error occured", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}