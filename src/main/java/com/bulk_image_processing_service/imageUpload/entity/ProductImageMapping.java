package com.bulk_image_processing_service.imageUpload.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(schema = "product_image_processing", name = "product_image_mapping")
@Data
public class ProductImageMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_product_mapping_id", nullable = false)
    private RequestProductMapping requestProductMapping;

    @Column(name = "image_url", nullable = false, length = 255)
    private String imageUrl;

    @Column(name = "is_processed", columnDefinition = "TINYINT DEFAULT 0")
    private boolean isPrecessed;
}

/*
CREATE TABLE product_image_processing.product_image_mapping (
    id INT AUTO_INCREMENT NOT NULL,
    request_product_mapping_id INT NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    is_processed TINYINT DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT fk_request_product_mapping FOREIGN KEY (request_product_mapping_id)
        REFERENCES product_image_processing.request_product_mapping(id)
        ON DELETE CASCADE
);
*/