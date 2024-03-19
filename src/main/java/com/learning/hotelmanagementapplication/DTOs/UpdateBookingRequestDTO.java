package com.learning.hotelmanagementapplication.DTOs;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateBookingRequestDTO {

    private Long newHotelId;
    private Long newRoomId;
    private LocalDate newCheckInDate;
    private LocalDate newCheckOutDate;
    private String referenceNumber;
}
