package com.lostlink.service;

import com.lostlink.dto.LostItemDTO;
import com.lostlink.entity.LostItem;
import com.lostlink.entity.User;
import com.lostlink.exception.ResourceNotFoundException;
import com.lostlink.mapper.LostItemMapper;
import com.lostlink.repository.LostItemRepository;
import com.lostlink.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LostItemService {

    private final LostItemRepository lostItemRepository;
    private final UserRepository userRepository;

    public LostItemService(LostItemRepository lostItemRepository, UserRepository userRepository) {
        this.lostItemRepository = lostItemRepository;
        this.userRepository = userRepository;
    }

    public LostItemDTO saveLostItem(LostItemDTO dto) {
        if (dto.getUserId() != null) {
            userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getUserId()));
        }
        LostItem item = LostItemMapper.toEntity(dto);
        if (item.getStatus() == null || item.getStatus().isBlank()) {
            item.setStatus("LOST");
        }
        return LostItemMapper.toDTO(lostItemRepository.save(item));
    }

    public List<LostItemDTO> getAllLostItems() {
        return lostItemRepository.findAll().stream()
                .map(LostItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    public LostItemDTO getLostItemById(Long id) {
        return lostItemRepository.findById(id)
                .map(LostItemMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Lost item not found with id: " + id));
    }

    public LostItemDTO updateLostItem(Long id, LostItemDTO dto) {
        LostItem existing = lostItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lost item not found with id: " + id));

        existing.setItemName(dto.getItemName());
        existing.setCategory(dto.getCategory());
        existing.setDescription(dto.getDescription());
        existing.setLocationLost(dto.getLocationLost());
        existing.setDateLost(dto.getDateLost());
        existing.setImageUrl(dto.getImageUrl());
        existing.setStatus(dto.getStatus());

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getUserId()));
            existing.setUser(user);
        }

        return LostItemMapper.toDTO(lostItemRepository.save(existing));
    }

    public void deleteLostItem(Long id) {
        if (!lostItemRepository.existsById(id))
            throw new ResourceNotFoundException("Lost item not found with id: " + id);
        lostItemRepository.deleteById(id);
    }

    public List<LostItemDTO> getLostItemsByCategory(String category) {
        return lostItemRepository.findByCategory(category).stream()
                .map(LostItemMapper::toDTO).collect(Collectors.toList());
    }

    public List<LostItemDTO> getLostItemsByStatus(String status) {
        return lostItemRepository.findByStatus(status).stream()
                .map(LostItemMapper::toDTO).collect(Collectors.toList());
    }

    public List<LostItemDTO> getLostItemsByLocation(String locationLost) {
        return lostItemRepository.findByLocationLost(locationLost).stream()
                .map(LostItemMapper::toDTO).collect(Collectors.toList());
    }

    public List<LostItemDTO> getLostItemsByUser(Long userId) {
        if (!userRepository.existsById(userId))
            throw new ResourceNotFoundException("User not found with id: " + userId);
        return lostItemRepository.findByUserId(userId).stream()
                .map(LostItemMapper::toDTO).collect(Collectors.toList());
    }

    public List<LostItemDTO> searchLostItems(String keyword) {
        return lostItemRepository.findByItemNameContainingIgnoreCase(keyword).stream()
                .map(LostItemMapper::toDTO).collect(Collectors.toList());
    }
}
