package com.ecommerce.advertisement_service.repository;

import com.ecommerce.advertisement_service.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    Optional<Advertisement> findByTitle(String title); 
}