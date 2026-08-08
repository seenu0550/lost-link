package com.lostlink.mapper;

import com.lostlink.dto.FoundItemDTO;
import com.lostlink.entity.FoundItem;
import com.lostlink.entity.User;

public class FoundItemMapper {

    public static FoundItemDTO toDTO(FoundItem foundItem) {

        if (foundItem == null) {
            return null;
        }

        FoundItemDTO dto = new FoundItemDTO();

        dto.setId(foundItem.getId());
        dto.setItemName(foundItem.getItemName());
        dto.setCategory(foundItem.getCategory());
        dto.setDescription(foundItem.getDescription());
        dto.setLocationFound(foundItem.getLocationFound());
        dto.setDateFound(foundItem.getDateFound());
        dto.setImageUrl(foundItem.getImageUrl());
        dto.setStatus(foundItem.getStatus());

        if (foundItem.getUser() != null) {
            dto.setUserId(foundItem.getUser().getId());
            dto.setUserName(foundItem.getUser().getName());
        }

        return dto;
    }

    public static FoundItem toEntity(FoundItemDTO dto) {

        if (dto == null) {
            return null;
        }

        FoundItem foundItem = new FoundItem();

        foundItem.setId(dto.getId());
        foundItem.setItemName(dto.getItemName());
        foundItem.setCategory(dto.getCategory());
        foundItem.setDescription(dto.getDescription());
        foundItem.setLocationFound(dto.getLocationFound());
        foundItem.setDateFound(dto.getDateFound());
        foundItem.setImageUrl(dto.getImageUrl());
        foundItem.setStatus(dto.getStatus());

        if (dto.getUserId() != null) {
            User user = new User();
            user.setId(dto.getUserId());
            foundItem.setUser(user);
        }

        return foundItem;
    }

}