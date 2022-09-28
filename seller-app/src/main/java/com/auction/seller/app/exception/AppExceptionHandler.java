package com.auction.seller.app.exception;

import com.auction.seller.app.model.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppExceptionHandler.class);

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<Object> handleAnyException(CustomException ex){
        LOGGER.info("Exception occured in processing request :: {}",ex.getServiceErrorCode());
        ApiResponse errorMessage = new ApiResponse(false, ex.getServiceErrorCode());

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
