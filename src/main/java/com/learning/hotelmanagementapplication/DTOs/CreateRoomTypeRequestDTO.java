package com.learning.hotelmanagementapplication.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRoomTypeRequestDTO {
    private String roomType;
    private Integer price;
}
