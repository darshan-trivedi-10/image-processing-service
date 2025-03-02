package com.bulk_image_processing_service.imageUpload.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductImageSheetFormat {
    SERIAL_NUMBER("S. No."),
    PRODUCT_NAME("Product Name"),
    INPUT_IMAGE_URL("Input Image Urls");

    final String name;
}
