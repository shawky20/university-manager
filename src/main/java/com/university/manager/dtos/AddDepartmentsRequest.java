package com.university.manager.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.university.manager.entities.Unit;
import lombok.Data;

import java.util.List;

@Data
public class AddDepartmentsRequest {
    @JsonProperty("departments")
    private List<UnitDTO> departments;

}
