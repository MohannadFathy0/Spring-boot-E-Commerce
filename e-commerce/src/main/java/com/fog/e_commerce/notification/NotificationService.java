package com.fog.e_commerce.notification;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository repository;
    private final NotificationController notificationController;

    public NotificationService(NotificationRepository repository, NotificationController notificationController) {
        this.repository = repository;
        this.notificationController = notificationController;
    }

    public Notification createOfferNotification(String title, String description, Date startDate, Date endDate) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setDescription(description);
        notification.setStartDate(startDate);
        notification.setEndDate(endDate);
        notification.setActive(true); // Set as active when created
        return repository.save(notification);
    }

    public List<Notification> getActiveOffers() {
        return repository.findByIsActiveTrue();
    }

    public void deactivateOffer(Long id) {
        Notification offer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offer not found"));
        offer.setActive(false);
        repository.save(offer);
    }

    public Notification createNewOffer(String title, String description, Date startDate, Date endDate) {
        Notification offerNotification = createOfferNotification(title, description, startDate, endDate);

        // Send the offer notification via WebSocket
//        notificationController.sendOfferNotification(offerNotification);

        return offerNotification;
    }
}
