package com.learning.hotelmanagementapplication.DTOs;

import com.learning.hotelmanagementapplication.Models.RoomStatus;
import com.learning.hotelmanagementapplication.Models.RoomType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomRequestDTO {
    private int roomNumber;
    private String roomType;
    private int capacity;
    private RoomStatus roomStatus;
    private Long hotelId;
}
