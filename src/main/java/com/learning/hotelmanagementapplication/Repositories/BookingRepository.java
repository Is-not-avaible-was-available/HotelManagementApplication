package com.learning.hotelmanagementapplication.Repositories;

import com.learning.hotelmanagementapplication.Models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
