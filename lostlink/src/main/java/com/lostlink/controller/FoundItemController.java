package com.lostlink.controller;

import com.lostlink.entity.FoundItem;
import com.lostlink.service.FoundItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/found-items")
@CrossOrigin
public class FoundItemController {


    private final FoundItemService foundItemService;


    public FoundItemController(FoundItemService foundItemService) {
        this.foundItemService = foundItemService;
    }


    @PostMapping
    public FoundItem createFoundItem(
            @RequestBody FoundItem foundItem) {

        return foundItemService.saveFoundItem(foundItem);
    }


    @GetMapping
    public List<FoundItem> getAllFoundItems() {

        return foundItemService.getAllFoundItems();
    }



    @GetMapping("/{id}")
    public FoundItem getFoundItemById(
            @PathVariable Long id) {

        return foundItemService.getFoundItemById(id);
    }


    @PutMapping("/{id}")
    public FoundItem updateFoundItem(
            @PathVariable Long id,
            @RequestBody FoundItem foundItem) {

        return foundItemService.updateFoundItem(id, foundItem);
    }


    @DeleteMapping("/{id}")
    public String deleteFoundItem(
            @PathVariable Long id) {

        foundItemService.deleteFoundItem(id);

        return "Found Item deleted successfully";
    }

}