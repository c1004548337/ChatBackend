package com.chatapp.service;

import com.chatapp.model.ConversationDTO;
import com.chatapp.model.Message;
import com.chatapp.model.Moment;
import com.chatapp.model.User;
import com.chatapp.repository.MessageRepository;
import com.chatapp.repository.MomentRepository;
import com.chatapp.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据服务类
 * 已重构为使用 MySQL 数据库 (via Spring Data JPA)
 */
@Service
public class DataService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MomentRepository momentRepository;

    @PostConstruct
    public void initMockData() {
        if (userRepository.count() == 0) {
            userRepository.save(new User("u1", "陈大明", "13800138000", "123456", "https://ui-avatars.com/api/?name=Da+Ming&background=0D8ABC&color=fff", "React Native 开发者"));
            userRepository.save(new User("u2", "李小红", "13900139000", "123456", "https://ui-avatars.com/api/?name=Xiao+Hong&background=FF5722&color=fff", "喜欢旅游和摄影"));
        }
    }

    // --- User Operations ---

    public Optional<User> findUserByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    public Optional<User> findUserById(String id) {
        return userRepository.findById(id);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // --- Message Operations ---

    public List<Message> getMessages(String userId1, String userId2) {
        return messageRepository.findBySenderIdAndReceiverIdOrSenderIdAndReceiverIdOrderByTimestampAsc(
                userId1, userId2, userId2, userId1
        );
    }

    public void markMessagesAsRead(String receiverId, String senderId) {
        List<Message> unreadMessages = messageRepository.findBySenderIdAndReceiverIdAndReadFalse(senderId, receiverId);
        if (!unreadMessages.isEmpty()) {
            for (Message m : unreadMessages) {
                m.setRead(true);
            }
            messageRepository.saveAll(unreadMessages);
        }
    }

    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    public List<ConversationDTO> getConversations(String currentUserId) {
        Map<String, ConversationDTO> conversationMap = new HashMap<>();

        // 获取所有与当前用户相关的消息
        List<Message> userMessages = messageRepository.findBySenderIdOrReceiverId(currentUserId, currentUserId);

        for (Message m : userMessages) {
            String targetUserId = null;
            if (m.getSenderId().equals(currentUserId)) {
                targetUserId = m.getReceiverId();
            } else if (m.getReceiverId().equals(currentUserId)) {
                targetUserId = m.getSenderId();
            }

            if (targetUserId != null) {
                final String finalOtherUserId = targetUserId;
                // 如果是新会话或者消息更新
                if (!conversationMap.containsKey(targetUserId) || m.getTimestamp() > conversationMap.get(targetUserId).getLastMessageTime()) {
                    Optional<User> otherUserOpt = findUserById(targetUserId);
                    if (otherUserOpt.isPresent()) {
                        User otherUser = otherUserOpt.get();
                        ConversationDTO dto = conversationMap.getOrDefault(targetUserId, new ConversationDTO());
                        dto.setUserId(otherUser.getId());
                        dto.setUserName(otherUser.getName());
                        dto.setUserAvatar(otherUser.getAvatar());
                        dto.setLastMessage(m.getText());
                        dto.setLastMessageTime(m.getTimestamp());
                        
                        // 计算未读数
                        // 在这个内存列表中计算可能比频繁查库更高效，或者可以使用 repository count 方法
                        // 这里直接使用 stream 过滤 userMessages
                        long unread = userMessages.stream()
                            .filter(msg -> msg.getSenderId().equals(finalOtherUserId) && msg.getReceiverId().equals(currentUserId) && !msg.isRead())
                            .count();
                        dto.setUnreadCount((int) unread); 
                        conversationMap.put(targetUserId, dto);
                    }
                }
            }
        }
        
        // 补充所有好友（兼容逻辑：显示所有用户，即使没有聊天记录）
        List<User> allUsers = userRepository.findAll();
        for (User u : allUsers) {
            if (!u.getId().equals(currentUserId)) {
                if (!conversationMap.containsKey(u.getId())) {
                    conversationMap.put(u.getId(), new ConversationDTO(
                        u.getId(), u.getName(), u.getAvatar(), "点击开始聊天", 0, 0
                    ));
                }
            }
        }

        return new ArrayList<>(conversationMap.values()).stream()
                .sorted((c1, c2) -> Long.compare(c2.getLastMessageTime(), c1.getLastMessageTime()))
                .collect(Collectors.toList());
    }

    // --- Moment Operations ---

    public List<Moment> getAllMoments() {
        return momentRepository.findAllByOrderByTimestampDesc();
    }

    public void saveMoment(Moment moment) {
        momentRepository.save(moment);
    }

    public boolean deleteMoment(String id) {
        if (momentRepository.existsById(id)) {
            momentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
