package com.ecommerce.advertisement_service.controller;

import com.ecommerce.advertisement_service.model.Advertisement;
import com.ecommerce.advertisement_service.service.AdvertisementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ads")
@CrossOrigin(origins = "http://localhost:5173")
public class AdvertisementController {

    private final AdvertisementService service;

    public AdvertisementController(AdvertisementService service) {
        this.service = service;
    }

    @GetMapping
    public List<Advertisement> getAllAds() {
        return service.getAllAds();
    }

    @GetMapping("/{id}")
    public Advertisement getAdById(@PathVariable Long id) {
        return service.getAdById(id)
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));
    }

    @PostMapping
    public Advertisement createAd(@RequestBody Advertisement ad) {
        return service.createAd(ad);
    }

    @PutMapping("/{id}")
    public Advertisement updateAd(@PathVariable Long id, @RequestBody Advertisement ad) {
        return service.updateAd(id, ad);
    }

    @DeleteMapping("/{id}")
    public boolean deleteAd(@PathVariable Long id) {
        service.deleteAd(id);
        return true;
    }
}
