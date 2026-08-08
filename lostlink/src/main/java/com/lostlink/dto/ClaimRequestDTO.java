package com.lostlink.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClaimRequestDTO {

    private Long id;

    private Long userId;

    private String userName;

    private Long lostItemId;

    private String lostItemName;


    private Long foundItemId;

    private String foundItemName;

    @NotBlank(message = "Claim message is required")
    private String message;

    @NotBlank(message = "Proof is required")
    private String proof;

    private String status;

    private LocalDateTime requestDate;
}