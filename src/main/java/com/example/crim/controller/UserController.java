package com.example.crim.controller;

import com.example.crim.model.User;
import com.example.crim.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Creates a new user.
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.status(201).body(userService.createUser(user));
    }

    /**
     * Searches for users by a keyword.
     */
    @GetMapping
    public ResponseEntity<List<User>> searchUsers(@RequestParam("search") String keyword) {
        return ResponseEntity.ok(userService.searchUsers(keyword));
    }

    /**
     * Adds a friend to the user's friend list.
     */
    @PostMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<User> addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        return ResponseEntity.ok(userService.addFriend(userId, friendId));
    }

    /**
     * Retrieves the dashboard for the user (channels and friends).
     */
    @GetMapping("/{userId}/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboard(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getDashboard(userId));
    }

    // Допълнителни ендпоинти като: изпращане на съобщения до приятел, обновяване на профил, смяна на парола и т.н.
}
