package com.bulk_image_processing_service.imageUpload.exception;

public class InvalidSheetFormatException extends RuntimeException{

    public InvalidSheetFormatException(String message){
        super(message);
    }

    public InvalidSheetFormatException(String message, Throwable cause){
        super(message, cause);
    }
}
