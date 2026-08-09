package com.lostlink.service;

import com.lostlink.dto.FoundItemDTO;
import com.lostlink.entity.FoundItem;
import com.lostlink.entity.User;
import com.lostlink.exception.ResourceNotFoundException;
import com.lostlink.mapper.FoundItemMapper;
import com.lostlink.repository.FoundItemRepository;
import com.lostlink.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoundItemService {

    private final FoundItemRepository foundItemRepository;
    private final UserRepository userRepository;

    public FoundItemService(FoundItemRepository foundItemRepository, UserRepository userRepository) {
        this.foundItemRepository = foundItemRepository;
        this.userRepository = userRepository;
    }

    public FoundItemDTO saveFoundItem(FoundItemDTO dto) {
        if (dto.getUserId() != null) {
            userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getUserId()));
        }
        FoundItem item = FoundItemMapper.toEntity(dto);
        if (item.getStatus() == null || item.getStatus().isBlank()) {
            item.setStatus("AVAILABLE");
        }
        return FoundItemMapper.toDTO(foundItemRepository.save(item));
    }

    public List<FoundItemDTO> getAllFoundItems() {
        return foundItemRepository.findAll().stream()
                .map(FoundItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    public FoundItemDTO getFoundItemById(Long id) {
        return foundItemRepository.findById(id)
                .map(FoundItemMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Found item not found with id: " + id));
    }

    public FoundItemDTO updateFoundItem(Long id, FoundItemDTO dto) {
        FoundItem existing = foundItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Found item not found with id: " + id));

        existing.setItemName(dto.getItemName());
        existing.setCategory(dto.getCategory());
        existing.setDescription(dto.getDescription());
        existing.setLocationFound(dto.getLocationFound());
        existing.setDateFound(dto.getDateFound());
        existing.setImageUrl(dto.getImageUrl());
        existing.setStatus(dto.getStatus());

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getUserId()));
            existing.setUser(user);
        }

        return FoundItemMapper.toDTO(foundItemRepository.save(existing));
    }

    public void deleteFoundItem(Long id) {
        if (!foundItemRepository.existsById(id))
            throw new ResourceNotFoundException("Found item not found with id: " + id);
        foundItemRepository.deleteById(id);
    }

    public List<FoundItemDTO> getFoundItemsByCategory(String category) {
        return foundItemRepository.findByCategory(category).stream()
                .map(FoundItemMapper::toDTO).collect(Collectors.toList());
    }

    public List<FoundItemDTO> getFoundItemsByStatus(String status) {
        return foundItemRepository.findByStatus(status).stream()
                .map(FoundItemMapper::toDTO).collect(Collectors.toList());
    }

    public List<FoundItemDTO> getFoundItemsByUser(Long userId) {
        if (!userRepository.existsById(userId))
            throw new ResourceNotFoundException("User not found with id: " + userId);
        return foundItemRepository.findByUserId(userId).stream()
                .map(FoundItemMapper::toDTO).collect(Collectors.toList());
    }

    public List<FoundItemDTO> getFoundItemsByLocation(String locationFound) {
        return foundItemRepository.findByLocationFound(locationFound).stream()
                .map(FoundItemMapper::toDTO).collect(Collectors.toList());
    }

    public List<FoundItemDTO> searchFoundItems(String keyword) {
        return foundItemRepository.findByItemNameContainingIgnoreCase(keyword).stream()
                .map(FoundItemMapper::toDTO).collect(Collectors.toList());
    }
}
