package be.ecom_backend.dto.request;

import lombok.Data;

@Data
public class ProductRequest {
    private String slug;
    private String image;
    private String name;
    private double rating;
    private int numReviews;
    private double price;
    private int countInStock;
    private String category;
    private String description;
}
