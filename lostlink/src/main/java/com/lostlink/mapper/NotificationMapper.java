package com.lostlink.mapper;

import com.lostlink.dto.NotificationDTO;
import com.lostlink.entity.Notification;
import com.lostlink.entity.User;

public class NotificationMapper {

    public static NotificationDTO toDTO(Notification n) {
        if (n == null) return null;
        NotificationDTO dto = new NotificationDTO();
        dto.setId(n.getId());
        dto.setMessage(n.getMessage());
        dto.setRead(n.isRead());
        dto.setCreatedAt(n.getCreatedAt());
        if (n.getUser() != null) dto.setUserId(n.getUser().getId());
        return dto;
    }

    public static Notification toEntity(NotificationDTO dto) {
        if (dto == null) return null;
        Notification n = new Notification();
        n.setMessage(dto.getMessage());
        if (dto.getUserId() != null) {
            User user = new User();
            user.setId(dto.getUserId());
            n.setUser(user);
        }
        return n;
    }
}
