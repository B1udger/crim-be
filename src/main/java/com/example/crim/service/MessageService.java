package com.example.crim.service;

import com.example.crim.model.Channel;
import com.example.crim.model.Message;
import com.example.crim.model.User;
import com.example.crim.repository.ChannelRepository;
import com.example.crim.repository.MessageRepository;
import com.example.crim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChannelRepository channelRepository;

    // Send a private message
    public Message sendPrivateMessage(Long senderId, Long recipientId, String content) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User recipient = userRepository.findById(recipientId)
                .orElseThrow(() -> new RuntimeException("Recipient not found"));

        Message message = new Message();
        message.setContent(content);
        message.setSender(sender);
        message.setRecipientUser(recipient);

        return messageRepository.save(message);
    }

    // Send a message to a channel
    public Message sendChannelMessage(Long senderId, Long channelId, String content) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));

        if (!channel.getMembers().contains(sender)) {
            throw new RuntimeException("User is not a member of the channel");
        }

        Message message = new Message();
        message.setContent(content);
        message.setSender(sender);
        message.setRecipientChannel(channel);

        return messageRepository.save(message);
    }

    // Retrieve messages for a private chat
    public List<Message> getPrivateMessages(Long user1Id, Long user2Id) {
        User user1 = userRepository.findById(user1Id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User user2 = userRepository.findById(user2Id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return messageRepository.findBySenderAndRecipientUserOrSenderAndRecipientUser(
                user1, user2, user2, user1);
    }

    // Retrieve messages from a channel
    public List<Message> getChannelMessages(Long channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));

        return messageRepository.findByRecipientChannel(channel);
    }
}
