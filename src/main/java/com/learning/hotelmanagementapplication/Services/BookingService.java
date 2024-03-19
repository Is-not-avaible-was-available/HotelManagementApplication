package com.learning.hotelmanagementapplication.Services;

import com.learning.hotelmanagementapplication.DTOs.BookingResponseDTO;
import com.learning.hotelmanagementapplication.Exceptions.AlreadyPresentException;
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
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public BookingService(UserRepository userRepository,
                          HotelRepository hotelRepository, RoomRepository roomRepository,
                          BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    public BookingResponseDTO createBooking(LocalDate checkInDate, LocalDate checkOutDate,
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

        List<Booking> existingBookings = bookingRepository.findAllByRoom(room);

        for(Booking existingBooking : existingBookings){
            LocalDate existingCheckInDate = existingBooking.getCheckInDate();
            LocalDate existingCheckOutDate = existingBooking.getCheckOutDate();

            if(checkInDate.isBefore(existingCheckOutDate) && checkOutDate.isAfter(existingCheckInDate)){
                throw new NotFoundException("Room not available");
            }
        }

        Booking booking = new Booking();
            booking.setBookingStatus(BookingStatus.PENDING);
            booking.setRoom(room);
            booking.setHotel(hotel);
            booking.setCheckInDate(checkInDate);
            booking.setCheckOutDate(checkOutDate);
            booking.setUser(user);
            booking.setCreatedAt(LocalDateTime.now());
            booking.setLastModifiedAt(LocalDateTime.now());
            booking.setReferenceNumber("Ref No:" + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
            +user.getEmail()+user.getName());

        room.setRoomStatus(RoomStatus.RESERVED);
        roomRepository.save(room);
        RoomType roomType = room.getRoomType();
        int price = roomType.getPrice();
        long diff = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        int noOfDays = (int)diff;
         price = price*noOfDays;
        booking.setPrice(price);

        bookingRepository.save(booking);

        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
        bookingResponseDTO = Mappers.convertToBookingResponseDTO(booking);
        return bookingResponseDTO;
    }


    public BookingResponseDTO updateBooking(Long hotelId, Long roomId, LocalDate checkInDate,
                                            LocalDate checkOutDate, String referenceNumber) throws NotFoundException, AlreadyPresentException {

        Optional<Booking> optionalBooking = bookingRepository.findByReferenceNumber(referenceNumber);
        if(optionalBooking.isEmpty()){
            throw new NotFoundException("Booking with ref no: " +referenceNumber+" not found!");
        }

        Booking booking = optionalBooking.get();
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        if(optionalRoom.isEmpty()){
            throw new NotFoundException("not found!");
        }

        Room room = optionalRoom.get();
        List<Booking> bookings = bookingRepository.findAllByRoom(room);
        for(Booking existingBookings: bookings){
            LocalDate existingCheckInDate = existingBookings.getCheckInDate();
            LocalDate existingCheckOutDate = existingBookings.getCheckOutDate();
            if(checkInDate.isBefore(existingCheckOutDate) && checkOutDate.isAfter(existingCheckInDate)){
                throw new AlreadyPresentException("Room is not available, please select different room!");
            }
        }
        RoomType roomType = room.getRoomType();
        long diff = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        int noOfDays = (int)diff;

        Integer price = roomType.getPrice();
        price = price*noOfDays;

        Hotel hotel = hotelRepository.findById(hotelId).get();
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setRoom(room);
        booking.setHotel(hotel);
        booking.setLastModifiedAt(LocalDateTime.now());
        booking.setCreatedAt(LocalDateTime.now());
        booking.setCheckOutDate(checkOutDate);
        booking.setCheckInDate(checkInDate);
        booking.setPrice(price);
        booking.setReferenceNumber("Ref No:"+LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        bookingRepository.save(booking);
        return Mappers.convertToBookingResponseDTO(booking);
    }


    public List<BookingResponseDTO> getAllBookings(){
        List<Booking> bookings = bookingRepository.findAll();

       return bookings
                .stream()
                .map(Mappers::convertToBookingResponseDTO)
                .toList();
    }

    public String deleteBooking(String referenceNumber) throws NotFoundException {
        Optional<Booking> optionalBooking = bookingRepository
                .findByReferenceNumber(referenceNumber);
        if(getAllBookings().isEmpty()){
            throw new NotFoundException("Booking not found!");
        }
        Booking booking = optionalBooking.get();

        bookingRepository.delete(booking);

        return "Booking with reference number: "+referenceNumber+" is cancelled";
    }

}
