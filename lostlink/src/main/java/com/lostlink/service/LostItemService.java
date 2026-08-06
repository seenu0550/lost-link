package com.lostlink.service;

import com.lostlink.entity.LostItem;
import com.lostlink.repository.LostItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LostItemService {

    private final LostItemRepository lostItemRepository;

    public LostItemService(LostItemRepository lostItemRepository) {
        this.lostItemRepository = lostItemRepository;
    }

    public LostItem saveLostItem(LostItem lostItem) {
        return lostItemRepository.save(lostItem);
    }


    public List<LostItem> getAllLostItems() {
        return lostItemRepository.findAll();
    }
    public LostItem getLostItemById(Long id) {
        return lostItemRepository.findById(id).orElse(null);
    }

    public LostItem updateLostItem(Long id, LostItem updatedLostItem) {
        LostItem existingItem = lostItemRepository.findById(id).orElse(null);

        if (existingItem != null) {
            existingItem.setItemName(updatedLostItem.getItemName());
            existingItem.setCategory(updatedLostItem.getCategory());
            existingItem.setDescription(updatedLostItem.getDescription());
            existingItem.setLocationLost(updatedLostItem.getLocationLost());
            existingItem.setDateLost(updatedLostItem.getDateLost());
            existingItem.setImageUrl(updatedLostItem.getImageUrl());
            existingItem.setStatus(updatedLostItem.getStatus());
            existingItem.setUser(updatedLostItem.getUser());

            return lostItemRepository.save(existingItem);
        }

        return null;
    }
    public void deleteLostItem(Long id) {
        lostItemRepository.deleteById(id);
    }

    public List<LostItem> searchByCategory(String category) {

        return lostItemRepository.findByCategory(category);
    }

    public List<LostItem> searchByStatus(String status) {

        return lostItemRepository.findByStatus(status);
    }
    public List<LostItem> searchByLocation(String locationLost) {

        return lostItemRepository.findByLocationLost(locationLost);
    }

    public List<LostItem> getLostItemsByUser(Long userId) {

        return lostItemRepository.findByUserId(userId);
    }
}