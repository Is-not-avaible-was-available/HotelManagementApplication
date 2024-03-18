package com.learning.hotelmanagementapplication.Services;

import com.learning.hotelmanagementapplication.DTOs.RoomResponseDTO;
import com.learning.hotelmanagementapplication.Exceptions.AlreadyPresentException;
import com.learning.hotelmanagementapplication.Exceptions.NotFoundException;
import com.learning.hotelmanagementapplication.Models.Hotel;
import com.learning.hotelmanagementapplication.Models.Room;
import com.learning.hotelmanagementapplication.Models.RoomStatus;
import com.learning.hotelmanagementapplication.Models.RoomType;
import com.learning.hotelmanagementapplication.Repositories.HotelRepository;
import com.learning.hotelmanagementapplication.Repositories.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    public void addNewRoom(int roomNumber, RoomType roomType, int capacity, Long hotelId) throws AlreadyPresentException, NotFoundException {
        Optional<Room> roomOptional = roomRepository.findByRoomNumber(roomNumber);
        if(roomOptional.isPresent()){
            throw new AlreadyPresentException("Room number already present!");
        }
        Optional<Hotel> optionalHotel=hotelRepository.findById(hotelId);
        if(optionalHotel.isEmpty()){
            throw new NotFoundException("Hotel not found!");
        }
        Hotel hotel = optionalHotel.get();

        Room room = Room
                .builder()
                .roomStatus(RoomStatus.AVAILABLE)
                .roomType(roomType)
                .roomNumber(roomNumber)
                .capacity(capacity).hotel(hotel)
                .build();

        Room savedRoom = roomRepository.save(room);
    }

    public void updateRoomDetails(int roomNumber, int capacity, RoomType roomType,
                                  RoomStatus  roomStatus, Long roomId) throws NotFoundException {

        Optional<Room> roomOptional = roomRepository.findById(roomId);
        if(roomOptional.isEmpty()){
            throw new NotFoundException("Room not found!");
        }
        Room room= new Room();
        room.setRoomNumber(roomNumber);
        room.setCapacity(capacity);
        room.setRoomStatus(roomStatus);
        room.setRoomType(roomType);

        Room saved = roomRepository.save(room);
    }

    public List<RoomResponseDTO> getAllRooms(){
        List<Room> rooms = roomRepository.findAll();

        return rooms
                .stream()
                .map(Mappers::convertToResponseDTO)
                .toList();
    }

    public RoomResponseDTO getRoomDetails(Long id) throws NotFoundException {
        Optional<Room> roomOptional = roomRepository.findById(id);
        if(roomOptional.isEmpty()){
            throw new NotFoundException("Room not found");
        }

        Room room = roomOptional.get();
        return Mappers.convertToResponseDTO(room);
    }

    public List<RoomResponseDTO> getRoomsByHotelName(String name){
        List<Room> rooms = roomRepository.findAllByHotel_Name(name);

        return rooms
                .stream()
                .map(Mappers::convertToResponseDTO)
                .toList();
    }
}
