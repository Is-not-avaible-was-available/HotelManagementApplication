package com.learning.hotelmanagementapplication.Controllers;

import com.learning.hotelmanagementapplication.DTOs.RoomResponseDTO;
import com.learning.hotelmanagementapplication.DTOs.SearchByHotelNameRequestDTO;
import com.learning.hotelmanagementapplication.DTOs.SearchByRoomTypeRequestDTO;
import com.learning.hotelmanagementapplication.DTOs.SearchHotelByCityRequestDTO;
import com.learning.hotelmanagementapplication.Models.Hotel;
import com.learning.hotelmanagementapplication.Services.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/hotelName")
    public ResponseEntity<Page<RoomResponseDTO>> searchRoomsByHotelName(@RequestBody SearchByHotelNameRequestDTO requestDTO){
      Page<RoomResponseDTO> responseDTOS=  searchService.searchRoomsByHotelName(requestDTO.getQuery(),
                requestDTO.getPageNumber(), requestDTO.getPageSize(), requestDTO.getSortValue());

      return new ResponseEntity<>(responseDTOS, HttpStatus.OK);
    }
    @PostMapping("/roomType")
    public ResponseEntity<Page<RoomResponseDTO>> searchRoomsByRoomType(@RequestBody SearchByRoomTypeRequestDTO requestDTO){

       Page<RoomResponseDTO> responseDTOS= searchService.searchRoomsByRoomTypes(requestDTO.getQuery(), requestDTO.getPageNumber(),
                requestDTO.getPageSize(), requestDTO.getSortValue());

       return new ResponseEntity<>(responseDTOS, HttpStatus.OK);
    }

    @PostMapping("/city")

    public ResponseEntity<Page<Hotel>> searchHotelsByCity(@RequestBody SearchHotelByCityRequestDTO requestDTO){

        Page<Hotel> hotels = searchService.searchHotelByCity(requestDTO.getQuery(),
                requestDTO.getPageSize(), requestDTO.getPageNumber(), requestDTO.getSortValue());
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @GetMapping("/hotel")
    public ResponseEntity<Page<RoomResponseDTO>> searchRoomByHotelNameAndRoomType(@RequestParam String hotelName,
                                                                                  @RequestParam String roomType,
                                                                                  @RequestParam int pageSize,
                                                                                  @RequestParam int pageNumber){
        Page<RoomResponseDTO> responseDTOPage = searchService.searchRoomsByHotelNameAndRoomType(hotelName, roomType,
                pageSize,pageNumber);
        return new ResponseEntity<>(responseDTOPage, HttpStatus.OK);
    }
}
