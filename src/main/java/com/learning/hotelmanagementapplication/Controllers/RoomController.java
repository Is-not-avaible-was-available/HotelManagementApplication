package com.learning.hotelmanagementapplication.Controllers;

import com.learning.hotelmanagementapplication.DTOs.CreateRoomTypeRequestDTO;
import com.learning.hotelmanagementapplication.DTOs.RoomRequestDTO;
import com.learning.hotelmanagementapplication.DTOs.RoomResponseDTO;
import com.learning.hotelmanagementapplication.Exceptions.AlreadyPresentException;
import com.learning.hotelmanagementapplication.Exceptions.NotFoundException;
import com.learning.hotelmanagementapplication.Services.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService  roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<String> addNewRoom(@RequestBody RoomRequestDTO requestDTO){
        try {
            roomService.addNewRoom(requestDTO.getRoomNumber(),requestDTO.getRoomType(),
                    requestDTO.getHotelId());
        } catch (AlreadyPresentException | NotFoundException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>("room created successfully!", HttpStatus.CREATED);
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<String> updateRoomDetails(@RequestBody RoomRequestDTO  requestDTO,
                                  @PathVariable("roomId") Long id){

        try {
            roomService.updateRoomDetails(requestDTO.getRoomNumber(),
                    requestDTO.getRoomType(), requestDTO.getRoomStatus(), id);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(id+" room updated successfully!", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RoomResponseDTO>> getAllRooms(){
        List<RoomResponseDTO> responseDTOS = roomService.getAllRooms();
        return new ResponseEntity<>(responseDTOS, HttpStatus.OK);
    }
    @GetMapping("/{id}")

    public ResponseEntity<RoomResponseDTO> getRoomById(@PathVariable("id") Long id){
        RoomResponseDTO roomResponseDTO = null;
        try {
            roomResponseDTO = roomService.getRoomDetails(id);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(roomResponseDTO,  HttpStatus.OK);
    }

    @GetMapping("/{hotel}")
    public ResponseEntity<List<RoomResponseDTO>> getAllRoomsByHotelName(@PathVariable("hotel") String HotelName){
        List<RoomResponseDTO> roomResponseDTOS = roomService.getRoomsByHotelName(HotelName);

        return new ResponseEntity<>(roomResponseDTOS, HttpStatus.OK);
    }

    @PostMapping("/roomType")
    public ResponseEntity<String> createRoomType(@RequestBody CreateRoomTypeRequestDTO requestDTO){
        try {
            roomService.createRoomType(requestDTO.getRoomType(), requestDTO.getPrice(), requestDTO.getCapacity());
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>("RoomType created!", HttpStatus.CREATED);
    }
}
