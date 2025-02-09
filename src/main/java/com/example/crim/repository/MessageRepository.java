package com.example.crim.repository;

import com.example.crim.model.Message;
import com.example.crim.model.Channel;
import com.example.crim.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository for Message entity.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {
    // Retrieve private messages exchanged between two users (both directions).
    List<Message> findBySenderAndRecipientUserOrSenderAndRecipientUser(
            User sender, User recipient, User sender2, User recipient2);

    // Retrieve messages for a given channel.
    List<Message> findByRecipientChannel(Channel channel);
}
