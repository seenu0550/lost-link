package com.lostlink.repository;

import com.lostlink.entity.ClaimRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRequestRepository extends JpaRepository<ClaimRequest,Long> {

    List<ClaimRequest> findByStatus(String status);
    List<ClaimRequest> findByUserId(Long userId);

}
