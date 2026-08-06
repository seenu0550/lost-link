package com.lostlink.service;

import com.lostlink.entity.FoundItem;
import com.lostlink.repository.FoundItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoundItemService {


    private final FoundItemRepository foundItemRepository;


    public FoundItemService(FoundItemRepository foundItemRepository) {
        this.foundItemRepository = foundItemRepository;
    }

    public FoundItem saveFoundItem(FoundItem foundItem) {

        return foundItemRepository.save(foundItem);
    }

    public List<FoundItem> getAllFoundItems() {

        return foundItemRepository.findAll();
    }


    public FoundItem getFoundItemById(Long id) {

        return foundItemRepository.findById(id)
                .orElse(null);
    }

    public FoundItem updateFoundItem(Long id, FoundItem updatedItem) {

        FoundItem existingItem =
                foundItemRepository.findById(id)
                        .orElse(null);


        if(existingItem != null) {

            existingItem.setItemName(updatedItem.getItemName());
            existingItem.setCategory(updatedItem.getCategory());
            existingItem.setDescription(updatedItem.getDescription());
            existingItem.setLocationFound(updatedItem.getLocationFound());
            existingItem.setDateFound(updatedItem.getDateFound());
            existingItem.setImageUrl(updatedItem.getImageUrl());
            existingItem.setStatus(updatedItem.getStatus());
            existingItem.setUser(updatedItem.getUser());


            return foundItemRepository.save(existingItem);
        }


        return null;
    }

    public void deleteFoundItem(Long id) {

        foundItemRepository.deleteById(id);
    }

}