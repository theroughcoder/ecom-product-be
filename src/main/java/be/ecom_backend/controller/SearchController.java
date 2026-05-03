package be.ecom_backend.controller;

import be.ecom_backend.dto.request.SearchRequestDto;
import be.ecom_backend.dto.response.ProductResponse;
import be.ecom_backend.dto.response.SearchResponseDto;
import be.ecom_backend.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<SearchResponseDto<ProductResponse>> search(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String price,
            @RequestParam(required = false) String order,
            @RequestParam(required = false) String rating,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        SearchRequestDto request = new SearchRequestDto();
        request.setQuery(query);
        request.setCategory(category);
        request.setPrice(price);
        request.setOrder(order);
        request.setRating(rating);
        request.setPage(page);
        request.setSize(size);

        return ResponseEntity.ok(searchService.search(request));
    }
}
