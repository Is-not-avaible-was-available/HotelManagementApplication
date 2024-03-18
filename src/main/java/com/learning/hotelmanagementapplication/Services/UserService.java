package com.learning.hotelmanagementapplication.Services;

import com.learning.hotelmanagementapplication.DTOs.UserResponseDTO;
import com.learning.hotelmanagementapplication.Exceptions.NotFoundException;
import com.learning.hotelmanagementapplication.Models.User;
import com.learning.hotelmanagementapplication.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO findUserDetails(Long id) throws NotFoundException {
        Optional<User> optionalUser= userRepository.findById(id);

        if(optionalUser.isEmpty()){
            throw new NotFoundException("Not found!");
        }

        User user = optionalUser.get();
        return Mappers.convertToResponseDTO(user);
    }
}
