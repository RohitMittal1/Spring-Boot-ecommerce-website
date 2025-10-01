package com.eRohit.Ecom_proj.dto;

import lombok.Data;

@Data
public class AdvertisementDTO {
    private int id;
    private String title;
    private String description;
    private String imageUrl;
    private String redirectUrl;
    private boolean active;

    public AdvertisementDTO() {}

    public AdvertisementDTO(int id, String title, String description, String imageUrl, String redirectUrl, boolean active) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.redirectUrl = redirectUrl;
        this.active = active;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getRedirectUrl() { return redirectUrl; }
    public void setRedirectUrl(String redirectUrl) { this.redirectUrl = redirectUrl; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}