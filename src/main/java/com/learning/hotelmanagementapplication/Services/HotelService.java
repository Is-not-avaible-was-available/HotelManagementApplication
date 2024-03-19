package com.learning.hotelmanagementapplication.Services;

import com.learning.hotelmanagementapplication.DTOs.HotelResponseDTO;
import com.learning.hotelmanagementapplication.Exceptions.AlreadyPresentException;
import com.learning.hotelmanagementapplication.Exceptions.NotFoundException;
import com.learning.hotelmanagementapplication.Models.Hotel;
import com.learning.hotelmanagementapplication.Models.Room;
import com.learning.hotelmanagementapplication.Repositories.HotelRepository;
import com.learning.hotelmanagementapplication.Repositories.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    public HotelService(HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    public void addNewHotel(String name, String address, String city) throws AlreadyPresentException {
        Optional<Hotel> optionalHotel = hotelRepository.findByName(name);
        if(optionalHotel.isPresent()){
            throw new AlreadyPresentException("Hotel already present!");
        }
        Hotel hotel = new Hotel();
        hotel.setAddress(address);
        hotel.setName(name);
        hotel.setRating(10.0);
        hotel.setCity(city);

        hotelRepository.save(hotel);
    }

    public List<Hotel> getAllHotels(){
        return hotelRepository.findAll();
    }

    public Hotel findByName(String name) throws NotFoundException {
        Optional<Hotel> hotelOptional= hotelRepository.findByName(name);
        if(hotelOptional.isEmpty()){
            throw new NotFoundException("Not Found!");
        }

        return hotelOptional.get();
    }

    public Hotel updateHotelDetails(String name, String address, double rating, Long id,
                                    String city) throws NotFoundException{
        Optional<Hotel> optionalHotel = hotelRepository.findByName(name);
        if(optionalHotel.isEmpty()){
            throw new NotFoundException("Not found!");
        }

        Hotel hotel = optionalHotel.get();
        hotel.setRating(rating);
        hotel.setName(name);
        hotel.setAddress(address);
        hotel.setCity(city);
        return hotelRepository.save(hotel);
    }
}
