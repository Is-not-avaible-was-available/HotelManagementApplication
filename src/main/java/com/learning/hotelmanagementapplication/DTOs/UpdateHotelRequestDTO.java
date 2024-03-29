package com.learning.hotelmanagementapplication.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateHotelRequestDTO {
    private String name;
    private String address;
    private double rating;
    List<Long> roomIds;
    private String city;
}
