package com.rentalApp.auth.exceptions.handler;

import com.mongodb.MongoWriteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //Mongo write exception
    @ExceptionHandler({MongoWriteException.class})
    public ResponseEntity<Object> handleMongoWriteException(final MongoWriteException ex, final WebRequest request){
        String bodyResponse = "Username, email or displayName already exists";
        log.error(bodyResponse,ex);
        return handleExceptionInternal(ex,bodyResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,request);
    }

}
