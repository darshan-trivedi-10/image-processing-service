package com.bulk_image_processing_service.imageUpload.controller;

import com.bulk_image_processing_service.imageUpload.service.ImageUploadService;
import com.bulk_image_processing_service.imageUpload.service.ImageUploadServiceImpl;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
public class ImageUploadController {

    private ImageUploadService imageUploadService;

    @Autowired
    public ImageUploadController(ImageUploadServiceImpl imageUploadService){
        this.imageUploadService = imageUploadService;
    }

    @PostMapping("/upload-image-sheet")
    ResponseEntity<?> uploadImageSheet(@RequestParam("productImageSheet") MultipartFile productImageSheet) {
        return imageUploadService.processImageSheet(productImageSheet);
    }


}

