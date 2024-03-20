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
import com.learning.hotelmanagementapplication.Repositories.RoomTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomTypeRepository roomTypeRepository;

    public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository,
                       RoomTypeRepository roomTypeRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
        this.roomTypeRepository = roomTypeRepository;
    }

    public void addNewRoom(int roomNumber, String roomType, Long hotelId) throws AlreadyPresentException, NotFoundException {

        Optional<Hotel> optionalHotel=hotelRepository.findById(hotelId);
        if(optionalHotel.isEmpty()){
            throw new NotFoundException("Hotel not found!");
        }
        Hotel hotel = optionalHotel.get();

        Optional<Room> roomOptional = roomRepository.findByRoomNumberAndHotel(roomNumber, hotel);
        if(roomOptional.isPresent()){
            throw new AlreadyPresentException("Room number already present!");
        }

        Optional<RoomType> roomTypeOptional = roomTypeRepository.findByRoomType(roomType);

        RoomType roomType1 = null;
        if(roomTypeOptional.isEmpty()){
            throw new NotFoundException("room type not found!");
        }else{
            roomType1 = roomTypeOptional.get();
        }
        Room room = Room
                .builder()
                .roomStatus(RoomStatus.AVAILABLE)
                .roomType(roomType1)
                .roomNumber(roomNumber)
                .hotel(hotel)
                .build();

        Room savedRoom = roomRepository.save(room);
    }

    public void updateRoomDetails(int roomNumber, String roomType,
                                  RoomStatus  roomStatus, Long roomId) throws NotFoundException {

        Optional<Room> roomOptional = roomRepository.findById(roomId);
        if(roomOptional.isEmpty()){
            throw new NotFoundException("Room not found!");
        }
        Optional<RoomType> roomTypeOptional = roomTypeRepository.findByRoomType(roomType);

        RoomType roomType1 =  null;
        if(roomTypeOptional.isEmpty()){
            roomType1 = new RoomType();
            roomType1.setRoomType(roomType);
            roomType1.setPrice(1000);
        }else{
            roomType1 = roomTypeOptional.get();
        }
        Room room= new Room();
        room.setRoomNumber(roomNumber);
        room.setRoomStatus(roomStatus);
        room.setRoomType(roomType1);

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

    public void createRoomType(String roomType, Integer price, Integer capacity) throws NotFoundException{

        Optional<RoomType> roomTypeOptional = roomTypeRepository.findByRoomType(roomType);
        if(roomTypeOptional.isPresent()){
            throw new NotFoundException("Already present!");
        }
        RoomType roomType1 = new RoomType(roomType, price,capacity);

        roomTypeRepository.save(roomType1);
    }
}
