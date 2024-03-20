package com.learning.hotelmanagementapplication.Services;

import com.learning.hotelmanagementapplication.DTOs.HotelResponseDTO;
import com.learning.hotelmanagementapplication.DTOs.RoomResponseDTO;
import com.learning.hotelmanagementapplication.Exceptions.NotFoundException;
import com.learning.hotelmanagementapplication.Models.Booking;
import com.learning.hotelmanagementapplication.Models.Hotel;
import com.learning.hotelmanagementapplication.Models.Room;
import com.learning.hotelmanagementapplication.Repositories.BookingRepository;
import com.learning.hotelmanagementapplication.Repositories.HotelRepository;
import com.learning.hotelmanagementapplication.Repositories.RoomRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final BookingRepository bookingRepository;

    public SearchService(RoomRepository roomRepository, HotelRepository hotelRepository,
                         BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
        this.bookingRepository = bookingRepository;
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

    public Page<HotelResponseDTO> searchHotelByCity(String city, int pageSize, int pageNumber, String sortValue){

        Sort sort = Sort.by(sortValue).descending();
        Pageable pageable = PageRequest
                .of(pageNumber, pageSize, sort);

        Page<Hotel> hotelPage= hotelRepository.findAllByCityContaining(city, pageable);
        List<Hotel> hotels = hotelPage.stream().toList();

        List<HotelResponseDTO> hotelResponseDTOS = hotels.stream().map(Mappers::convertToResponseDTO).toList();

        return new PageImpl<>(hotelResponseDTOS, hotelPage.getPageable(),
                hotelPage.getTotalElements());
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

    public Page<RoomResponseDTO> searchRoomBasedOnCheckInAndCheckOutDate(LocalDate checkIn, LocalDate checkOut,
                                                              String query, int pageSize, int pageNumber) throws NotFoundException {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Hotel> hotelPage = hotelRepository.findAllByCityContaining(query, pageable);
        List<Hotel> hotelsList = hotelPage.stream().toList();
        List<Room> rooms = new ArrayList<>();
        for (Hotel hotel : hotelsList) {
            rooms.addAll(hotel.getRooms());
        }
        List<Room> availableRooms = new ArrayList<>();

        for (Room room : rooms) {
            if (isAvailable(room, checkIn, checkOut)) {
                availableRooms.add(room);
            }
        }
        List<RoomResponseDTO> roomResponseDTOS = availableRooms
                .stream()
                .map(Mappers::convertToResponseDTO).
                toList();

        return new PageImpl<>(roomResponseDTOS, hotelPage.getPageable(),
                hotelPage.getTotalPages());
    }
    public boolean isAvailable(Room room, LocalDate checkIn, LocalDate checkOut){
        List<Booking> bookings = bookingRepository.findAllByRoom(room);
        for(Booking booking:bookings){
            LocalDate bookingCheckInDate = booking.getCheckInDate();
            LocalDate bookingCheckOutDate = booking.getCheckOutDate();

            if(checkIn.isBefore(bookingCheckOutDate) && checkOut.isAfter(bookingCheckInDate)){
                return false;
            }
        }
        return true;
    }
}
