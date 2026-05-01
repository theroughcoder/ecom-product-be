package be.ecom_backend.dto.response;


import lombok.Data;

import java.util.List;

@Data
public class SearchResponseDto<T> {

    private List<T> products;
    private int page;
    private int pages;
    private long countProducts;
}
