package com.bulk_image_processing_service.imageUpload.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(schema = "product_image_processing", name = "requests")
@Data
public class Requests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private int requestId;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "created_by", length = 55)
    private String createdBy;

    @Column(name = "created_date", columnDefinition = "DATETIME DEFAULT NOW()")
    private LocalDateTime createdDate;

    @Column(name = "updated_date", columnDefinition = "DATETIME ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedDate;

}
