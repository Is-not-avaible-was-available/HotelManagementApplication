package com.learning.hotelmanagementapplication.Controllers;

import com.learning.hotelmanagementapplication.DTOs.CreateBookingRequestDTO;
import com.learning.hotelmanagementapplication.Exceptions.NotFoundException;
import com.learning.hotelmanagementapplication.Services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<String> addNewBooking(@RequestBody CreateBookingRequestDTO requestDTO){
        String response = null;
        try {
            response =
            bookingService.createBooking(requestDTO.getCheckInDate(), requestDTO.getCheckOutDate(),
                    requestDTO.getHotelId(), requestDTO.getRoomId(), requestDTO.getUserId());
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>("Booking created with ref no" + response, HttpStatus.CREATED);
    }

    public void getAllBookings(){

    }

    public void updateBooking(){

    }

    public void cancelBooking(){

    }
}
