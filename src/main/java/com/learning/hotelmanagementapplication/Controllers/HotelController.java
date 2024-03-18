package com.learning.hotelmanagementapplication.Controllers;

import com.learning.hotelmanagementapplication.DTOs.AddHotelRequestDTO;
import com.learning.hotelmanagementapplication.DTOs.HotelResponseDTO;
import com.learning.hotelmanagementapplication.DTOs.UpdateHotelRequestDTO;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @PostMapping
    public ResponseEntity<String> addAHotel(@RequestBody AddHotelRequestDTO requestDTO){
        return  ResponseEntity.ok("Added a new Hotel named:  " + requestDTO.getName());
    }

    @GetMapping
    public ResponseEntity<List<HotelResponseDTO>> getAllHotelDetails(){
        return null;
    }

    @PutMapping
    public ResponseEntity<HotelResponseDTO> updateHotelDetails(
            @RequestBody UpdateHotelRequestDTO requestDTO
    ){
        return null;
    }
    @GetMapping("/{name}")
    public ResponseEntity<HotelResponseDTO> getHotelByName(@PathVariable("name") String name){
        return null;
    }
}
