package be.ecom_backend.service;

import be.ecom_backend.dto.request.ProductRequest;
import be.ecom_backend.dto.request.ProductValidateStockRequest;
import be.ecom_backend.dto.response.ProductResponse;
import be.ecom_backend.dto.response.SearchResponseDto;
import be.ecom_backend.model.OrderItem;
import be.ecom_backend.model.Product;
import be.ecom_backend.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
public class ProductService {

    private static final int ADMIN_PAGE_SIZE = 10;

    public ProductRepository productRepository;

    public List<ProductResponse> getProductsList() {
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public List<String> getCategories() {
        return productRepository.findDistinctCategories();
    }

    public SearchResponseDto<ProductResponse> getAdminProducts(int page) {
        PageRequest pageable = PageRequest.of(page - 1, ADMIN_PAGE_SIZE, Sort.by(Sort.Direction.DESC, "id"));
        Page<Product> productPage = productRepository.findAll(pageable);

        SearchResponseDto<ProductResponse> response = new SearchResponseDto<>();
        response.setProducts(productPage.getContent().stream().map(this::mapToProductResponse).collect(Collectors.toList()));
        response.setPage(page);
        response.setPages(productPage.getTotalPages());
        response.setCountProducts((int) productPage.getTotalElements());
        return response;
    }
    public ProductResponse getProductById(Long id){
        return productRepository.findById(id).map(this::mapToProductResponse)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id " + id));
    }

    public String validateProductsCount(ProductValidateStockRequest request) {

        for (OrderItem item : request.getOrderItems()) {

            Product product = productRepository.findById(item.getId())
                    .orElseThrow(() ->
                            new RuntimeException("Product not found: " + item.getId()));

            if (product.getCountInStock() < item.getQuantity()) {
                return "Insufficient stock for product : " + item.getName()
                        + ". Available: " + product.getCountInStock()
                        + ", Requested: " + item.getQuantity();
            }
        }

        return "Stock validation successful";
    }

    public void createProduct (ProductRequest productRequest) {
        Product product = new Product();
        updateToProductRequest(product, productRequest);
        productRepository.save(product);
    }

    public void updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id " + id));
        updateToProductRequest(product, productRequest);
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id " + id);
        }
        productRepository.deleteById(id);
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
        response.setCategory(product.getCategory());
        response.setDescription(product.getDescription());
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
        product.setCategory(productRequest.getCategory());
        product.setDescription(productRequest.getDescription());
    }

}
