package com.nhom7.qltd.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "chat_messages")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @ManyToOne
    @JoinColumn(name = "from_user",referencedColumnName = "user_name")
    private User user;
    private String text;
    private LocalDateTime timestamp = LocalDateTime.now();
}