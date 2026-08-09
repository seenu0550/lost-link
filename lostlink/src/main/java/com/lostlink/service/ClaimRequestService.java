package com.lostlink.service;

import com.lostlink.dto.ClaimRequestDTO;
import com.lostlink.entity.ClaimRequest;
import com.lostlink.exception.DuplicateClaimException;
import com.lostlink.exception.ResourceNotFoundException;
import com.lostlink.mapper.ClaimRequestMapper;
import com.lostlink.repository.ClaimRequestRepository;
import com.lostlink.repository.FoundItemRepository;
import com.lostlink.repository.LostItemRepository;
import com.lostlink.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClaimRequestService {

    private final ClaimRequestRepository claimRequestRepository;
    private final UserRepository userRepository;
    private final LostItemRepository lostItemRepository;
    private final FoundItemRepository foundItemRepository;
    private final NotificationService notificationService;

    public ClaimRequestService(ClaimRequestRepository claimRequestRepository,
                               UserRepository userRepository,
                               LostItemRepository lostItemRepository,
                               FoundItemRepository foundItemRepository,
                               NotificationService notificationService) {
        this.claimRequestRepository = claimRequestRepository;
        this.userRepository = userRepository;
        this.lostItemRepository = lostItemRepository;
        this.foundItemRepository = foundItemRepository;
        this.notificationService = notificationService;
    }

    public ClaimRequestDTO saveClaimRequest(ClaimRequestDTO dto) {
        if (dto.getUserId() != null) {
            userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getUserId()));
        }
        if (dto.getLostItemId() != null) {
            lostItemRepository.findById(dto.getLostItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Lost item not found with id: " + dto.getLostItemId()));
            if (dto.getUserId() != null && claimRequestRepository.existsByUserIdAndLostItemId(dto.getUserId(), dto.getLostItemId())) {
                throw new DuplicateClaimException("You have already submitted a claim for this lost item.");
            }
        }
        if (dto.getFoundItemId() != null) {
            foundItemRepository.findById(dto.getFoundItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Found item not found with id: " + dto.getFoundItemId()));
            if (dto.getUserId() != null && claimRequestRepository.existsByUserIdAndFoundItemId(dto.getUserId(), dto.getFoundItemId())) {
                throw new DuplicateClaimException("You have already submitted a claim for this found item.");
            }
        }

        ClaimRequest claim = ClaimRequestMapper.toEntity(dto);
        claim.setStatus("PENDING");
        claim.setRequestDate(LocalDate.now());

        return ClaimRequestMapper.toDTO(claimRequestRepository.save(claim));
    }

    public List<ClaimRequestDTO> getAllClaimRequests() {
        return claimRequestRepository.findAll().stream()
                .map(ClaimRequestMapper::toDTO).collect(Collectors.toList());
    }

    public ClaimRequestDTO getClaimRequestById(Long id) {
        return claimRequestRepository.findById(id)
                .map(ClaimRequestMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Claim request not found with id: " + id));
    }

    public ClaimRequestDTO updateClaimRequest(Long id, ClaimRequestDTO dto) {
        ClaimRequest existing = claimRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Claim request not found with id: " + id));

        existing.setMessage(dto.getMessage());
        existing.setProof(dto.getProof());
        existing.setStatus(dto.getStatus());

        if (dto.getUserId() != null) {
            existing.setUser(userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getUserId())));
        }
        if (dto.getLostItemId() != null) {
            existing.setLostItem(lostItemRepository.findById(dto.getLostItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Lost item not found with id: " + dto.getLostItemId())));
        }
        if (dto.getFoundItemId() != null) {
            existing.setFoundItem(foundItemRepository.findById(dto.getFoundItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Found item not found with id: " + dto.getFoundItemId())));
        }

        return ClaimRequestMapper.toDTO(claimRequestRepository.save(existing));
    }

    public void deleteClaimRequest(Long id) {
        if (!claimRequestRepository.existsById(id))
            throw new ResourceNotFoundException("Claim request not found with id: " + id);
        claimRequestRepository.deleteById(id);
    }

    public ClaimRequestDTO approveClaim(Long id) {
        return updateClaimStatus(id, "APPROVED");
    }

    public ClaimRequestDTO rejectClaim(Long id) {
        return updateClaimStatus(id, "REJECTED");
    }

    private ClaimRequestDTO updateClaimStatus(Long id, String status) {
        ClaimRequest claim = claimRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Claim request not found with id: " + id));
        claim.setStatus(status);
        ClaimRequestDTO result = ClaimRequestMapper.toDTO(claimRequestRepository.save(claim));
        if (claim.getUser() != null) {
            String itemName = claim.getLostItem() != null ? claim.getLostItem().getItemName()
                    : (claim.getFoundItem() != null ? claim.getFoundItem().getItemName() : "item");
            notificationService.sendNotification(claim.getUser().getId(),
                    "Your claim for '" + itemName + "' has been " + status + ".");
        }
        return result;
    }

    public List<ClaimRequestDTO> getPendingClaims() {
        return claimRequestRepository.findByStatus("PENDING").stream()
                .map(ClaimRequestMapper::toDTO).collect(Collectors.toList());
    }

    public List<ClaimRequestDTO> getClaimsByUser(Long userId) {
        if (!userRepository.existsById(userId))
            throw new ResourceNotFoundException("User not found with id: " + userId);
        return claimRequestRepository.findByUserId(userId).stream()
                .map(ClaimRequestMapper::toDTO).collect(Collectors.toList());
    }

    public List<ClaimRequestDTO> getClaimsByStatus(String status) {
        return claimRequestRepository.findByStatus(status).stream()
                .map(ClaimRequestMapper::toDTO).collect(Collectors.toList());
    }

    public List<ClaimRequestDTO> getClaimsByLostItem(Long lostItemId) {
        if (!lostItemRepository.existsById(lostItemId))
            throw new ResourceNotFoundException("Lost item not found with id: " + lostItemId);
        return claimRequestRepository.findByLostItemId(lostItemId).stream()
                .map(ClaimRequestMapper::toDTO).collect(Collectors.toList());
    }

    public List<ClaimRequestDTO> getClaimsByFoundItem(Long foundItemId) {
        if (!foundItemRepository.existsById(foundItemId))
            throw new ResourceNotFoundException("Found item not found with id: " + foundItemId);
        return claimRequestRepository.findByFoundItemId(foundItemId).stream()
                .map(ClaimRequestMapper::toDTO).collect(Collectors.toList());
    }
}
