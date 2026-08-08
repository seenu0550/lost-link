package com.lostlink.controller;

import com.lostlink.dto.ClaimRequestDTO;
import com.lostlink.service.ClaimRequestService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/claims")
public class ClaimRequestController {

    private final ClaimRequestService claimRequestService;

    public ClaimRequestController(ClaimRequestService claimRequestService) {
        this.claimRequestService = claimRequestService;
    }


    @PostMapping
    public ClaimRequestDTO createClaimRequest(
          @Valid @RequestBody ClaimRequestDTO claimRequestDTO) {

        return claimRequestService.saveClaimRequest(claimRequestDTO);
    }


    @GetMapping
    public List<ClaimRequestDTO> getAllClaimRequests() {

        return claimRequestService.getAllClaimRequests();
    }


    @GetMapping("/{id}")
    public ClaimRequestDTO getClaimRequestById(
            @PathVariable Long id) {

        return claimRequestService.getClaimRequestById(id);
    }


    @PutMapping("/{id}")
    public ClaimRequestDTO updateClaimRequest(
            @PathVariable Long id,
            @Valid
            @RequestBody ClaimRequestDTO claimRequestDTO) {

        return claimRequestService.updateClaimRequest(id, claimRequestDTO);
    }


    @DeleteMapping("/{id}")
    public String deleteClaimRequest(
            @PathVariable Long id) {

        claimRequestService.deleteClaimRequest(id);

        return "Claim Request deleted successfully";
    }


    @GetMapping("/pending")
    public List<ClaimRequestDTO> getPendingClaims() {

        return claimRequestService.getPendingClaims();
    }


    @GetMapping("/user/{userId}")
    public List<ClaimRequestDTO> getClaimsByUser(
            @PathVariable Long userId) {

        return claimRequestService.getClaimsByUser(userId);
    }


    @GetMapping("/status/{status}")
    public List<ClaimRequestDTO> getClaimsByStatus(
            @PathVariable String status) {

        return claimRequestService.getClaimsByStatus(status);
    }


    @PutMapping("/{id}/approve")
    public ClaimRequestDTO approveClaim(
            @PathVariable Long id) {

        return claimRequestService.approveClaim(id);
    }


    @PutMapping("/{id}/reject")
    public ClaimRequestDTO rejectClaim(
            @PathVariable Long id) {

        return claimRequestService.rejectClaim(id);
    }

}