package com.lostlink.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FoundItemDTO {

    private Long id;

    @NotBlank(message = "Item name is required")
    private String itemName;


    @NotBlank(message = "Category is required")
    private String category;


    @NotBlank(message = "Description is required")
    private String description;


    @NotBlank(message = "Found location is required")
    private String locationFound;


    private LocalDate dateFound;


    private String imageUrl;


    private String status;


    private Long userId;

    private String userName;
}