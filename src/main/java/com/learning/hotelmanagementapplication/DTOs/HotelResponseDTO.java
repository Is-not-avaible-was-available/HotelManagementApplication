package com.learning.hotelmanagementapplication.DTOs;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HotelResponseDTO {
    private String name;
    private String address;
    private double rating;
}
