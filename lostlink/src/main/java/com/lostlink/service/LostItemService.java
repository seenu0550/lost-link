package com.lostlink.service;

import com.lostlink.dto.LostItemDTO;
import com.lostlink.entity.LostItem;
import com.lostlink.mapper.LostItemMapper;
import com.lostlink.repository.LostItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LostItemService {

    private final LostItemRepository lostItemRepository;

    public LostItemService(LostItemRepository lostItemRepository) {
        this.lostItemRepository = lostItemRepository;
    }

    public LostItemDTO saveLostItem(LostItemDTO lostItemDTO) {

        LostItem lostItem = LostItemMapper.toEntity(lostItemDTO);

        lostItem = lostItemRepository.save(lostItem);

        return LostItemMapper.toDTO(lostItem);
    }

    public List<LostItemDTO> getAllLostItems() {

        return lostItemRepository.findAll()
                .stream()
                .map(LostItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    public LostItemDTO getLostItemById(Long id) {

        LostItem lostItem = lostItemRepository.findById(id).orElse(null);

        return LostItemMapper.toDTO(lostItem);
    }

    public LostItemDTO updateLostItem(Long id, LostItemDTO lostItemDTO) {

        LostItem existingItem = lostItemRepository.findById(id).orElse(null);

        if (existingItem != null) {

            existingItem.setItemName(lostItemDTO.getItemName());
            existingItem.setCategory(lostItemDTO.getCategory());
            existingItem.setDescription(lostItemDTO.getDescription());
            existingItem.setLocationLost(lostItemDTO.getLocationLost());
            existingItem.setDateLost(lostItemDTO.getDateLost());
            existingItem.setImageUrl(lostItemDTO.getImageUrl());
            existingItem.setStatus(lostItemDTO.getStatus());

            if (lostItemDTO.getUserId() != null) {
                existingItem.getUser().setId(lostItemDTO.getUserId());
            }

            existingItem = lostItemRepository.save(existingItem);

            return LostItemMapper.toDTO(existingItem);
        }

        return null;
    }

    public void deleteLostItem(Long id) {
        lostItemRepository.deleteById(id);
    }

    public List<LostItemDTO> getLostItemsByCategory(String category) {

        return lostItemRepository.findByCategory(category)
                .stream()
                .map(LostItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<LostItemDTO> getLostItemsByStatus(String status) {

        return lostItemRepository.findByStatus(status)
                .stream()
                .map(LostItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<LostItemDTO> getLostItemsByLocation(String locationLost) {

        return lostItemRepository.findByLocationLost(locationLost)
                .stream()
                .map(LostItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<LostItemDTO> getLostItemsByUser(Long userId) {

        return lostItemRepository.findByUserId(userId)
                .stream()
                .map(LostItemMapper::toDTO)
                .collect(Collectors.toList());
    }
}