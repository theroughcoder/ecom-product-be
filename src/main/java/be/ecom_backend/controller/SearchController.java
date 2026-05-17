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
    public ResponseEntity<SearchResponseDto<ProductResponse>> search(SearchRequestDto request) {
        return ResponseEntity.ok(searchService.search(request));
    }
}
