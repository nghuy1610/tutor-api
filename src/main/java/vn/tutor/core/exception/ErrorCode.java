package vn.tutor.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
  ERROR_000("TTE-000", "Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR),
  ERROR_001("TTE-001", "Invalid or missing credentials", HttpStatus.UNAUTHORIZED),
  ERROR_002("TTE-002", "Invalid or missing permission", HttpStatus.FORBIDDEN),
  ERROR_003("TTE-003", "Invalid payload data", HttpStatus.BAD_REQUEST),
  ERROR_004("TTE-004", "Resource not found", HttpStatus.NOT_FOUND),
  ERROR_005("TTE-005", "Bad request", HttpStatus.BAD_REQUEST);

  private final String code;
  private final String message;
  private final HttpStatusCode httpStatusCode;

  ErrorCode(String code, String message, HttpStatusCode httpStatusCode) {
    this.code = code;
    this.message = message;
    this.httpStatusCode = httpStatusCode;
  }
}
