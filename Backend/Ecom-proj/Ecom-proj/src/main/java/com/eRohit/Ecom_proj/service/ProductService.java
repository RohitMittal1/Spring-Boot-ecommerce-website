package com.eRohit.Ecom_proj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eRohit.Ecom_proj.model.Product;
import com.eRohit.Ecom_proj.repo.ProductRepo;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;

    public Product saveProduct(Product product, MultipartFile imageFile) throws IOException {
        
        Optional<Product> existing = repo.findByNameIgnoreCase(product.getName());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Product with name '" + product.getName() + "' already exists.");
        }

        if (product.getBrand() != null && repo.findByNameAndBrand(product.getName(), product.getBrand()).isPresent()) {
            throw new IllegalArgumentException("Product '" + product.getName() + "' already exists under brand '" + product.getBrand() + "'");
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            product.setImageName(imageFile.getOriginalFilename());
            product.setImageType(imageFile.getContentType());
            product.setImageData(imageFile.getBytes());
        }
        return repo.save(product);
    }

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product getProductById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Product updateProduct(int id, Product updatedProduct, MultipartFile imageFile) throws IOException {
        Product product = getProductById(id);
        if (product == null) {
            return null;
        }

        Optional<Product> duplicate = repo.findByNameIgnoreCase(updatedProduct.getName());
        if (duplicate.isPresent() && duplicate.get().getId() != id) {
            throw new IllegalArgumentException("Another product with name '" + updatedProduct.getName() + "' already exists.");
        }

        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setBrand(updatedProduct.getBrand());
        product.setPrice(updatedProduct.getPrice());
        product.setCategory(updatedProduct.getCategory());
        product.setReleaseDate(updatedProduct.getReleaseDate());
        product.setProductAvailable(updatedProduct.isProductAvailable());
        product.setStockQuantity(updatedProduct.getStockQuantity());

        if (imageFile != null && !imageFile.isEmpty()) {
            product.setImageName(imageFile.getOriginalFilename());
            product.setImageType(imageFile.getContentType());
            product.setImageData(imageFile.getBytes());
        }
        return repo.save(product);
    }

    public void deleteProduct(int id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Product with ID " + id + " does not exist.");
        }
        repo.deleteById(id);
    }

    public List<Product> searchProducts(String keyword) {
        return repo.searchProducts(keyword);
    }
}
