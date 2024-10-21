package com.fog.e_commerce.notification;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "_notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;        // Title of the offer
    private String description;  // Offer description
    private Date startDate; // When the offer starts
    private Date endDate;   // When the offer ends
    private boolean isActive;

    public Notification() {
    }

    public Notification(String title, String description, Date startDate, Date endDate, boolean isActive) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
