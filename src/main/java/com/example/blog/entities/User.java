package com.example.blog.entities;

import com.example.blog.entities.datatypes.Role;
import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Collection;
import java.util.List;

//@Entity
//@Table
@Document(collection = "users")
public class User extends  BaseEntity implements UserDetails
{
    //@Id
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
//    public Long id;
    public  String firstName;
    public  String lastName;
    public LocalDate birthDate;
    @Column(unique = true)
    public  String email;

    public  String Password; // Hashed

    private  File image;

    @Transient
    public Integer Age;

    @Enumerated(EnumType.STRING)
     private  Role Role;

    private  String username;


    public  User(String name, String surname, String email, LocalDate birthDay){

        this.firstName= name;
        this.lastName= surname;
        this.username=name +"."+surname;
        this.email=email;
        this.birthDate = birthDay;
        this.Age=getAge();
        this.CreatedAt= LocalDateTime.now();
        this.Role= com.example.blog.entities.datatypes.Role.User;
        this.setCreatedAt(LocalDateTime.now());
    }

    public User() {
    }

    public Integer getAge(){
        return Period.between(this.birthDate,LocalDate.now()).getYears();
    }
    public void setRole(Role role) {
        Role=role;
    }
    public String getEmail() {
        return email;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Role.name()));
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public  String getUsername2(){
        return  this.username;
    }

    @Override
    public String getUsername() {

        return email; //email
    }

    @Override
    public String getPassword() {
        return Password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Builder design pattern
    private User(UserBuilder builder) {
        this.firstName=builder.firstName;
        this.lastName=builder.lastName;
        this.username= builder.firstName +"."+builder.lastName;
        this.id =builder.Id;
        this.email=builder.Email;
        this.Password= builder.Password;
        this.Role=builder.Role;
        this.setCreatedAt(LocalDateTime.now());
    }

    public void setPassword(String password) {
        Password = password; // encrypt
    }

    public String getImageId() {
        return image==null? null: image.getId();
    }
    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    //Builder Class
    public static class UserBuilder{

        // required parameters
        private String firstName;
        private String lastName;

        // optional parameters
        private String Id;

        private String Email;
        private String Password;
        private  Role Role;

        public UserBuilder(String name, String surname){
            this.firstName=name;
            this.lastName=surname;
        }

        public UserBuilder setId(String id) {
            this.Id = id;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.Email = email;
            return this;
        }
        public UserBuilder setPassword(String password) {
            this.Password = password;
            return this;
        }
        public UserBuilder setRole(Role role) {
            this.Role = role;
            return this;
        }

        public User build(){
            return new User(this);
        }

    }
}
