package com.example.blog.models;

import com.example.blog.entities.User;
import com.example.blog.shared.helpers.StringHelper;

import java.time.LocalDate;
import java.time.Period;

public class UserModel {
    public String id;
    public  String firstName;
    public  String lastName;
    public LocalDate birthDate;
    public  String email;

    private String image;

    public Integer age;

    public UserModel(String id, String name, String surname, LocalDate birthDay, String email, Integer age, String imageId) {
        this.id = id;
        this.firstName= name;
        this.lastName= surname;
        this.email=email;
        this.birthDate = birthDay;
        this.age = age==null? getAge(birthDay): age;
        this.image= StringHelper.GetFileUrl(imageId);
    }
    public  UserModel(User user)
    {   this.id = user.id;
        this.firstName= user.firstName;
        this.lastName= user.lastName;
        this.email=user.getEmail();
        this.birthDate = user.birthDate;
        this.age = age==null? getAge(birthDate): age;
        this.image= StringHelper.GetFileUrl(user.getImageId());
    }

    private  Integer getAge(LocalDate birthDay){

        if(birthDay==null)
            return null;
        Period age= Period.between(birthDay, LocalDate.now());
        return age.getYears();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
   }
