package com.bulk_image_processing_service.imageUpload.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(schema = "product_image_processing", name = "request_product_mapping")
@Data
public class RequestProductMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "product_name", nullable = false, length = 55)
    private String productName;

    @Column(name = "serial_no", nullable = false, length = 55)
    private String serialNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private Requests requests;
}
