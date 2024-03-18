package com.learning.hotelmanagementapplication.DTOs;

import com.learning.hotelmanagementapplication.Models.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDTO {

    private String name;
    private String email;
    private String password;
    private UserType userType;
    private String mobile;
}
