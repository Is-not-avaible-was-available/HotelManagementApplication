package com.learning.hotelmanagementapplication.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Hotel extends BaseModel{
    private String name;
    private String address;
    private double rating;
    @OneToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.REMOVE
    })

    private List<Room>  rooms;
    private String city;
}
