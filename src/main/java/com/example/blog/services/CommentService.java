package com.example.blog.services;

import com.example.blog.entities.Comment;
import com.example.blog.models.comment.CommentRequest;
import com.example.blog.models.comment.CommentResponse;
import com.example.blog.repositories.BlogPostRepository;
import com.example.blog.repositories.CommentRepository;
import com.example.blog.repositories.UserRepository;
import com.example.blog.shared.exceptions.BlogException;
import com.example.blog.shared.helpers.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService
{
    private  final CommentRepository commentRepository;
    private  final UserRepository userRepository;
    private  final BlogPostRepository blogPostRepository;
    private  final  UserService userService;

    private static final Logger logger= LoggerFactory.getLogger(CommentService.class);
    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository, BlogPostRepository blogPostRepository, UserService userService){

        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.blogPostRepository = blogPostRepository;
        this.userService = userService;
    }

    public List<CommentResponse> GetComments(){
        List<Comment> comments= commentRepository.findAll();
        List<CommentResponse> result = comments.stream()
                .map(u -> new CommentResponse(u))
                .toList();
        return result;
    }

    public CommentResponse GetComment(String id) throws BlogException {
        logger.info("Getting comment {}", id);
        var comment= commentRepository.findById(id)
                .orElseThrow(()-> new BlogException("Comment not found"));
        return  new CommentResponse(comment);
    }

    public CommentResponse CreateComment(CommentRequest commentRequest) throws BlogException {
//        logger.info("User adding comment with english {}, hindi {}, french {}"
//                , commentRequest.getEnglish(),commentRequest.getHindi(), commentRequest.getFrench());

        ValidateCommentRequest(commentRequest);

//        var comment= commentRepository.findByEnglishAndHindiAndFrench(commentRequest.getEnglish(),commentRequest.getHindi(),commentRequest.getFrench());
//        if(comment!=null){
//            logger.error("Comment already exists");
//            throw  new BlogException("Comment already exists");
//        }
        var user= userService.GetUserContext();
//                userRepository.findById(commentRequest.getUserId())
//                .orElseThrow(()-> new BlogException("User not found"));
        var blogPost= blogPostRepository.findById(commentRequest.getBlogPostId())
                .orElseThrow(()-> new BlogException("Blog post not found"));

       var comment= new Comment(user,blogPost,commentRequest.getText());

        commentRepository.save(comment);
        return  new CommentResponse(comment);
    }

    public CommentResponse UpdateComment(String id, CommentRequest commentRequest) throws BlogException {
//        logger.info("User updating comment {} with english {}, hindi {}, french {}"
//            ,id, commentRequest.getEnglish(),commentRequest.getHindi(), commentRequest.getFrench());

        ValidateCommentRequest(commentRequest);

        var comment= commentRepository.findById(id)
                .orElseThrow(()-> new BlogException("Comment not find"));

        comment.Update(commentRequest.getText());
        commentRepository.save(comment);
        return new CommentResponse(comment);
    }

    public  void Delete(String id) throws BlogException {
        logger.info("User deleting comment {}",id);
        var comment= commentRepository.findById(id)
                .orElseThrow(()-> new BlogException("Comment not find"));
        //comment.setDeletedAt(LocalDateTime.now()) ; commentRepository.save(comment);
        commentRepository.delete(comment);
        logger.info("Comment deleted successfully");
    }

    private  void ValidateCommentRequest(CommentRequest commentRequest) throws BlogException {
        if(StringHelper.StringIsNullOrEmpty(commentRequest.getText()))
            throw  new BlogException("Text is empty");
//        if(StringHelper.StringIsNullOrEmpty(commentRequest.()))
//            throw  new BlogException("User id is empty");
        if(StringHelper.StringIsNullOrEmpty(commentRequest.getBlogPostId()))
            throw new BlogException("Blog post is is empty");
    }
}
