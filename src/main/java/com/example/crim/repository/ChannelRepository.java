package com.example.crim.repository;

import com.example.crim.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository for Channel entity.
 */
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    // Return channels that are not soft-deleted.
    List<Channel> findByDeletedFalse();

    // Retrieve channels where a given user is a member.
    List<Channel> findByMembers_Id(Long userId);
}
