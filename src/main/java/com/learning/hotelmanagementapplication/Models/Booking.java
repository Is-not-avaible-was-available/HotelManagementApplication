package com.learning.hotelmanagementapplication.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer price;

}
