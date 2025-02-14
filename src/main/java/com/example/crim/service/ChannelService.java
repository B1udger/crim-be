package com.example.crim.service;

import com.example.crim.model.Channel;
import com.example.crim.model.User;
import com.example.crim.repository.ChannelRepository;
import com.example.crim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChannelService {
    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private UserRepository userRepository;

    public Channel createChannel(String name, Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        Channel channel = new Channel(name, owner);
        channel.getMembers().add(owner); // Owner is also a member
        channel.getAdmins().add(owner); // Owner is automatically admin
        return channelRepository.save(channel);
    }

    // Rename channel
    public Channel updateChannelName(Long channelId, String newName, Long requesterId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));

        boolean isOwner = channel.getOwner().getId().equals(requesterId);
        boolean isAdmin = channel.getAdmins().stream().anyMatch(admin -> admin.getId().equals(requesterId));

        if (!isOwner && !isAdmin) {
            throw new RuntimeException("No permission to rename channel");
        }

        channel.setName(newName);
        return channelRepository.save(channel);
    }

    // Delete channel
    public Channel deleteChannel(Long channelId, Long requesterId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));

        if (!channel.getOwner().getId().equals(requesterId)) {
            throw new RuntimeException("Only the owner can delete this channel");
        }

        channel.setDeleted(true);
        return channelRepository.save(channel);
    }

    // Add member
    public Channel addMember(Long channelId, Long memberId, Long requesterId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean isOwner = channel.getOwner().getId().equals(requesterId);
        boolean isAdmin = channel.getAdmins().stream().anyMatch(admin -> admin.getId().equals(requesterId));

        if (!isOwner && !isAdmin) {
            throw new RuntimeException("No permission to add members");
        }

        channel.getMembers().add(member);
        return channelRepository.save(channel);
    }

    // Remove member
    public Channel removeMember(Long channelId, Long memberId, Long requesterId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!channel.getOwner().getId().equals(requesterId)) {
            throw new RuntimeException("Only the owner can remove members");
        }

        if (channel.getAdmins().contains(member)) {
            throw new RuntimeException("Cannot remove an admin member");
        }

        channel.getMembers().remove(member);
        return channelRepository.save(channel);
    }

    // Set admin role
    public Channel setAdmin(Long channelId, Long memberId, Long requesterId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));

        if (!channel.getOwner().getId().equals(requesterId)) {
            throw new RuntimeException("Only the owner can assign admin role");
        }

        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!channel.getMembers().contains(member)) {
            throw new RuntimeException("User must be a member first");
        }

        channel.getAdmins().add(member);
        return channelRepository.save(channel);
    }
}
