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

    public FoundItemService(FoundItemRepository foundItemRepository,
                            UserRepository userRepository) {
        this.foundItemRepository = foundItemRepository;
        this.userRepository = userRepository;
    }


    public FoundItemDTO saveFoundItem(FoundItemDTO foundItemDTO) {

        FoundItem foundItem = FoundItemMapper.toEntity(foundItemDTO);

        FoundItem savedItem = foundItemRepository.save(foundItem);

        return FoundItemMapper.toDTO(savedItem);
    }


    public List<FoundItemDTO> getAllFoundItems() {

        return foundItemRepository.findAll()
                .stream()
                .map(FoundItemMapper::toDTO)
                .collect(Collectors.toList());
    }


    public FoundItemDTO getFoundItemById(Long id) {

        return foundItemRepository.findById(id)
                .map(FoundItemMapper::toDTO)
                .orElse(null);
    }

    public FoundItemDTO updateFoundItem(Long id, FoundItemDTO updatedDTO) {

        FoundItem existingItem =
                foundItemRepository.findById(id)
                        .orElse(null);


        if(existingItem != null) {

            existingItem.setItemName(updatedDTO.getItemName());
            existingItem.setCategory(updatedDTO.getCategory());
            existingItem.setDescription(updatedDTO.getDescription());
            existingItem.setLocationFound(updatedDTO.getLocationFound());
            existingItem.setDateFound(updatedDTO.getDateFound());
            existingItem.setImageUrl(updatedDTO.getImageUrl());
            existingItem.setStatus(updatedDTO.getStatus());


            if (updatedDTO.getUserId() != null) {

                User user = userRepository.findById(updatedDTO.getUserId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException("User not found with id: " + updatedDTO.getUserId()));

                existingItem.setUser(user);
            }


            FoundItem updatedItem =
                    foundItemRepository.save(existingItem);


            return FoundItemMapper.toDTO(updatedItem);
        }


        return null;
    }

    public void deleteFoundItem(Long id) {

        foundItemRepository.deleteById(id);
    }

}