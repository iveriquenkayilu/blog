package com.example.blog.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

@MappedSuperclass

public abstract class BaseEntity {

     //public UUID Id;

     //@Id
     @Indexed(unique=true)
     public String id;
     protected LocalDateTime CreatedAt;
     @Nullable
     protected LocalDateTime DeletedAt;
     protected String CreatorId;

     public String getId() {
          return id;
     }

     public void setId(String id) {
          this.id = id;
     }

     public LocalDateTime getCreatedAt() {
          return CreatedAt;
     }

     public void setCreatedAt(LocalDateTime createdAt) {
          CreatedAt = createdAt;
     }

     @Nullable
     public LocalDateTime getDeletedAt() {
          return DeletedAt;
     }

     public void setDeletedAt(@Nullable LocalDateTime deletedAt) {
          DeletedAt = deletedAt;
     }

     public String getCreatorId() {
          return CreatorId;
     }

     public void setCreatorId(String creatorId) {
          CreatorId = creatorId;
     }

//     @SequenceGenerator(
//             name = "language_sequence",
//             sequenceName = "language_sequence",
//             allocationSize = 1
//     )
//     @GeneratedValue(
//             strategy = GenerationType.SEQUENCE,
//             generator = "language_sequence"
//     )
}
