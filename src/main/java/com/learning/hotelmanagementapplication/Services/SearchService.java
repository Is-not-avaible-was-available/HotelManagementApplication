package com.learning.hotelmanagementapplication.Services;

import com.learning.hotelmanagementapplication.DTOs.HotelResponseDTO;
import com.learning.hotelmanagementapplication.DTOs.RoomResponseDTO;
import com.learning.hotelmanagementapplication.Models.Hotel;
import com.learning.hotelmanagementapplication.Models.Room;
import com.learning.hotelmanagementapplication.Repositories.HotelRepository;
import com.learning.hotelmanagementapplication.Repositories.RoomRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class SearchService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    public SearchService(RoomRepository roomRepository, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    public Page<RoomResponseDTO> searchRoomsByHotelName(String hotelName, int pageNumber, int pageSize,
                                                        String sortValue){
        Sort sort = Sort.by(sortValue).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Room> rooms = roomRepository.findAllByHotel_Name(hotelName, pageable);

        List<RoomResponseDTO> roomResponseDTOS =
                rooms
                        .stream()
                        .map(Mappers::convertToResponseDTO)
                        .toList();

        return new PageImpl<>(roomResponseDTOS,
                rooms.getPageable(), rooms.getTotalElements());
    }

    public Page<RoomResponseDTO> searchRoomsByRoomTypes(String roomType, int pageNumber,
                                       int pageSize, String sortValue){
        Pageable pageable = PageRequest
                .of(pageNumber, pageSize)
                .withSort(Sort.by(sortValue).descending());


        Page<Room> roomPage = roomRepository.findAllByRoomType_RoomType(roomType, pageable);
        List<RoomResponseDTO> roomResponseDTOS = roomPage
                .stream()
                .map(Mappers::convertToResponseDTO)
                .toList();

        return new PageImpl<>(roomResponseDTOS,
                roomPage.getPageable(), roomPage.getTotalPages());
    }

    public Page<Hotel> searchHotelByCity(String city, int pageSize, int pageNumber, String sortValue){

        Pageable pageable = PageRequest
                .of(pageNumber, pageSize)
                .withSort(Sort.by(sortValue).descending());

        return hotelRepository.findAllByCityContaining(city, pageable);
    }

    public Page<RoomResponseDTO> searchRoomsByHotelNameAndRoomType( String hotelName,
                                                         String roomType, int pageSize,
                                                        int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber,pageSize);

        Page<Room> roomPage = roomRepository.findAllByRoomType_RoomTypeAndHotel_Name(roomType, hotelName, pageable);
        List<RoomResponseDTO> roomResponseDTOS = roomPage.stream().map(Mappers::convertToResponseDTO).toList();

        return new PageImpl<>(roomResponseDTOS, roomPage.getPageable(),
                roomPage.getTotalPages());
    }
}
