package com.chatapp.model;

public class ConversationDTO {
    private String userId;
    private String userName;
    private String userAvatar;
    private String lastMessage;
    private long lastMessageTime;
    private int unreadCount;

    public ConversationDTO() {}

    public ConversationDTO(String userId, String userName, String userAvatar, String lastMessage, long lastMessageTime, int unreadCount) {
        this.userId = userId;
        this.userName = userName;
        this.userAvatar = userAvatar;
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
        this.unreadCount = unreadCount;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserAvatar() { return userAvatar; }
    public void setUserAvatar(String userAvatar) { this.userAvatar = userAvatar; }

    public String getLastMessage() { return lastMessage; }
    public void setLastMessage(String lastMessage) { this.lastMessage = lastMessage; }

    public long getLastMessageTime() { return lastMessageTime; }
    public void setLastMessageTime(long lastMessageTime) { this.lastMessageTime = lastMessageTime; }

    public int getUnreadCount() { return unreadCount; }
    public void setUnreadCount(int unreadCount) { this.unreadCount = unreadCount; }
}
