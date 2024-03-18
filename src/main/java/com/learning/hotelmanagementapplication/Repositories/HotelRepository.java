package com.learning.hotelmanagementapplication.Repositories;

import com.learning.hotelmanagementapplication.Models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
