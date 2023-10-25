package com.example.blog.controllers;

import com.example.blog.models.ResponseModel;
import com.example.blog.models.comment.CommentRequest;
import com.example.blog.services.CommentService;
import com.example.blog.shared.exceptions.BlogException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="api/v1/comments")
public class CommentController
{
    private  final CommentService commentService;
    private static final Logger logger= LoggerFactory.getLogger(CommentController.class);
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<ResponseModel> Get()
    {
            var comments= commentService.GetComments();
            return ResponseModel.Ok("Comments fetched", comments);
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseModel> GetComment(@PathVariable String id)
    {
        try{
            var comment= commentService.GetComment(id);
            return ResponseModel.Ok("Comment fetched", comment);
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
    public ResponseEntity<ResponseModel> Create(@RequestBody CommentRequest commentRequest)
    {
        try{
            var comment= commentService.CreateComment(commentRequest);
            return ResponseModel.Ok("Comment created successfully", comment);
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
    public ResponseEntity<ResponseModel> Update(@RequestBody CommentRequest commentRequest,@PathVariable String id)
    {
        try{
            var comment= commentService.UpdateComment(id,commentRequest);
            return ResponseModel.Ok("Comment updated successfully", comment);
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
            commentService.Delete(id);
            return ResponseModel.Ok("Comment deleted successfully", id);
        }
        catch (BlogException e){
            return  ResponseModel.Fail(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            return  ResponseModel.Fail("Error occured", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
