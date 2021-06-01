package vn.tutor.core.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.tutor.core.dto.ErrorDto;

import java.util.List;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<List<ErrorDto>> handleException(Exception ex) {
        return ResponseEntity.badRequest().body(List.of(ErrorDto.builder().errorMessage(ex.getMessage()).build()));
    }
}
