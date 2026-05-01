package be.ecom_backend.service;

import be.ecom_backend.dto.request.SearchRequestDto;
import be.ecom_backend.dto.response.ProductResponse;
import be.ecom_backend.dto.response.SearchResponseDto;
import be.ecom_backend.model.Product;

import be.ecom_backend.repository.ProductRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class SearchService {

    ProductRepository productRepository;
    public SearchResponseDto<ProductResponse> search(SearchRequestDto searchProduct ) {

        List<Product> productList = productRepository.findAll();

        SearchResponseDto<ProductResponse> response = new SearchResponseDto<ProductResponse>();
        response.setProducts(productList.stream().map(this:: mapToProductResponse).collect(Collectors.toList()));
        response.setPage(1);
        response.setPages(10);
        response.setCountProducts(11);

        return response;
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
}
