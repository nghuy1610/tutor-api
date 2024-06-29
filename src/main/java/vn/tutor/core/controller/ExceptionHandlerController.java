package vn.tutor.core.controller;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.tutor.core.dto.ErrorDto;
import vn.tutor.core.exception.ErrorCode;
import vn.tutor.core.exception.ResourceNotFoundException;

@RestControllerAdvice
public class ExceptionHandlerController {

  private static final Logger LOGGER = LogManager.getLogger(ExceptionHandlerController.class);

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorDto> handleAuthenticationException(AuthenticationException ex) {
    LOGGER.error("Authentication exception, ex = {}", ExceptionUtils.getStackTrace(ex));
    return constructErrorResponse(ErrorCode.ERROR_001);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorDto> handleAccessDeniedException(AccessDeniedException ex) {
    LOGGER.error("Authorisation exception, ex = {}", ExceptionUtils.getStackTrace(ex));
    return constructErrorResponse(ErrorCode.ERROR_002);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException ex) {
    LOGGER.error("Validation exception, ex = {}", ExceptionUtils.getStackTrace(ex));
    return constructErrorResponse(ErrorCode.ERROR_003, ex);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorDto> handleResourceNotFoundException(ResourceNotFoundException ex) {
    LOGGER.error("Resource not found exception, ex = {}", ExceptionUtils.getStackTrace(ex));
    return constructErrorResponse(ErrorCode.ERROR_004, ex);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDto> handleException(Exception ex) {
    LOGGER.error("No specific handler exception, ex = {}", ExceptionUtils.getStackTrace(ex));
    return constructErrorResponse(ErrorCode.ERROR_000);
  }

  private ResponseEntity<ErrorDto> constructErrorResponse(ErrorCode errorCode) {
    return ResponseEntity.status(errorCode.getHttpStatusCode()).body(new ErrorDto(errorCode));
  }

  private ResponseEntity<ErrorDto> constructErrorResponse(ErrorCode errorCode, Exception ex) {
    return ResponseEntity.status(errorCode.getHttpStatusCode()).body(new ErrorDto(errorCode, ex));
  }
}
