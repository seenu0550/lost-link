package com.lostlink.controller;

import com.lostlink.dto.FoundItemDTO;
import com.lostlink.service.FoundItemService;
import jakarta.validation.Valid;
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
    public FoundItemDTO createFoundItem(
          @Valid @RequestBody FoundItemDTO foundItemDTO) {

        return foundItemService.saveFoundItem(foundItemDTO);
    }


    @GetMapping
    public List<FoundItemDTO> getAllFoundItems() {

        return foundItemService.getAllFoundItems();
    }


    @GetMapping("/{id}")
    public FoundItemDTO getFoundItemById(
            @PathVariable Long id) {

        return foundItemService.getFoundItemById(id);
    }


    @PutMapping("/{id}")
    public FoundItemDTO updateFoundItem(
            @PathVariable Long id,
            @Valid
            @RequestBody FoundItemDTO foundItemDTO) {

        return foundItemService.updateFoundItem(id, foundItemDTO);
    }


    @DeleteMapping("/{id}")
    public String deleteFoundItem(
            @PathVariable Long id) {

        foundItemService.deleteFoundItem(id);

        return "Found Item deleted successfully";
    }

}