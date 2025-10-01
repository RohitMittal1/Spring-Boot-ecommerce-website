package com.eRohit.Ecom_proj.controller;

import com.eRohit.Ecom_proj.client.AdvertisementClient;
import com.eRohit.Ecom_proj.dto.AdvertisementDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    private final AdvertisementClient advertisementClient;

    public HomeController(AdvertisementClient advertisementClient) {
        this.advertisementClient = advertisementClient;
    }

    @GetMapping("/api/ads")
    public List<AdvertisementDTO> getAds() {
        return advertisementClient.getAllAds();
    }
}