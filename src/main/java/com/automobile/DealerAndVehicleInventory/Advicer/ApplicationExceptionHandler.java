package com.automobile.DealerAndVehicleInventory.Advicer;

import com.automobile.DealerAndVehicleInventory.Exception.ResourceNotFoundException;
import com.automobile.DealerAndVehicleInventory.Exception.TenantAccessDeniedException;
import org.springframework.expression.AccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArguments(MethodArgumentNotValidException exception) {
        HashMap<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError ->
        {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, String> handleInvalidArguments(HttpMessageNotReadableException exception) {
        HashMap<String, String> errorMap = new HashMap<>();
        String message = exception.getMessage();
        if (message.contains("Status")) {
            errorMap.put("message", "Invalid value for status. Allowed values: AVAILABLE, SOLD");
        }
        else if(message.contains("SubscriptionType")){
            errorMap.put("message", "Invalid value for SubscriptionType. Allowed values: BASIC, PREMIUM");
        }
        else {
            errorMap.put("message", "Invalid request payload");
        }
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Map<String, String> handleResourceNotFound(ResourceNotFoundException exception) {
        HashMap<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        errorMap.put("statusCode", HttpStatus.NOT_FOUND.toString());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException.class)
    public Map<String, String> handleMissingTenantHeader(MissingRequestHeaderException exception) {
        HashMap<String, String> errorMap = new HashMap<>();
        errorMap.put("message", "X-Tenant-Id is required");
        return errorMap;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(TenantAccessDeniedException.class)
    public Map<String, String> handleMissingTenantHeader(TenantAccessDeniedException exception) {
        HashMap<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        return errorMap;
    }


}
