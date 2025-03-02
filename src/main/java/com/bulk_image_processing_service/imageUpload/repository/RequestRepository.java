package com.bulk_image_processing_service.imageUpload.repository;

import com.bulk_image_processing_service.imageUpload.entity.Requests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Requests, Integer> {
}
