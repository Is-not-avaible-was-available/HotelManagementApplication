package com.learning.hotelmanagementapplication.Controllers;

import com.learning.hotelmanagementapplication.DTOs.LogOutRequestDTO;
import com.learning.hotelmanagementapplication.DTOs.LoginRequestDTO;
import com.learning.hotelmanagementapplication.DTOs.SignUpRequestDTO;
import com.learning.hotelmanagementapplication.DTOs.UserResponseDTO;
import com.learning.hotelmanagementapplication.Exceptions.AlreadyPresentException;
import com.learning.hotelmanagementapplication.Exceptions.BadCredentialException;
import com.learning.hotelmanagementapplication.Exceptions.NotFoundException;
import com.learning.hotelmanagementapplication.Services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequestDTO requestDTO){
        try {
            authService.signUp(requestDTO.getName(), requestDTO.getEmail(), requestDTO.getPassword(),
                    requestDTO.getMobile(), requestDTO.getUserType());
        } catch (AlreadyPresentException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>("Sign up successfully for !" + requestDTO.getName(),
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody LoginRequestDTO requestDTO){

        try {
            return authService.login(requestDTO.getEmail(), requestDTO.getPassword());
        } catch (BadCredentialException | NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody LogOutRequestDTO requestDTO){

        authService.logout(requestDTO.getToken(), requestDTO.getUserId());
        return new ResponseEntity<>("Logged Out Successfully", HttpStatus.OK);
    }
}
