package com.bulk_image_processing_service.imageUpload.utils;

import com.bulk_image_processing_service.imageUpload.dto.ProductImageSheetDto;
import com.bulk_image_processing_service.imageUpload.enums.ProductImageSheetFormat;
import com.bulk_image_processing_service.imageUpload.exception.InvalidSheetFormatException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class ReadAndValidateProductImageSheet {

    public static final String EMPTY_FILE = "Empty File";
    public static final String INVALID_FORMAT = "Invalid Format";
    public static final String INVALID_ROW = "Invalid ROW at %s";
    public static final String INVALID_IMAGE_URL = "Invalid Image URL at %s";

    public List<ProductImageSheetDto> readProductImageSheet(MultipartFile productImageSheet) {
        if (productImageSheet.isEmpty()){
            throw new InvalidSheetFormatException(EMPTY_FILE);
        }

        List<ProductImageSheetDto> productImageSheetDtoList = new ArrayList<>();
        try (InputStream inputStream = productImageSheet.getInputStream(); Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            if (!isValidHeader(sheet.getRow(0))){
                throw new InvalidSheetFormatException(INVALID_FORMAT);
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++){
                ProductImageSheetDto productImageSheetDto = validateAndReadRow(sheet, i);
                productImageSheetDtoList.add(productImageSheetDto);
            }
        } catch (Exception e) {
            log.error("Unable to Read Product Image Sheet :: {}, {}", e.getMessage(), e.getStackTrace());
        }


        return productImageSheetDtoList;
    }

    private ProductImageSheetDto validateAndReadRow(Sheet sheet, int i) {
        Row row = sheet.getRow(i);
        if (isRowEmpty(row)){
            throw new InvalidSheetFormatException(String.format(INVALID_ROW, i));
        }

        if (!isValidProductImage(sheet.getRow(ProductImageSheetFormat.INPUT_IMAGE_URL.ordinal()))){
            throw new InvalidSheetFormatException(String.format(INVALID_IMAGE_URL, i));
        }

        return createProductImageSheetDto(row);
    }

    private static ProductImageSheetDto createProductImageSheetDto(Row row) {
        ProductImageSheetDto productImageSheetDto = new ProductImageSheetDto();
        productImageSheetDto.setSerialNumber(row.getCell(ProductImageSheetFormat.SERIAL_NUMBER.ordinal()).toString());
        productImageSheetDto.setProductName(row.getCell(ProductImageSheetFormat.PRODUCT_NAME.ordinal()).toString());
        String imageUrls = row.getCell(ProductImageSheetFormat.INPUT_IMAGE_URL.ordinal()).toString();
        productImageSheetDto.setProductImages(Arrays.stream(imageUrls.split(",")).toList());
        return productImageSheetDto;
    }

    private boolean isValidProductImage(Row row) {
        String imageUrl = row.getCell(ProductImageSheetFormat.INPUT_IMAGE_URL.ordinal()).toString();
        return Arrays.stream(imageUrl.split(",")).allMatch(url -> url.startsWith("https://"));
    }

    private boolean isRowEmpty(Row row) {
        if (row.getCell(ProductImageSheetFormat.SERIAL_NUMBER.ordinal()) == null){
            return false;
        }

        if (row.getCell(ProductImageSheetFormat.PRODUCT_NAME.ordinal()) == null){
            return false;
        }

        if (row.getCell(ProductImageSheetFormat.INPUT_IMAGE_URL.ordinal()) == null){
            return false;
        }

        return true;
    }

    private boolean isValidHeader(Row row){
        if (row.getCell(ProductImageSheetFormat.SERIAL_NUMBER.ordinal()) != null && !row.getCell(ProductImageSheetFormat.SERIAL_NUMBER.ordinal()).toString().equals(ProductImageSheetFormat.SERIAL_NUMBER.getName())){
            return false;
        }

        if (row.getCell(ProductImageSheetFormat.PRODUCT_NAME.ordinal()) != null && !row.getCell(ProductImageSheetFormat.PRODUCT_NAME.ordinal()).toString().equals(ProductImageSheetFormat.PRODUCT_NAME.getName())){
            return false;
        }

        return row.getCell(ProductImageSheetFormat.INPUT_IMAGE_URL.ordinal()) == null || row.getCell(ProductImageSheetFormat.INPUT_IMAGE_URL.ordinal()).toString()
                .equals(ProductImageSheetFormat.INPUT_IMAGE_URL.getName());
    }
}
