package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationHandler {

    //canh bắt lỗi cho mình
    //định nghĩa cho nó chạy mỗi khi gặp 1 cái exception nào đó

    //MethodArgumentNotValidException: lỗi do thư viện quăng ra

    //nếu gặp lỗi MethodArgumentNotValidException => hàm sẽ được chạy

    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ResponseEntity handleValidationException(MethodArgumentNotValidException exception) {

        String message = "";

        // cứ mỗi thuộc tính lỗi => găn biến vào message
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            //Name, studentCode, score
            message +=  fieldError.getDefaultMessage() + "\n";
        }
        return new ResponseEntity(message, HttpStatus.BAD_REQUEST);   // input đầu vào sai, FE check lại
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleValidationException(Exception exception) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);   // input đầu vào sai, FE check lại
    }
}
