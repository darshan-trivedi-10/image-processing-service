package com.bulk_image_processing_service.imageUpload.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {
    ResponseEntity<?> processImageSheet(MultipartFile productImageSheet);
}
