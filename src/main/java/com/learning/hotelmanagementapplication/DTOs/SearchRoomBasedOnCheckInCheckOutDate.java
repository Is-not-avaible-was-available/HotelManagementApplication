package com.learning.hotelmanagementapplication.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class SearchRoomBasedOnCheckInCheckOutDate {

    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String query;
    private int pageSize;
    private int pageNumber;
}
