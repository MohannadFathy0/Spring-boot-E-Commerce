package com.fog.e_commerce.notification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository  extends JpaRepository<Notification, Long> {
    List<Notification> findByIsActiveTrue();
}
