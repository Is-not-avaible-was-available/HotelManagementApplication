package com.learning.hotelmanagementapplication.Repositories;

import com.learning.hotelmanagementapplication.Models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByName(String name);
}
