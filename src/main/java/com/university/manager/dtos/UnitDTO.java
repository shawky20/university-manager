package com.university.manager.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.university.manager.constants.University;
import lombok.Data;

import java.util.List;

@Data
public class UnitDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("details")
    private String details;
    @JsonProperty("price")
    private String price;
    @JsonProperty("address")
    private String address;
    @JsonProperty("region")
    private String region;
    @JsonProperty("university")
    private String university; // the unit near this university
    @JsonProperty("transportations")
    private List<TransportationDTO> transportations;
    @JsonProperty("images")
    private List<ImageDTO> images;

}
