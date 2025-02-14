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

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChannelRepository channelRepository;

    public User registerUser(User user) {
        user.setRole("USER");
        return userRepository.save(user);
    }

    public Optional<User> loginUser(String username, String password) {
        return userRepository.findByUsernameContaining(username)
                .stream()
                .filter(u -> u.getPassword().equals(password))
                .findFirst();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setEmail(updatedUser.getEmail());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> searchUsers(String keyword) {
        return userRepository.findByUsernameContaining(keyword);
    }

    public User addFriend(Long userId, Long friendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.getFriends().add(friend);
        friend.getFriends().add(user);
        userRepository.save(friend);
        return userRepository.save(user);
    }

    public Map<String, Object> getDashboard(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Channel> channels = channelRepository.findByMembers_Id(userId);
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("friends", user.getFriends());
        dashboard.put("channels", channels);
        return dashboard;
    }
}
