package com.bulk_image_processing_service.imageUpload.repository;

import com.bulk_image_processing_service.imageUpload.entity.RequestProductMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestProductMappingRepository extends JpaRepository<RequestProductMapping, Integer> {
}
