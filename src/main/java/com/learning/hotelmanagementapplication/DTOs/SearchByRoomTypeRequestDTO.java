package com.learning.hotelmanagementapplication.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchByRoomTypeRequestDTO {

    private String query;
    private int pageSize;
    private int pageNumber;
    private String sortValue;
}
