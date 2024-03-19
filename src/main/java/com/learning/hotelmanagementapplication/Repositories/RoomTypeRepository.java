package com.learning.hotelmanagementapplication.Repositories;

import com.learning.hotelmanagementapplication.Models.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {

    Optional<RoomType> findByRoomType(String roomType);
}
