package com.lostlink.mapper;

import com.lostlink.dto.ClaimRequestDTO;
import com.lostlink.entity.ClaimRequest;
import com.lostlink.entity.FoundItem;
import com.lostlink.entity.LostItem;
import com.lostlink.entity.User;

public class ClaimRequestMapper {

    public static ClaimRequestDTO toDTO(ClaimRequest claimRequest) {

        if (claimRequest == null) {
            return null;
        }

        ClaimRequestDTO dto = new ClaimRequestDTO();

        dto.setId(claimRequest.getId());

        if (claimRequest.getUser() != null) {
            dto.setUserId(claimRequest.getUser().getId());
            dto.setUserName(claimRequest.getUser().getName());
        }

        if (claimRequest.getLostItem() != null) {
            dto.setLostItemId(claimRequest.getLostItem().getId());
            dto.setLostItemName(claimRequest.getLostItem().getItemName());
        }

        if (claimRequest.getFoundItem() != null) {
            dto.setFoundItemId(claimRequest.getFoundItem().getId());
            dto.setFoundItemName(claimRequest.getFoundItem().getItemName());
        }

        dto.setMessage(claimRequest.getMessage());
        dto.setProof(claimRequest.getProof());
        dto.setStatus(claimRequest.getStatus());
        dto.setRequestDate(claimRequest.getRequestDate());

        return dto;
    }

    public static ClaimRequest toEntity(ClaimRequestDTO dto) {

        if (dto == null) {
            return null;
        }

        ClaimRequest claimRequest = new ClaimRequest();

        claimRequest.setId(dto.getId());

        if (dto.getUserId() != null) {
            User user = new User();
            user.setId(dto.getUserId());
            claimRequest.setUser(user);
        }

        if (dto.getLostItemId() != null) {
            LostItem lostItem = new LostItem();
            lostItem.setId(dto.getLostItemId());
            claimRequest.setLostItem(lostItem);
        }

        if (dto.getFoundItemId() != null) {
            FoundItem foundItem = new FoundItem();
            foundItem.setId(dto.getFoundItemId());
            claimRequest.setFoundItem(foundItem);
        }

        claimRequest.setMessage(dto.getMessage());
        claimRequest.setProof(dto.getProof());
        claimRequest.setStatus(dto.getStatus());
        claimRequest.setRequestDate(dto.getRequestDate());

        return claimRequest;
    }

}