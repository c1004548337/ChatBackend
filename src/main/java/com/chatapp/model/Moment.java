package com.chatapp.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "moments")
public class Moment {
    @Id
    private String id;
    private String userId;
    private String userName;
    private String userAvatar;
    
    @Column(length = 2000)
    private String content;
    
    @ElementCollection
    @CollectionTable(name = "moment_images", joinColumns = @JoinColumn(name = "moment_id"))
    @Column(name = "image_url")
    private List<String> images;
    
    private long timestamp;
    private int likes;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "moment_id")
    private List<Comment> comments;

    public Moment() {}

    public Moment(String id, String userId, String userName, String userAvatar, String content, List<String> images, long timestamp, int likes, List<Comment> comments) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.userAvatar = userAvatar;
        this.content = content;
        this.images = images;
        this.timestamp = timestamp;
        this.likes = likes;
        this.comments = comments;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserAvatar() { return userAvatar; }
    public void setUserAvatar(String userAvatar) { this.userAvatar = userAvatar; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }

    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }
}
