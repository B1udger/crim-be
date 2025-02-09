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

    /**
     * Creates a new channel.
     */
    @PostMapping
    public ResponseEntity<Channel> createChannel(@RequestParam String name, @RequestParam Long ownerId) {
        return ResponseEntity.status(201).body(channelService.createChannel(name, ownerId));
    }

    /**
     * Updates the channel name.
     */
    @PutMapping("/{channelId}")
    public ResponseEntity<Channel> updateChannelName(@PathVariable Long channelId,
                                                     @RequestParam String newName,
                                                     @RequestParam Long requesterId) {
        return ResponseEntity.ok(channelService.updateChannelName(channelId, newName, requesterId));
    }

    /**
     * Soft-deletes the channel.
     */
    @DeleteMapping("/{channelId}")
    public ResponseEntity<Channel> deleteChannel(@PathVariable Long channelId,
                                                 @RequestParam Long requesterId) {
        return ResponseEntity.ok(channelService.deleteChannel(channelId, requesterId));
    }

    /**
     * Adds a member to the channel.
     */
    @PostMapping("/{channelId}/members/{memberId}")
    public ResponseEntity<Channel> addMember(@PathVariable Long channelId,
                                             @PathVariable Long memberId,
                                             @RequestParam Long requesterId) {
        return ResponseEntity.ok(channelService.addMember(channelId, memberId, requesterId));
    }

    /**
     * Removes a guest member from the channel.
     */
    @DeleteMapping("/{channelId}/members/{memberId}")
    public ResponseEntity<Channel> removeMember(@PathVariable Long channelId,
                                                @PathVariable Long memberId,
                                                @RequestParam Long requesterId) {
        return ResponseEntity.ok(channelService.removeMember(channelId, memberId, requesterId));
    }

    /**
     * Assigns the ADMIN role to a channel member.
     */
    @PutMapping("/{channelId}/members/{memberId}/role")
    public ResponseEntity<Channel> setAdmin(@PathVariable Long channelId,
                                            @PathVariable Long memberId,
                                            @RequestParam Long requesterId) {
        return ResponseEntity.ok(channelService.setAdmin(channelId, memberId, requesterId));
    }

    /**
     * Retrieves all channels (optionally, channels where the user is a member).
     */
    @GetMapping
    public ResponseEntity<List<Channel>> getAllChannels() {
        return ResponseEntity.ok(channelService.getAllChannels());
    }

    @GetMapping("/member/{userId}")
    public ResponseEntity<List<Channel>> getChannelsByMember(@PathVariable Long userId) {
        return ResponseEntity.ok(channelService.getAllChannelsByMember(userId));
    }
}
