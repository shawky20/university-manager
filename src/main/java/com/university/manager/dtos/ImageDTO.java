package com.university.manager.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ImageDTO {
    @JsonProperty("URL")
    private String url;
}
