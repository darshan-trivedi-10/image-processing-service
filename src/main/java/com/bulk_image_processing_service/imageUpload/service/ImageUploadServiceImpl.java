package com.bulk_image_processing_service.imageUpload.service;

import com.bulk_image_processing_service.imageUpload.dto.ProductImageSheetDto;
import com.bulk_image_processing_service.imageUpload.dto.Response;
import com.bulk_image_processing_service.imageUpload.exception.InvalidSheetFormatException;
import com.bulk_image_processing_service.imageUpload.repository.ImageUploadRepository;
import com.bulk_image_processing_service.imageUpload.utils.ReadAndValidateProductImageSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ImageUploadServiceImpl implements ImageUploadService{

    private ReadAndValidateProductImageSheet readAndValidateProductImageSheet;
    private ImageUploadRepository imageUploadRepository;

    private final String RESPONSE_SUCCESS_MESSAGE = "Your request has been submitted successfully. Request ID: %s";

    @Autowired
    public ImageUploadServiceImpl(ReadAndValidateProductImageSheet readAndValidateProductImageSheet, ImageUploadRepository imageUploadRepository){
        this.readAndValidateProductImageSheet = readAndValidateProductImageSheet;
        this.imageUploadRepository = imageUploadRepository;
    }

    @Override
    public ResponseEntity<?> processImageSheet(MultipartFile productImageSheet) {
        Response response = new Response();
        try {
            List<ProductImageSheetDto> productDataList = readAndValidateProductImageSheet.readProductImageSheet(productImageSheet);
            int requestId = imageUploadRepository.createImageProcessRequest(productDataList);
            response.setMessage(String.format(RESPONSE_SUCCESS_MESSAGE, requestId));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (InvalidSheetFormatException exception){
            response.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
