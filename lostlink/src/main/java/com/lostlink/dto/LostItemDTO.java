package com.lostlink.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LostItemDTO {

    private Long id;

    @NotBlank(message = "Item name is required")
    private String itemName;


    @NotBlank(message = "Category is required")
    private String category;


    @NotBlank(message = "Description is required")
    private String description;


    @NotBlank(message = "Location is required")
    private String locationLost;


    private LocalDate dateLost;


    private String imageUrl;


    private String status;


    private Long userId;
}