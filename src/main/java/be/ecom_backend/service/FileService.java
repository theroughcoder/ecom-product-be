package be.ecom_backend.service;

import be.ecom_backend.dto.response.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileUploadResponse uploadFile(MultipartFile multipartFile, String category);
}
