package com.example.crim.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/**
 * Represents a message sent by a user.
 */
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Message content
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // Sender of the message
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    // Timestamp when the message was sent
    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    // For private messages: the recipient user (nullable if not a private message)
    @ManyToOne
    @JoinColumn(name = "recipient_user_id")
    private User recipientUser;

    // For channel messages: the recipient channel (nullable if not a channel message)
    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel recipientChannel;

    public Message() {
    }

    public Message(String content, User sender) {
        this.content = content;
        this.sender = sender;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public User getRecipientUser() {
        return recipientUser;
    }

    public void setRecipientUser(User recipientUser) {
        this.recipientUser = recipientUser;
    }

    public Channel getRecipientChannel() {
        return recipientChannel;
    }

    public void setRecipientChannel(Channel recipientChannel) {
        this.recipientChannel = recipientChannel;
    }
}
