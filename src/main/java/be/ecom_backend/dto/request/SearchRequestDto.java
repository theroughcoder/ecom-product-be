package be.ecom_backend.dto.request;

import lombok.Data;

@Data
public class SearchRequestDto {

    private String query;
    private String category;
    private String price;
    private String order;
    private String rating;
    private int page = 1;
    private int size = 20;
}
