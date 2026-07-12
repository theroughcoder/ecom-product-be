package be.ecom_backend.controller;

import be.ecom_backend.dto.response.FileUploadResponse;
import be.ecom_backend.service.FileServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequestMapping("/api/v1/s3")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileServiceImp fileService;

    @PostMapping("/upload-image")
    public ResponseEntity<FileUploadResponse> uploadFile(MultipartFile file, @RequestParam(required = false) String category) {
        return new ResponseEntity<>(fileService.uploadFile(file, category), HttpStatus.OK);
    }
}

