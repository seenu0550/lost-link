package com.lostlink.controller;

import com.lostlink.dto.FoundItemDTO;
import com.lostlink.service.FoundItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/found-items")
public class FoundItemController {

    private final FoundItemService foundItemService;

    public FoundItemController(FoundItemService foundItemService) {
        this.foundItemService = foundItemService;
    }

    @PostMapping
    public ResponseEntity<FoundItemDTO> createFoundItem(@Valid @RequestBody FoundItemDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(foundItemService.saveFoundItem(dto));
    }

    @GetMapping
    public ResponseEntity<List<FoundItemDTO>> getAllFoundItems() {
        return ResponseEntity.ok(foundItemService.getAllFoundItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoundItemDTO> getFoundItemById(@PathVariable Long id) {
        return ResponseEntity.ok(foundItemService.getFoundItemById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoundItemDTO> updateFoundItem(@PathVariable Long id,
                                                         @Valid @RequestBody FoundItemDTO dto) {
        return ResponseEntity.ok(foundItemService.updateFoundItem(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoundItem(@PathVariable Long id) {
        foundItemService.deleteFoundItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<FoundItemDTO>> getFoundItemsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(foundItemService.getFoundItemsByCategory(category));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<FoundItemDTO>> getFoundItemsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(foundItemService.getFoundItemsByStatus(status));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FoundItemDTO>> getFoundItemsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(foundItemService.getFoundItemsByUser(userId));
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<FoundItemDTO>> getFoundItemsByLocation(@PathVariable String location) {
        return ResponseEntity.ok(foundItemService.getFoundItemsByLocation(location));
    }

    @GetMapping("/search")
    public ResponseEntity<List<FoundItemDTO>> searchFoundItems(@RequestParam String keyword) {
        return ResponseEntity.ok(foundItemService.searchFoundItems(keyword));
    }
}
