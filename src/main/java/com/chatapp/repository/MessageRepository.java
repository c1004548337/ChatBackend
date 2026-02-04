package com.chatapp.repository;

import com.chatapp.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, String> {
    
    // Find chat history between two users
    List<Message> findBySenderIdAndReceiverIdOrSenderIdAndReceiverIdOrderByTimestampAsc(
        String senderId1, String receiverId1, 
        String senderId2, String receiverId2
    );
    
    // Find all messages involving a user (for conversation list)
    List<Message> findBySenderIdOrReceiverId(String senderId, String receiverId);
    
    // Find unread messages sent by sender to receiver
    List<Message> findBySenderIdAndReceiverIdAndReadFalse(String senderId, String receiverId);
}
