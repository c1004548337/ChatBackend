package com.chatapp.controller;

import com.chatapp.model.ConversationDTO;
import com.chatapp.model.Message;
import com.chatapp.model.User;
import com.chatapp.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired
    private DataService dataService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(dataService.getAllUsers());
    }

    @GetMapping("/conversations")
    public ResponseEntity<List<ConversationDTO>> getConversations(@RequestParam String userId) {
        return ResponseEntity.ok(dataService.getConversations(userId));
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages(@RequestParam String userId1, @RequestParam String userId2) {
        return ResponseEntity.ok(dataService.getMessages(userId1, userId2));
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        message.setId(UUID.randomUUID().toString());
        message.setTimestamp(System.currentTimeMillis());
        dataService.saveMessage(message);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/messages/read")
    public ResponseEntity<Void> markMessagesRead(@RequestParam String userId, @RequestParam String otherUserId) {
        dataService.markMessagesAsRead(userId, otherUserId);
        return ResponseEntity.ok().build();
    }
}
