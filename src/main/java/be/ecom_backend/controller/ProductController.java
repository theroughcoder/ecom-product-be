package be.ecom_backend.controller;

import be.ecom_backend.dto.request.ProductRequest;
import be.ecom_backend.dto.request.ProductValidateStockRequest;
import be.ecom_backend.dto.response.ProductResponse;
import be.ecom_backend.dto.response.SearchResponseDto;
import be.ecom_backend.model.Product;
import be.ecom_backend.service.ProductService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequestMapping("/api/products")
public class ProductController {

    public ProductService prod;

    ProductController(ProductService prod){
        this.prod = prod;
    }

    @GetMapping("/categories")
    public List<String> getCategories() {
        return prod.getCategories();
    }

    @GetMapping("/admin")
    public SearchResponseDto<ProductResponse> getAdminProducts(@RequestParam(defaultValue = "1") int page) {
        return prod.getAdminProducts(page);
    }

    @GetMapping
    public List<ProductResponse> getProducts() {
        return prod.getProductsList();
    }


    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable("id") long id) {
        return prod.getProductById(id);
    }

    @PostMapping
    public void addProduct(@RequestBody ProductRequest product) {
        prod.createProduct(product);
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable("id") long id, @RequestBody ProductRequest product) {
        prod.updateProduct(id, product);
    }

    @PostMapping("/check-products-quantity")
    public String checkProductsQuantity(@RequestBody ProductValidateStockRequest request) {
       return prod.validateProductsCount(request);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") long id) {
        prod.deleteProduct(id);
    }

}
