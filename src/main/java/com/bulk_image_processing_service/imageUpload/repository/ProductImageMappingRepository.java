package com.bulk_image_processing_service.imageUpload.repository;

import com.bulk_image_processing_service.imageUpload.entity.ProductImageMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageMappingRepository extends JpaRepository<ProductImageMapping, Integer> {
}
