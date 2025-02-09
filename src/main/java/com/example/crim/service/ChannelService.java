package com.example.crim.service;

import com.example.crim.model.Channel;
import com.example.crim.model.User;
import com.example.crim.repository.ChannelRepository;
import com.example.crim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for channel-related operations.
 */
@Service
public class ChannelService {

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new channel with the given name and owner.
     * The creator becomes the owner and is automatically added as a member.
     */
    public Channel createChannel(String name, Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found with id " + ownerId));
        Channel channel = new Channel(name, owner);
        return channelRepository.save(channel);
    }

    /**
     * Retrieves a channel by its ID.
     */
    public Optional<Channel> getChannelById(Long channelId) {
        return channelRepository.findById(channelId);
    }

    /**
     * Updates the channel name.
     * Allowed only if the requester is the OWNER or an ADMIN.
     */
    public Channel updateChannelName(Long channelId, String newName, Long requesterId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found with id " + channelId));
        if (!channel.getOwner().getId().equals(requesterId) &&
                channel.getAdmins().stream().noneMatch(admin -> admin.getId().equals(requesterId))) {
            throw new RuntimeException("No permission to update the channel name");
        }
        channel.setName(newName);
        return channelRepository.save(channel);
    }

    /**
     * Soft-deletes the channel.
     * Allowed only for the OWNER.
     */
    public Channel deleteChannel(Long channelId, Long requesterId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found with id " + channelId));
        if (!channel.getOwner().getId().equals(requesterId)) {
            throw new RuntimeException("Only the owner can delete the channel");
        }
        channel.setDeleted(true);
        return channelRepository.save(channel);
    }

    /**
     * Adds a member to the channel.
     * Allowed only if the requester is the OWNER or an ADMIN.
     */
    public Channel addMember(Long channelId, Long memberId, Long requesterId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found with id " + channelId));
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + memberId));
        if (!channel.getOwner().getId().equals(requesterId) &&
                channel.getAdmins().stream().noneMatch(admin -> admin.getId().equals(requesterId))) {
            throw new RuntimeException("No permission to add member to the channel");
        }
        channel.getMembers().add(member);
        return channelRepository.save(channel);
    }

    /**
     * Removes a guest member from the channel.
     * Allowed only for the OWNER.
     */
    public Channel removeMember(Long channelId, Long memberId, Long requesterId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found with id " + channelId));
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + memberId));
        if (!channel.getOwner().getId().equals(requesterId)) {
            throw new RuntimeException("Only the owner can remove a guest member from the channel");
        }
        if (channel.getAdmins().contains(member)) {
            throw new RuntimeException("Cannot remove an admin member");
        }
        channel.getMembers().remove(member);
        return channelRepository.save(channel);
    }

    /**
     * Sets a member as ADMIN.
     * Allowed only for the OWNER.
     */
    public Channel setAdmin(Long channelId, Long memberId, Long requesterId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found with id " + channelId));
        if (!channel.getOwner().getId().equals(requesterId)) {
            throw new RuntimeException("Only the owner can assign admin role");
        }
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + memberId));
        channel.getAdmins().add(member);
        return channelRepository.save(channel);
    }

    // Допълнителни методи за извличане на списъци от канали (напр. getAllChannels, getChannelsByUser)
    public List<Channel> getAllChannels() {
        return channelRepository.findAll();
    }

    public List<Channel> getAllChannelsByMember(Long userId) {
        return channelRepository.findByMembers_Id(userId);
    }

    // Допълнителни методи за добавяне/премахване на потребители чрез DTO, ако е необходимо.
}
