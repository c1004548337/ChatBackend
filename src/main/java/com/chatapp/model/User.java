package com.chatapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    private String id;
    private String name;
    private String phone;
    private String password;
    private String avatar;
    private String bio;

    public User() {}

    public User(String id, String name, String phone, String password, String avatar, String bio) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.avatar = avatar;
        this.bio = bio;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
}
