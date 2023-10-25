package com.example.blog.repositories;

import com.example.blog.entities.File;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends MongoRepository<File,String>
{
    File findByNameAndContentTypeAndSizeInBytes(String name, String contentType, Long sizeInBytes);
}
