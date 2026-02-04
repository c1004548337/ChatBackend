package com.chatapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    private String id;
    private String userId;
    private String userName;
    private String content;

    public Comment() {}

    public Comment(String id, String userId, String userName, String content) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.content = content;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
