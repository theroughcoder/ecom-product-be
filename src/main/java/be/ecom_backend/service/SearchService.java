package be.ecom_backend.service;

import be.ecom_backend.dto.request.SearchRequestDto;
import be.ecom_backend.dto.response.ProductResponse;
import be.ecom_backend.dto.response.SearchResponseDto;
import be.ecom_backend.model.Product;

import be.ecom_backend.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
public class SearchService {

    public ProductRepository productRepository;

    public SearchResponseDto<ProductResponse> search(SearchRequestDto searchProduct) {

        double[] priceRange = parsePriceRange(searchProduct.getPrice());
        double minRating = parseMinRating(searchProduct.getRating());
        Sort sort = parseOrder(searchProduct.getOrder());
        PageRequest pageable = PageRequest.of(searchProduct.getPage() - 1, searchProduct.getSize(), sort);

        Page<Product> productPage = productRepository.searchByFilter(
                searchProduct.getQuery(),
                searchProduct.getCategory(),
                priceRange[0],
                priceRange[1],
                minRating,
                pageable
        );

        SearchResponseDto<ProductResponse> response = new SearchResponseDto<ProductResponse>();
        response.setProducts(productPage.getContent().stream().map(this::mapToProductResponse).collect(Collectors.toList()));
        response.setPage(searchProduct.getPage());
        response.setPages(productPage.getTotalPages());
        response.setCountProducts((int) productPage.getTotalElements());

        return response;
    }

    private double[] parsePriceRange(String price) {
        if (price == null || price.equals("all")) {
            return new double[]{0, Double.MAX_VALUE};
        }
        String[] parts = price.split("-");
        return new double[]{Double.parseDouble(parts[0]), Double.parseDouble(parts[1])};
    }

    private double parseMinRating(String rating) {
        if (rating == null || rating.equals("all")) {
            return 0;
        }
        return Double.parseDouble(rating);
    }

    private Sort parseOrder(String order) {
        if (order == null) return Sort.by(Sort.Direction.DESC, "id");
        return switch (order) {
            case "lowest"   -> Sort.by(Sort.Direction.ASC,  "price");
            case "highest"  -> Sort.by(Sort.Direction.DESC, "price");
            case "toprated" -> Sort.by(Sort.Direction.DESC, "rating");
            default         -> Sort.by(Sort.Direction.DESC, "id"); // featured, newest, or unknown
        };
    }

    public ProductResponse mapToProductResponse(Product product) {
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
