package com.learning.hotelmanagementapplication.Controllers;

import com.learning.hotelmanagementapplication.DTOs.BookingResponseDTO;
import com.learning.hotelmanagementapplication.DTOs.CancelBookingRequestDTO;
import com.learning.hotelmanagementapplication.DTOs.CreateBookingRequestDTO;
import com.learning.hotelmanagementapplication.DTOs.UpdateBookingRequestDTO;
import com.learning.hotelmanagementapplication.Exceptions.AlreadyPresentException;
import com.learning.hotelmanagementapplication.Exceptions.NotFoundException;
import com.learning.hotelmanagementapplication.Services.BookingService;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponseDTO> addNewBooking(@RequestBody CreateBookingRequestDTO requestDTO){
        BookingResponseDTO response = null;
        try {
            response =
            bookingService.createBooking(requestDTO.getCheckInDate(), requestDTO.getCheckOutDate(),
                    requestDTO.getHotelId(), requestDTO.getRoomId(), requestDTO.getUserId());
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> getAllBookings(){
        List<BookingResponseDTO> bookings= bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<BookingResponseDTO> updateBooking(@RequestBody UpdateBookingRequestDTO requestDTO){
        BookingResponseDTO responseDTO = null;
        try {
            responseDTO = bookingService.updateBooking(
                    requestDTO.getNewHotelId(), requestDTO.getNewRoomId(), requestDTO.getNewCheckInDate(),
                    requestDTO.getNewCheckOutDate(),requestDTO.getReferenceNumber()
            );
        } catch (NotFoundException | AlreadyPresentException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<String> cancelBooking(@RequestBody CancelBookingRequestDTO requestDTO){
        String response = null;
        try {
            response = bookingService.deleteBooking(requestDTO.getReferenceNumber());
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
