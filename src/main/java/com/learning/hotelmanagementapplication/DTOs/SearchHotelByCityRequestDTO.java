package com.learning.hotelmanagementapplication.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchHotelByCityRequestDTO {
    private int pageSize;
    private int pageNumber;
    private String query;
    private String sortValue;
}
