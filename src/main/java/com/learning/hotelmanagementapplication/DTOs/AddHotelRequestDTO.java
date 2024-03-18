package com.learning.hotelmanagementapplication.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddHotelRequestDTO {
    private String name;
    private String address;
    private List<Long> roomIds;
}
