package com.lostlink.controller;

import com.lostlink.entity.LostItem;
import com.lostlink.service.LostItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lost-items")
public class LostItemController {

    private final LostItemService lostItemService;

    public LostItemController(LostItemService lostItemService) {
        this.lostItemService = lostItemService;
    }


    @PostMapping
    public LostItem createLostItem(@RequestBody LostItem lostItem) {
        return lostItemService.saveLostItem(lostItem);
    }

    @GetMapping
    public List<LostItem> getAllLostItems() {
        return lostItemService.getAllLostItems();
    }


    @GetMapping("/{id}")
    public LostItem getLostItemById(@PathVariable Long id) {
        return lostItemService.getLostItemById(id);
    }

    @PutMapping("/{id}")
    public LostItem updateLostItem(@PathVariable Long id,
                                   @RequestBody LostItem lostItem) {
        return lostItemService.updateLostItem(id, lostItem);
    }

    @DeleteMapping("/{id}")
    public void deleteLostItem(@PathVariable Long id) {
        lostItemService.deleteLostItem(id);
    }


    @GetMapping("/search/category/{category}")
    public List<LostItem> searchByCategory(
            @PathVariable String category){

        return lostItemService.searchByCategory(category);
    }



    @GetMapping("/search/status/{status}")
    public List<LostItem> searchByStatus(
            @PathVariable String status){

        return lostItemService.searchByStatus(status);
    }



    @GetMapping("/search/location/{location}")
    public List<LostItem> searchByLocation(
            @PathVariable String location){

        return lostItemService.searchByLocation(location);
    }



    @GetMapping("/user/{userId}")
    public List<LostItem> getLostItemsByUser(
            @PathVariable Long userId){

        return lostItemService.getLostItemsByUser(userId);
    }
}