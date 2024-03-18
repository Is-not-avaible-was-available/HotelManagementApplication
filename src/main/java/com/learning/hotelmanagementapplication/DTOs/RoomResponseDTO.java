package com.learning.hotelmanagementapplication.DTOs;

import com.learning.hotelmanagementapplication.Models.Room;
import com.learning.hotelmanagementapplication.Models.RoomStatus;
import com.learning.hotelmanagementapplication.Models.RoomType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomResponseDTO {

    private int roomNumber;
    private int capacity;
    private RoomType roomType;
    private RoomStatus roomStatus;
}
