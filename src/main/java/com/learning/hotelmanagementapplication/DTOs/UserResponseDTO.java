package com.learning.hotelmanagementapplication.DTOs;

import com.learning.hotelmanagementapplication.Models.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private String name;
    private String email;
    private String mobile;
    private UserType userType;
}
