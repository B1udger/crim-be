package com.example.crim.repository;

import com.example.crim.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
    List<Channel> findByDeletedFalse();
    List<Channel> findByMembers_Id(Long userId);
}
