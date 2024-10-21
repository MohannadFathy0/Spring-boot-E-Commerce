package com.fog.e_commerce.notification;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/massage")
    @SendTo("/topic/notifications")
    // Send offer notification to clients
    public ResponseEntity<String> sendOfferNotification(Notification offer) {
        messagingTemplate.convertAndSend("/topic/offers", offer);
        return ResponseEntity.ok("Notification sent successfully!");
    }
}
