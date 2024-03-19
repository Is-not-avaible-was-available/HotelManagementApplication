package com.learning.hotelmanagementapplication.Services;

import com.learning.hotelmanagementapplication.Exceptions.NotFoundException;
import com.learning.hotelmanagementapplication.Models.*;
import com.learning.hotelmanagementapplication.Repositories.BookingRepository;
import com.learning.hotelmanagementapplication.Repositories.HotelRepository;
import com.learning.hotelmanagementapplication.Repositories.RoomRepository;
import com.learning.hotelmanagementapplication.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

@Service
public class BookingService {
    private UserRepository userRepository;
    private HotelRepository hotelRepository;
    private RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public BookingService(UserRepository userRepository,
                          HotelRepository hotelRepository, RoomRepository roomRepository,
                          BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    public String createBooking(LocalDate checkInDate, LocalDate checkOutDate,
                              Long hotelId, Long roomId, Long userId) throws NotFoundException {

        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelId);
        if(optionalHotel.isEmpty()){
            throw new NotFoundException("Not Found!");
        }

        Hotel hotel = optionalHotel.get();

        Optional<Room> roomOptional =  roomRepository.findById(roomId);
        if(roomOptional.isEmpty()){
            throw new NotFoundException("Not found!");
        }

        Room room = roomOptional.get();
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new NotFoundException("not found!");
        }
        User user = userOptional.get();


        Booking booking = null;
        if (room.getRoomStatus().equals(RoomStatus.AVAILABLE)) {

             booking = new Booking();
            booking.setBookingStatus(BookingStatus.PENDING);
            booking.setRoom(room);
            booking.setHotel(hotel);
            booking.setCheckInDate(checkInDate);
            booking.setCheckOutDate(checkOutDate);
            booking.setUser(user);
            booking.setCreatedAt(LocalDateTime.now());
            booking.setLastModifiedAt(LocalDateTime.now());
            booking.setReferenceNumber("Ref No: " + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
            bookingRepository.save(booking);
        }
        assert booking != null;
        return booking.getReferenceNumber();


    }
}
