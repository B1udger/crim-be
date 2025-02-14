package com.example.crim.controller;

import com.example.crim.model.Channel;
import com.example.crim.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {
    @Autowired
    private ChannelService channelService;

    @PostMapping
    public ResponseEntity<Channel> createChannel(@RequestParam String name, @RequestParam Long ownerId) {
        return ResponseEntity.status(201).body(channelService.createChannel(name, ownerId));
    }

    @PutMapping("/{channelId}")
    public ResponseEntity<Channel> updateChannelName(@PathVariable Long channelId,
                                                     @RequestParam String newName,
                                                     @RequestParam Long requesterId) {
        return ResponseEntity.ok(channelService.updateChannelName(channelId, newName, requesterId));
    }

    @DeleteMapping("/{channelId}")
    public ResponseEntity<Channel> deleteChannel(@PathVariable Long channelId,
                                                 @RequestParam Long requesterId) {
        return ResponseEntity.ok(channelService.deleteChannel(channelId, requesterId));
    }

    @PostMapping("/{channelId}/members/{memberId}")
    public ResponseEntity<Channel> addMember(@PathVariable Long channelId,
                                             @PathVariable Long memberId,
                                             @RequestParam Long requesterId) {
        return ResponseEntity.ok(channelService.addMember(channelId, memberId, requesterId));
    }

    @DeleteMapping("/{channelId}/members/{memberId}")
    public ResponseEntity<Channel> removeMember(@PathVariable Long channelId,
                                                @PathVariable Long memberId,
                                                @RequestParam Long requesterId) {
        return ResponseEntity.ok(channelService.removeMember(channelId, memberId, requesterId));
    }

    @PutMapping("/{channelId}/members/{memberId}/role")
    public ResponseEntity<Channel> setAdmin(@PathVariable Long channelId,
                                            @PathVariable Long memberId,
                                            @RequestParam Long requesterId) {
        return ResponseEntity.ok(channelService.setAdmin(channelId, memberId, requesterId));
    }


    @PutMapping("/{channelId}/rename")
    public ResponseEntity<Channel> renameChannel(@PathVariable Long channelId,
                                                 @RequestParam String newName,
                                                 @RequestParam Long requesterId) {
        return ResponseEntity.ok(channelService.updateChannelName(channelId, newName, requesterId));
    }
}
