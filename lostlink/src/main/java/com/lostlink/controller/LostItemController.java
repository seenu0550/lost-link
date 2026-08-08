package com.lostlink.controller;

import com.lostlink.dto.LostItemDTO;
import com.lostlink.service.LostItemService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lost-items")
public class LostItemController {

    private final LostItemService lostItemService;

    public LostItemController(LostItemService lostItemService) {
        this.lostItemService = lostItemService;
    }

    @PostMapping
    public LostItemDTO createLostItem(@Valid @RequestBody LostItemDTO lostItemDTO) {
        return lostItemService.saveLostItem(lostItemDTO);
    }

    @GetMapping
    public List<LostItemDTO> getAllLostItems() {
        return lostItemService.getAllLostItems();
    }

    @GetMapping("/{id}")
    public LostItemDTO getLostItemById(@PathVariable Long id) {
        return lostItemService.getLostItemById(id);
    }

    @PutMapping("/{id}")
    public LostItemDTO updateLostItem(@PathVariable Long id,
                                      @Valid
                                      @RequestBody LostItemDTO lostItemDTO) {
        return lostItemService.updateLostItem(id, lostItemDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteLostItem(@PathVariable Long id) {
        lostItemService.deleteLostItem(id);
    }

    @GetMapping("/category/{category}")
    public List<LostItemDTO> getLostItemsByCategory(@PathVariable String category) {
        return lostItemService.getLostItemsByCategory(category);
    }

    @GetMapping("/status/{status}")
    public List<LostItemDTO> getLostItemsByStatus(@PathVariable String status) {
        return lostItemService.getLostItemsByStatus(status);
    }

    @GetMapping("/location/{location}")
    public List<LostItemDTO> getLostItemsByLocation(@PathVariable String location) {
        return lostItemService.getLostItemsByLocation(location);
    }

    @GetMapping("/user/{userId}")
    public List<LostItemDTO> getLostItemsByUser(@PathVariable Long userId) {
        return lostItemService.getLostItemsByUser(userId);
    }
}