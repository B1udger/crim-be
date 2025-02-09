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

/**
 * Service layer for message-related operations.
 */
@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChannelRepository channelRepository;

    /**
     * Sends a private message from sender to recipient.
     */
    public Message sendPrivateMessage(Long senderId, Long recipientId, String content) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found with id " + senderId));
        User recipient = userRepository.findById(recipientId)
                .orElseThrow(() -> new RuntimeException("Recipient not found with id " + recipientId));
        Message message = new Message();
        message.setContent(content);
        message.setSender(sender);
        message.setRecipientUser(recipient);
        return messageRepository.save(message);
    }

    /**
     * Sends a message to a channel.
     */
    public Message sendChannelMessage(Long senderId, Long channelId, String content) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found with id " + senderId));
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found with id " + channelId));
        Message message = new Message();
        message.setContent(content);
        message.setSender(sender);
        message.setRecipientChannel(channel);
        return messageRepository.save(message);
    }

    /**
     * Retrieves all messages from a specific channel.
     */
    public List<Message> getChannelMessages(Long channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found with id " + channelId));
        return messageRepository.findByRecipientChannel(channel);
    }

    /**
     * Retrieves private messages exchanged between two users (both directions).
     */
    public List<Message> getPrivateMessages(Long user1Id, Long user2Id) {
        User user1 = userRepository.findById(user1Id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + user1Id));
        User user2 = userRepository.findById(user2Id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + user2Id));
        return messageRepository.findBySenderAndRecipientUserOrSenderAndRecipientUser(
                user1, user2, user2, user1);
    }

    // Допълнителни методи за редактиране или "меко" изтриване на съобщения, ако условието го изисква.
    public Message editMessage(Long messageId, String newContent, Long userId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found with id " + messageId));
        // Примерна логика: само авторът може да редактира съобщението
        if (!message.getSender().getId().equals(userId)) {
            throw new RuntimeException("Only the author can edit the message");
        }
        message.setContent(newContent);
        return messageRepository.save(message);
    }

    public void deleteMessage(Long messageId, Long userId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found with id " + messageId));
        // Примерна логика: само авторът може да изтрие съобщението (меко изтриване може да означава флаг)
        if (!message.getSender().getId().equals(userId)) {
            throw new RuntimeException("Only the author can delete the message");
        }
        // За меко изтриване можем да добавим флаг (например, message.deleted = true) – тук ползваме физическо изтриване.
        messageRepository.delete(message);
    }
}
