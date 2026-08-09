package com.lostlink.controller;

import com.lostlink.repository.ClaimRequestRepository;
import com.lostlink.repository.FoundItemRepository;
import com.lostlink.repository.LostItemRepository;
import com.lostlink.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final LostItemRepository lostItemRepository;
    private final FoundItemRepository foundItemRepository;
    private final ClaimRequestRepository claimRequestRepository;
    private final UserRepository userRepository;

    public DashboardController(LostItemRepository lostItemRepository,
                               FoundItemRepository foundItemRepository,
                               ClaimRequestRepository claimRequestRepository,
                               UserRepository userRepository) {
        this.lostItemRepository = lostItemRepository;
        this.foundItemRepository = foundItemRepository;
        this.claimRequestRepository = claimRequestRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/admin/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getAdminStats() {
        return ResponseEntity.ok(Map.of(
                "totalUsers", userRepository.count(),
                "totalLostItems", lostItemRepository.count(),
                "totalFoundItems", foundItemRepository.count(),
                "totalClaims", claimRequestRepository.count(),
                "pendingClaims", claimRequestRepository.findByStatus("PENDING").size(),
                "approvedClaims", claimRequestRepository.findByStatus("APPROVED").size(),
                "rejectedClaims", claimRequestRepository.findByStatus("REJECTED").size()
        ));
    }

    @GetMapping("/user/{userId}/stats")
    public ResponseEntity<Map<String, Object>> getUserStats(@PathVariable Long userId) {
        return ResponseEntity.ok(Map.of(
                "myLostItems", lostItemRepository.findByUserId(userId).size(),
                "myFoundItems", foundItemRepository.findByUserId(userId).size(),
                "myClaims", claimRequestRepository.findByUserId(userId).size(),
                "myPendingClaims", claimRequestRepository.findByUserId(userId).stream()
                        .filter(c -> "PENDING".equals(c.getStatus())).count(),
                "myApprovedClaims", claimRequestRepository.findByUserId(userId).stream()
                        .filter(c -> "APPROVED".equals(c.getStatus())).count()
        ));
    }
}
