package com.learning.hotelmanagementapplication.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
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
    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
    private List<Room>  rooms;
}
