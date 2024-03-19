package com.learning.hotelmanagementapplication.Repositories;

import com.learning.hotelmanagementapplication.Models.Hotel;
import com.learning.hotelmanagementapplication.Models.Room;
import com.learning.hotelmanagementapplication.Models.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomNumberAndHotel(int number, Hotel hotel);
    Optional<Room> findRoomByHotel(Hotel hotel);
    List<Room> findAllByHotel_Name(String  name);

    Page<Room> findAllByHotel_Name(String hotelName, Pageable pageable);
    Page<Room> findAllByRoomType_RoomType(String roomType, Pageable pageable);
}
