package com.learning.hotelmanagementapplication.Repositories;

import com.learning.hotelmanagementapplication.Models.Booking;
import com.learning.hotelmanagementapplication.Models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByRoom(Room room);
    Optional<Booking> findByReferenceNumber(String refNumber);
}
