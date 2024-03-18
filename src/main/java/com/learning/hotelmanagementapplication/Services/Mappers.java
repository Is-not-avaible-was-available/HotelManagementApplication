package com.learning.hotelmanagementapplication.Services;

import com.learning.hotelmanagementapplication.DTOs.HotelResponseDTO;
import com.learning.hotelmanagementapplication.DTOs.RoomResponseDTO;
import com.learning.hotelmanagementapplication.DTOs.UserResponseDTO;
import com.learning.hotelmanagementapplication.Models.Hotel;
import com.learning.hotelmanagementapplication.Models.Room;
import com.learning.hotelmanagementapplication.Models.User;
import com.learning.hotelmanagementapplication.Repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class Mappers {
    public static RoomResponseDTO convertToResponseDTO(Room room){
        RoomResponseDTO roomResponseDTO = new RoomResponseDTO();
        roomResponseDTO.setRoomStatus(room.getRoomStatus());
        roomResponseDTO.setRoomNumber(room.getRoomNumber());
        roomResponseDTO.setRoomType(room.getRoomType());
        roomResponseDTO.setCapacity(room.getCapacity());
        return roomResponseDTO;
    }

    public HotelResponseDTO convertToResponseDTO(Hotel hotel){
        HotelResponseDTO hotelResponseDTO = new HotelResponseDTO();
        hotelResponseDTO.setAddress(hotel.getAddress());
        hotelResponseDTO.setName(hotel.getName());
        hotelResponseDTO.setRating(hotel.getRating());
        return hotelResponseDTO;
    }

    public static UserResponseDTO convertToResponseDTO(User user){
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setUserType(user.getUserType());
        userResponseDTO.setMobile(user.getMobile());
        return userResponseDTO;
    }
}
