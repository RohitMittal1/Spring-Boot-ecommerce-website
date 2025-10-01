package com.ecommerce.advertisement_service.service;

import com.ecommerce.advertisement_service.model.Advertisement;
import com.ecommerce.advertisement_service.repository.AdvertisementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdvertisementService {

    private final AdvertisementRepository repository;

    public AdvertisementService(AdvertisementRepository repository) {
        this.repository = repository;
    }

    public List<Advertisement> getAllAds() {
        return repository.findAll();
    }

    public Optional<Advertisement> getAdById(Long id) {
        return repository.findById(id);
    }

    public Advertisement createAd(Advertisement ad) {
        Optional<Advertisement> existingAd = repository.findByTitle(ad.getTitle());
        if (existingAd.isPresent()) {
            Advertisement adv = existingAd.get();
            adv.setDescription(ad.getDescription());
            adv.setImageUrl(ad.getImageUrl());
            adv.setActive(ad.isActive());
            return repository.save(adv);
        }
        return repository.save(ad);
    }

    public Advertisement updateAd(Long id, Advertisement ad) {
        Advertisement existingAd = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));

        existingAd.setTitle(ad.getTitle());
        existingAd.setDescription(ad.getDescription());
        existingAd.setImageUrl(ad.getImageUrl());
        existingAd.setActive(ad.isActive());

        return repository.save(existingAd);
    }

    public void deleteAd(Long id) {
        repository.deleteById(id);
    }
}