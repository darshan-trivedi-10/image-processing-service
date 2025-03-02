package com.bulk_image_processing_service.imageUpload.repository;

import com.bulk_image_processing_service.imageUpload.dto.ProductImageSheetDto;
import com.bulk_image_processing_service.imageUpload.entity.ProductImageMapping;
import com.bulk_image_processing_service.imageUpload.entity.RequestProductMapping;
import com.bulk_image_processing_service.imageUpload.entity.Requests;
import com.bulk_image_processing_service.imageUpload.enums.ImageProcessRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ImageUploadRepositoryImpl implements ImageUploadRepository{

    private RequestRepository requestRepository;
    private RequestProductMappingRepository requestProductMappingRepository;
    private ProductImageMappingRepository productImageMappingRepository;

    @Autowired
    public ImageUploadRepositoryImpl(
            RequestRepository requestRepository,
            RequestProductMappingRepository requestProductMappingRepository,
            ProductImageMappingRepository productImageMappingRepository
    ){
        this.requestRepository = requestRepository;
        this.requestProductMappingRepository = requestProductMappingRepository;
        this.productImageMappingRepository = productImageMappingRepository;
    }


    @Override
    @Transactional
    public int createImageProcessRequest(List<ProductImageSheetDto> productImageData) {
        Requests requests = new Requests();
        requests.setStatus(ImageProcessRequestStatus.CREATED.toString());
        requests.setCreatedDate(LocalDateTime.now());
        requestRepository.save(requests);

        for (ProductImageSheetDto productImageSheetDto : productImageData){
            RequestProductMapping requestProductMapping = new RequestProductMapping();
            requestProductMapping.setRequests(requests);
            requestProductMapping.setProductName(productImageSheetDto.getProductName());
            requestProductMapping.setSerialNo(productImageSheetDto.getSerialNumber());

            requestProductMappingRepository.save(requestProductMapping);

            List<ProductImageMapping> productImageMappings = new ArrayList<>();
            for (String imageUrl : productImageSheetDto.getProductImages()){
                ProductImageMapping productImageMapping = new ProductImageMapping();
                productImageMapping.setRequestProductMapping(requestProductMapping);
                productImageMapping.setPrecessed(false);
                productImageMapping.setImageUrl(imageUrl);
                productImageMappings.add(productImageMapping);
            }

            productImageMappingRepository.saveAll(productImageMappings);
        }

        return requests.getRequestId();
    }
}
