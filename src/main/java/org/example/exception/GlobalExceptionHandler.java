package org.example.exception;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<String> handleDatabaseException(DatabaseException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка БД: "+ex.getMessage());
    }

    @ExceptionHandler(OtherException.class)
    public ResponseEntity<String> handleOtherException(OtherException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handerValidationExceptions (MethodArgumentNotValidException ex) {
        Map<String,String> errors= new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)-> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage =error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        //return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST).b;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getAllErrors());
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?>  handeIllegalArgumentException(IllegalArgumentException ex ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?>  handeRunTimeException(RuntimeException ex ) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
    @ExceptionHandler(IOException.class)
    public ResponseEntity<?>  handeIOException(IOException ex ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<?> handleJsonParseException(JsonParseException ex) {
        String errorMessage = ex.getMessage();
        String location = null;
        if (errorMessage != null) {
            int startIndex = errorMessage.indexOf(" at ");
            int endIndex = errorMessage.lastIndexOf(")");
            if (startIndex != -1 && endIndex != -1) {
                location = errorMessage.substring(startIndex + 4, endIndex);
            }
        }
        if (location != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка парсинга JSON: " + location);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка парсинга JSON: " + errorMessage);
        }
    }


    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<?> handleJsonMappingException (JsonMappingException ex) {
        String fieldname =ex.getPathReference();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка сопоставления Json: Неверный формат анных в поле: " +fieldname);
    }



}
