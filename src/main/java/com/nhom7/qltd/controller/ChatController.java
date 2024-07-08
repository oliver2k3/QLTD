package com.nhom7.qltd.controller;

import com.nhom7.qltd.model.ChatMessage;
import com.nhom7.qltd.model.User;
import com.nhom7.qltd.repository.ChatMessageRepository;
import com.nhom7.qltd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private UserService userService;
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("No authentication found. User must be logged in to send messages.");
        }
        String username = authentication.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + username));


        message.setUser(user); // Set the 'from' field with the username
        chatMessageRepository.save(message);
        return message;
    }
}