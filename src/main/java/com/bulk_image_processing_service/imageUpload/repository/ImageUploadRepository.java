package com.bulk_image_processing_service.imageUpload.repository;

import com.bulk_image_processing_service.imageUpload.dto.ProductImageSheetDto;

import java.util.List;

public interface ImageUploadRepository {
    int createImageProcessRequest(List<ProductImageSheetDto> productImageData);
}
