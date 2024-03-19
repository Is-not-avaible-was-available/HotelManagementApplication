package com.learning.hotelmanagementapplication.Controllers;

import com.learning.hotelmanagementapplication.DTOs.AddHotelRequestDTO;
import com.learning.hotelmanagementapplication.DTOs.HotelResponseDTO;
import com.learning.hotelmanagementapplication.DTOs.UpdateHotelRequestDTO;
import com.learning.hotelmanagementapplication.Exceptions.AlreadyPresentException;
import com.learning.hotelmanagementapplication.Exceptions.NotFoundException;
import com.learning.hotelmanagementapplication.Models.Hotel;
import com.learning.hotelmanagementapplication.Services.HotelService;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    private final HotelService hotelService;
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping
    public ResponseEntity<String> addAHotel(@RequestBody AddHotelRequestDTO requestDTO){

        try {
            hotelService.addNewHotel(requestDTO.getName(),
                    requestDTO.getAddress(), requestDTO.getCity());
        } catch (AlreadyPresentException e) {
            throw new RuntimeException(e);
        }

        return  ResponseEntity.ok("Added a new Hotel named:  " + requestDTO.getName());
    }

    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotelDetails(){
        List<Hotel> hotels = hotelService.getAllHotels();
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @PutMapping("/{hotel}")
    public ResponseEntity<Hotel> updateHotelDetails(
            @RequestBody UpdateHotelRequestDTO requestDTO,@PathVariable("hotel") Long hotelId
    ){
        Hotel hotel = null;
        try {
            hotel= hotelService.updateHotelDetails(requestDTO.getName(), requestDTO.getAddress(),
                    requestDTO.getRating(), hotelId, requestDTO.getCity());
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(hotel,  HttpStatus.OK);
    }
    @GetMapping("/{name}")
    public ResponseEntity<Hotel> getHotelByName(@PathVariable("name") String name){
        Hotel hotel = null;
        try {
           hotel = hotelService.findByName(name);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }
}
