package com.learning.hotelmanagementapplication.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Booking extends BaseModel{
    private String referenceNumber;
    @ManyToOne
    private User user;
    @Enumerated(value = EnumType.ORDINAL)
    private BookingStatus bookingStatus;
    @ManyToOne
    private Hotel hotel;
    @ManyToOne
    private Room room;
}
