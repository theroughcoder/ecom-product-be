package be.ecom_backend.controller;

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

    @GetMapping
    public List<Product> getProducts() {
        return prod.getProductsList();
    }

    @PostMapping
    public void addProduct(@RequestBody Product product) {
        prod.createProduct(product);
    }

}
