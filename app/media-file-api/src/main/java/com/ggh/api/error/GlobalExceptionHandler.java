package com.ggh.api.error;

import com.ggh.core.crypto.CryptoException;
import com.ggh.core.web.BusinessException;
import com.ggh.core.web.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ErrorResponse handleException(BusinessException e) {
        log.debug(e.getMessage(), e);
        return new ErrorResponse(e.getMessage(), e.errorCode);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ErrorResponse handleException(Exception e) {
        log.debug(e.getMessage(), e);
        return new ErrorResponse(e.getMessage(), MediaFileErrorCode.DEFAULT);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResponse handleException(NoSuchElementException e) {
        log.debug(e.getMessage(), e);
        return new ErrorResponse(e.getMessage(), MediaFileErrorCode.DEFAULT);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CryptoException.class)
    public ErrorResponse handleException(CryptoException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(e.getMessage(), MediaFileErrorCode.DEFAULT);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleServerException(Exception e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(e.getMessage(), MediaFileErrorCode.DEFAULT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var errorResponse = new ErrorResponse(ex.getMessage(), MediaFileErrorCode.DEFAULT);
        return ResponseEntity.status(status)
                .body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> createResponseEntity(Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        var exception = (Exception) request.getAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        String message = "unknown error";
        if (exception != null) {
            message = exception.getMessage();
        }
        var errorResponse = new ErrorResponse(message, MediaFileErrorCode.DEFAULT);
        return ResponseEntity.status(statusCode)
                .body(errorResponse);
    }
}
