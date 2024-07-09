package com.nhom7.qltd.service;

import com.nhom7.qltd.model.Message;
import com.nhom7.qltd.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    @Autowired
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }
    public Message broadcastMessage(Message message) {
        Message savedMessage = messageRepository.save(message);
        simpMessagingTemplate.convertAndSend("/topic/messages", savedMessage);
        return savedMessage;
    }
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
}
