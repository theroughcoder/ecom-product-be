package be.ecom_backend.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FileUploadResponse {
    private String filePath;
    private LocalDateTime dateTime;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
