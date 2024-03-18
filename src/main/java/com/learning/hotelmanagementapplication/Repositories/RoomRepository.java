package com.learning.hotelmanagementapplication.Repositories;

import com.learning.hotelmanagementapplication.Models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
