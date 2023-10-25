package com.example.blog.repositories;

import com.example.blog.entities.BlogPost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepository extends MongoRepository<BlogPost,String>
{
//    @Query(value = "SELECT * FROM fun_fact ORDER BY RAND() LIMIT 1", nativeQuery = true)
//    Optional<FunFact> findRandom();
//    FunFact findByTitle_IdAndDescription_Id(Long titleId, Long descriptionId);
}

