package com.learning.hotelmanagementapplication.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "users")
public class User extends BaseModel{
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private String mobile;
    @Enumerated(EnumType.ORDINAL)
    private  UserType userType;
}
