package com.learning.hotelmanagementapplication.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingResponseDTO {

    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String userName;
    private String mobile;
    private String referenceId;
    private Integer price;
}
