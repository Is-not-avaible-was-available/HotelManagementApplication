package com.learning.hotelmanagementapplication.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
public class Room extends BaseModel{
    private int roomNumber;
    private int capacity;
    @Enumerated(value = EnumType.ORDINAL)
    private RoomStatus roomStatus;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private RoomType roomType;
    @ManyToOne
    private Hotel hotel;
    @OneToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.REMOVE
    })
    private List<Booking> bookings;
    public Room() {

    }
}
