package com.learning.hotelmanagementapplication.Controllers;

import com.learning.hotelmanagementapplication.DTOs.RoomRequestDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
public class RoomController {

    @PostMapping
    public void addNewRoom(@RequestBody RoomRequestDTO requestDTO){

    }

    @PostMapping
    public void updateRoomDetails(@RequestBody RoomRequestDTO  requestDTO){

    }

    @GetMapping
    public void getAllRooms(){

    }
    @GetMapping("/{id}")

    public void getRoomById(@PathVariable("id") Long id){

    }

    @GetMapping("/{hotel}")
    public void getAllRoomsByHotelName(@PathVariable("hotel") String HotelName){

    }
}
