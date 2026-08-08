package com.lostlink.mapper;

import com.lostlink.dto.LostItemDTO;
import com.lostlink.entity.LostItem;
import com.lostlink.entity.User;

public class LostItemMapper {

    public static LostItemDTO toDTO(LostItem lostItem) {

        if (lostItem == null) {
            return null;
        }

        LostItemDTO dto = new LostItemDTO();

        dto.setId(lostItem.getId());
        dto.setItemName(lostItem.getItemName());
        dto.setCategory(lostItem.getCategory());
        dto.setDescription(lostItem.getDescription());
        dto.setLocationLost(lostItem.getLocationLost());
        dto.setDateLost(lostItem.getDateLost());
        dto.setImageUrl(lostItem.getImageUrl());
        dto.setStatus(lostItem.getStatus());

        if (lostItem.getUser() != null) {
            dto.setUserId(lostItem.getUser().getId());
            dto.setUserName(lostItem.getUser().getName());
        }

        return dto;
    }

    public static LostItem toEntity(LostItemDTO dto) {

        if (dto == null) {
            return null;
        }

        LostItem lostItem = new LostItem();

        lostItem.setId(dto.getId());
        lostItem.setItemName(dto.getItemName());
        lostItem.setCategory(dto.getCategory());
        lostItem.setDescription(dto.getDescription());
        lostItem.setLocationLost(dto.getLocationLost());
        lostItem.setDateLost(dto.getDateLost());
        lostItem.setImageUrl(dto.getImageUrl());
        lostItem.setStatus(dto.getStatus());

        if (dto.getUserId() != null) {
            User user = new User();
            user.setId(dto.getUserId());
            lostItem.setUser(user);
        }

        return lostItem;
    }

}