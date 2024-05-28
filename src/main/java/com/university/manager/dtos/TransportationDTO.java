package com.university.manager.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransportationDTO {
    @JsonProperty("address")
    private String address;
    @JsonProperty("date")
    private String date;
    @JsonProperty("price")
    private Long price;
}
