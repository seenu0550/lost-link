package com.lostlink.service;

import com.lostlink.dto.ClaimRequestDTO;
import com.lostlink.entity.ClaimRequest;
import com.lostlink.exception.ResourceNotFoundException;
import com.lostlink.mapper.ClaimRequestMapper;
import com.lostlink.repository.ClaimRequestRepository;
import com.lostlink.repository.FoundItemRepository;
import com.lostlink.repository.LostItemRepository;
import com.lostlink.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClaimRequestService {

    private final ClaimRequestRepository claimRequestRepository;
    private final UserRepository userRepository;
    private final LostItemRepository lostItemRepository;
    private final FoundItemRepository foundItemRepository;

    public ClaimRequestService(
            ClaimRequestRepository claimRequestRepository,
            UserRepository userRepository,
            LostItemRepository lostItemRepository,
            FoundItemRepository foundItemRepository) {

        this.claimRequestRepository = claimRequestRepository;
        this.userRepository = userRepository;
        this.lostItemRepository = lostItemRepository;
        this.foundItemRepository = foundItemRepository;
    }

    public ClaimRequestDTO saveClaimRequest(ClaimRequestDTO claimRequestDTO) {

        ClaimRequest claimRequest = ClaimRequestMapper.toEntity(claimRequestDTO);

        ClaimRequest savedRequest = claimRequestRepository.save(claimRequest);

        return ClaimRequestMapper.toDTO(savedRequest);
    }


    public List<ClaimRequestDTO> getAllClaimRequests() {

        return claimRequestRepository.findAll()
                .stream()
                .map(ClaimRequestMapper::toDTO)
                .collect(Collectors.toList());
    }


    public ClaimRequestDTO getClaimRequestById(Long id) {

        return claimRequestRepository.findById(id)
                .map(ClaimRequestMapper::toDTO)
                .orElse(null);
    }


    public ClaimRequestDTO updateClaimRequest(Long id, ClaimRequestDTO updatedDTO) {

        ClaimRequest existingRequest =
                claimRequestRepository.findById(id)
                        .orElse(null);


        if (existingRequest != null) {

            existingRequest.setMessage(updatedDTO.getMessage());
            existingRequest.setProof(updatedDTO.getProof());
            existingRequest.setStatus(updatedDTO.getStatus());
            existingRequest.setRequestDate(updatedDTO.getRequestDate());

            if (updatedDTO.getUserId() != null) {
                existingRequest.setUser(
                        userRepository.findById(updatedDTO.getUserId())
                                .orElseThrow(() ->
                                        new ResourceNotFoundException("User not found"))
                );
            }

            if (updatedDTO.getLostItemId() != null) {
                existingRequest.setLostItem(
                        lostItemRepository.findById(updatedDTO.getLostItemId())
                                .orElseThrow(() ->
                                        new ResourceNotFoundException("Lost Item not found"))
                );
            }

            if (updatedDTO.getFoundItemId() != null) {
                existingRequest.setFoundItem(
                        foundItemRepository.findById(updatedDTO.getFoundItemId())
                                .orElseThrow(() ->
                                        new ResourceNotFoundException("Found Item not found"))
                );
            }


            ClaimRequest updatedRequest =
                    claimRequestRepository.save(existingRequest);


            return ClaimRequestMapper.toDTO(updatedRequest);
        }


        return null;
    }


    public void deleteClaimRequest(Long id) {

        claimRequestRepository.deleteById(id);
    }


    public ClaimRequestDTO approveClaim(Long id) {

        ClaimRequest claimRequest =
                claimRequestRepository.findById(id)
                        .orElse(null);


        if (claimRequest != null) {

            claimRequest.setStatus("APPROVED");

            ClaimRequest savedRequest =
                    claimRequestRepository.save(claimRequest);

            return ClaimRequestMapper.toDTO(savedRequest);
        }


        return null;
    }


    public ClaimRequestDTO rejectClaim(Long id) {

        ClaimRequest claimRequest =
                claimRequestRepository.findById(id)
                        .orElse(null);


        if (claimRequest != null) {

            claimRequest.setStatus("REJECTED");

            ClaimRequest savedRequest =
                    claimRequestRepository.save(claimRequest);

            return ClaimRequestMapper.toDTO(savedRequest);
        }


        return null;
    }


    public List<ClaimRequestDTO> getPendingClaims() {

        return claimRequestRepository.findByStatus("PENDING")
                .stream()
                .map(ClaimRequestMapper::toDTO)
                .collect(Collectors.toList());
    }


    public List<ClaimRequestDTO> getClaimsByUser(Long userId) {

        return claimRequestRepository.findByUserId(userId)
                .stream()
                .map(ClaimRequestMapper::toDTO)
                .collect(Collectors.toList());
    }


    public List<ClaimRequestDTO> getClaimsByStatus(String status) {

        return claimRequestRepository.findByStatus(status)
                .stream()
                .map(ClaimRequestMapper::toDTO)
                .collect(Collectors.toList());
    }

}