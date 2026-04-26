package be.ecom_backend.service;

import be.ecom_backend.model.Product;
import be.ecom_backend.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@AllArgsConstructor
public class ProductService {

    public ProductRepository productRepository;

    public List<Product> getProductsList() {
        return productRepository.findAll();
    }

    public void createProduct (Product product) {
        productRepository.save(product);
    }

}
