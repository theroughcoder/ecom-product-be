package be.ecom_backend.service;

import be.ecom_backend.dto.request.ProductRequest;
import be.ecom_backend.dto.response.ProductResponse;
import be.ecom_backend.model.Product;
import be.ecom_backend.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
public class ProductService {

    public ProductRepository productRepository;

    public List<ProductResponse> getProductsList() {
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public void createProduct (ProductRequest productRequest) {
        Product product = new Product();
        updateToProductRequest(product, productRequest);
        productRepository.save(product);
    }

    public ProductResponse mapToProductResponse (Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setImage(product.getImage());
        response.setSlug(product.getSlug());
        response.setRating(product.getRating());
        response.setNumReviews(product.getNumReviews());
        response.setCountInStock(product.getCountInStock());
        return response;
    }
    private void updateToProductRequest(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setImage(productRequest.getImage());
        product.setSlug(productRequest.getSlug());
        product.setRating(productRequest.getRating());
        product.setNumReviews(productRequest.getNumReviews());
        product.setCountInStock(productRequest.getCountInStock());
    }

}
