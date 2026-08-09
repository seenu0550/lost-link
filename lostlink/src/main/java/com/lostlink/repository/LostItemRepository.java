package com.lostlink.repository;

import com.lostlink.entity.LostItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LostItemRepository extends JpaRepository<LostItem, Long> {

    List<LostItem> findByCategory(String category);

    List<LostItem> findByStatus(String status);

    List<LostItem> findByLocationLost(String locationLost);

    List<LostItem> findByUserId(Long userId);

    List<LostItem> findByItemNameContainingIgnoreCase(String keyword);
}