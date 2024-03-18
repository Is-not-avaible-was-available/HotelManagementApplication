package com.learning.hotelmanagementapplication.Repositories;

import com.learning.hotelmanagementapplication.Models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomNumber(int number);
    List<Room> findAllByHotel_Name(String  name);
}
