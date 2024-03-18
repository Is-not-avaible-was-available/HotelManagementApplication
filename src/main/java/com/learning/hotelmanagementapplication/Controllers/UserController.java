package com.learning.hotelmanagementapplication.Controllers;

import com.learning.hotelmanagementapplication.DTOs.UserResponseDTO;
import com.learning.hotelmanagementapplication.Exceptions.NotFoundException;
import com.learning.hotelmanagementapplication.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserDetails(@PathVariable("id") Long id){
        UserResponseDTO responseDTO = null;
        try {
            responseDTO = userService.findUserDetails(id);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
       return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
