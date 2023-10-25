package com.example.blog.repositories;

import com.example.blog.entities.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<Comment,String>
{
//    Translation findByEnglishAndHindiAndFrench(String english, String hindi, String french); //Optional
//    Translation findByEnglish(String english);
}
