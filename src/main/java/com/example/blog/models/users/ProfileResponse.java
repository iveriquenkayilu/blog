package com.example.blog.models.users;

import com.example.blog.entities.User;
import com.example.blog.shared.helpers.StringHelper;

import java.time.LocalDate;

public class ProfileResponse
{
    private  String firstName;
    private  String lastName;
    private String image;
    private LocalDate birthDate;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public ProfileResponse(User user)
    {
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.image = StringHelper.GetFileUrl(user.getImageId());
        this.birthDate = user.birthDate;
        //this.secondaryLanguage=user.getSecondaryLanguage();
    }
   }
