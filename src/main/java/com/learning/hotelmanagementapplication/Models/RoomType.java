package com.learning.hotelmanagementapplication.Models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RoomType extends BaseModel{
    private String roomType;
    private Integer price;
    private Integer capacity;
}
