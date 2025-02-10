package com.example.crim.service;

import com.example.crim.model.Channel;
import com.example.crim.model.User;
import com.example.crim.repository.ChannelRepository;
import com.example.crim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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
        return channelRepository.save(channel);
    }

    public Optional<Channel> getChannelById(Long channelId) {
        return channelRepository.findById(channelId);
    }

    public Channel updateChannelName(Long channelId, String newName, Long requesterId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));
        if (!channel.getOwner().getId().equals(requesterId) &&
                channel.getAdmins().stream().noneMatch(admin -> admin.getId().equals(requesterId))) {
            throw new RuntimeException("No permission to update channel name");
        }
        channel.setName(newName);
        return channelRepository.save(channel);
    }

    public Channel deleteChannel(Long channelId, Long requesterId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));
        if (!channel.getOwner().getId().equals(requesterId)) {
            throw new RuntimeException("Only owner can delete channel");
        }
        channel.setDeleted(true);
        return channelRepository.save(channel);
    }

    public Channel addMember(Long channelId, Long memberId, Long requesterId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!channel.getOwner().getId().equals(requesterId) &&
                channel.getAdmins().stream().noneMatch(admin -> admin.getId().equals(requesterId))) {
            throw new RuntimeException("No permission to add member");
        }
        channel.getMembers().add(member);
        return channelRepository.save(channel);
    }

    public Channel removeMember(Long channelId, Long memberId, Long requesterId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!channel.getOwner().getId().equals(requesterId)) {
            throw new RuntimeException("Only owner can remove member");
        }
        if (channel.getAdmins().contains(member)) {
            throw new RuntimeException("Cannot remove an admin member");
        }
        channel.getMembers().remove(member);
        return channelRepository.save(channel);
    }

    public Channel setAdmin(Long channelId, Long memberId, Long requesterId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));
        if (!channel.getOwner().getId().equals(requesterId)) {
            throw new RuntimeException("Only owner can assign admin role");
        }
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        channel.getAdmins().add(member);
        return channelRepository.save(channel);
    }

    public List<Channel> getAllChannels() {
        return channelRepository.findAll();
    }

    public List<Channel> getAllChannelsByMember(Long userId) {
        return channelRepository.findByMembers_Id(userId);
    }
}
