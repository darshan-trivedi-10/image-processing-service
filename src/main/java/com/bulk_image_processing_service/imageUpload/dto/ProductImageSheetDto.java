package com.bulk_image_processing_service.imageUpload.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ProductImageSheetDto {
    private String serialNumber;
    private String productName;
    private List<String> productImages;
}
