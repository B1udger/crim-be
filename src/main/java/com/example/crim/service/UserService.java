package com.example.crim.service;

import com.example.crim.model.User;
import com.example.crim.model.Channel;
import com.example.crim.repository.UserRepository;
import com.example.crim.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service layer for user-related operations.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChannelRepository channelRepository;

    /**
     * Creates a new user.
     */
    public User createUser(User user) {
        // Optionally add password encryption here.
        return userRepository.save(user);
    }

    /**
     * Retrieves a user by its ID.
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Updates a user's data.
     */
    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setEmail(updatedUser.getEmail());
        return userRepository.save(user);
    }

    /**
     * Deletes a user by its ID.
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Searches for users whose username contains the given keyword.
     */
    public List<User> searchUsers(String keyword) {
        return userRepository.findByUsernameContaining(keyword);
    }

    /**
     * Adds a friend to the user's friend list (bidirectional).
     */
    public User addFriend(Long userId, Long friendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + friendId));
        user.getFriends().add(friend);
        // For bidirectional friendship:
        friend.getFriends().add(user);
        userRepository.save(friend);
        return userRepository.save(user);
    }

    /**
     * Retrieves a dashboard for a user, including the channels where the user is a member and the friend list.
     */
    public Map<String, Object> getDashboard(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
        List<Channel> channels = channelRepository.findByMembers_Id(userId);
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("friends", user.getFriends());
        dashboard.put("channels", channels);
        return dashboard;
    }

    // Допълнителни методи като изпращане на съобщения до приятели, актуализиране на профил, смяна на парола и т.н.
    // могат да се добавят според нуждите на условието.
}
