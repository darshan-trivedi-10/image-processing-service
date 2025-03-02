package com.bulk_image_processing_service.imageUpload.service;

import com.bulk_image_processing_service.imageUpload.dto.ProductImageSheetDto;
import com.bulk_image_processing_service.imageUpload.dto.Response;
import com.bulk_image_processing_service.imageUpload.exception.InvalidSheetFormatException;
import com.bulk_image_processing_service.imageUpload.utils.ReadAndValidateProductImageSheet;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public class ImageUploadServiceImpl implements ImageUploadService{

    private ReadAndValidateProductImageSheet readAndValidateProductImageSheet;

    private final String SUCCESS_MESSAGE = "Your request has been submitted successfully. Request ID: ";

    @Autowired
    public ImageUploadServiceImpl(ReadAndValidateProductImageSheet readAndValidateProductImageSheet){
        this.readAndValidateProductImageSheet = readAndValidateProductImageSheet;
    }

    @Override
    public ResponseEntity<?> processImageSheet(MultipartFile productImageSheet) {
        Response response = new Response();

        try {
            List<ProductImageSheetDto> productDataList = readAndValidateProductImageSheet.readProductImageSheet(productImageSheet);


            response.setMessage("Your request has been submitted successfully. Request ID: ");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (InvalidSheetFormatException exception){
            response.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
