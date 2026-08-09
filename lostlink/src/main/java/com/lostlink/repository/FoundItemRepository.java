package com.lostlink.repository;

import com.lostlink.entity.FoundItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoundItemRepository extends JpaRepository<FoundItem, Long> {

    List<FoundItem> findByCategory(String category);

    List<FoundItem> findByStatus(String status);

    List<FoundItem> findByUserId(Long userId);

    List<FoundItem> findByLocationFound(String locationFound);

    List<FoundItem> findByItemNameContainingIgnoreCase(String keyword);
}