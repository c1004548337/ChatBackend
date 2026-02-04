package com.chatapp.controller;

import com.chatapp.model.User;
import com.chatapp.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // 允许跨域
public class AuthController {

    @Autowired
    private DataService dataService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        String phone = payload.get("phone");
        String password = payload.get("password");

        Optional<User> userOpt = dataService.findUserByPhone(phone);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            // TODO: 生成 JWT Token 并返回
            return ResponseEntity.ok(userOpt.get());
        } else {
            return ResponseEntity.status(401).body("用户名或密码错误");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (dataService.findUserByPhone(user.getPhone()).isPresent()) {
            return ResponseEntity.badRequest().body("手机号已被注册");
        }
        user.setId(UUID.randomUUID().toString());
        // 默认头像
        if (user.getAvatar() == null) {
            user.setAvatar("https://ui-avatars.com/api/?name=" + user.getName().replaceAll(" ", "+"));
        }
        dataService.saveUser(user);
        return ResponseEntity.ok(user);
    }
}
