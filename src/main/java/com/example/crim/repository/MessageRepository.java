package com.example.crim.repository;

import com.example.crim.model.Message;
import com.example.crim.model.Channel;
import com.example.crim.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderAndRecipientUserOrSenderAndRecipientUser(
            User sender, User recipient, User sender2, User recipient2);
    List<Message> findByRecipientChannel(Channel channel);
}
