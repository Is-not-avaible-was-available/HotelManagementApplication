package com.learning.hotelmanagementapplication.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
    @Enumerated(value = EnumType.ORDINAL)
    private RoomType roomType;
    @ManyToOne
    private Hotel hotel;

    public Room() {

    }
}
