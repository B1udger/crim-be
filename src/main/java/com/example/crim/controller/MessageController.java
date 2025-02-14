package com.example.crim.controller;

import com.example.crim.model.Message;
import com.example.crim.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/private")
    public ResponseEntity<Message> sendPrivateMessage(@RequestParam Long senderId,
                                                      @RequestParam Long recipientId,
                                                      @RequestParam String content) {
        return ResponseEntity.status(201)
                .body(messageService.sendPrivateMessage(senderId, recipientId, content));
    }

    @PostMapping("/channel")
    public ResponseEntity<Message> sendChannelMessage(@RequestParam Long senderId,
                                                      @RequestParam Long channelId,
                                                      @RequestParam String content) {
        return ResponseEntity.status(201)
                .body(messageService.sendChannelMessage(senderId, channelId, content));
    }

    @GetMapping("/private")
    public ResponseEntity<List<Message>> getPrivateMessages(@RequestParam Long user1Id,
                                                            @RequestParam Long user2Id) {
        return ResponseEntity.ok(messageService.getPrivateMessages(user1Id, user2Id));
    }

    @GetMapping("/channel/{channelId}")
    public ResponseEntity<List<Message>> getChannelMessages(@PathVariable Long channelId) {
        return ResponseEntity.ok(messageService.getChannelMessages(channelId));
    }
}
