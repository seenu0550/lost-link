package com.lostlink.controller;

import com.lostlink.dto.LostItemDTO;
import com.lostlink.service.LostItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<LostItemDTO> createLostItem(@Valid @RequestBody LostItemDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lostItemService.saveLostItem(dto));
    }

    @GetMapping
    public ResponseEntity<List<LostItemDTO>> getAllLostItems() {
        return ResponseEntity.ok(lostItemService.getAllLostItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LostItemDTO> getLostItemById(@PathVariable Long id) {
        return ResponseEntity.ok(lostItemService.getLostItemById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LostItemDTO> updateLostItem(@PathVariable Long id,
                                                       @Valid @RequestBody LostItemDTO dto) {
        return ResponseEntity.ok(lostItemService.updateLostItem(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLostItem(@PathVariable Long id) {
        lostItemService.deleteLostItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<LostItemDTO>> getLostItemsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(lostItemService.getLostItemsByCategory(category));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<LostItemDTO>> getLostItemsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(lostItemService.getLostItemsByStatus(status));
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<LostItemDTO>> getLostItemsByLocation(@PathVariable String location) {
        return ResponseEntity.ok(lostItemService.getLostItemsByLocation(location));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LostItemDTO>> getLostItemsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(lostItemService.getLostItemsByUser(userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<LostItemDTO>> searchLostItems(@RequestParam String keyword) {
        return ResponseEntity.ok(lostItemService.searchLostItems(keyword));
    }
}
