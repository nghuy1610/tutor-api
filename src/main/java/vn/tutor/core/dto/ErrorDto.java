package vn.tutor.core.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.method.ParameterErrors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import vn.tutor.core.exception.ErrorCode;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class ErrorDto {

  private String errorCode;
  private String errorMessage;
  private List<ErrorDetails> details;

  public ErrorDto(ErrorCode errorCode) {
    this.errorCode = errorCode.getCode();
    this.errorMessage = errorCode.getMessage();
  }

  public ErrorDto(ErrorCode errorCode, Exception exception) {
    this(errorCode);
    if (exception instanceof MethodArgumentNotValidException ex) {
      this.details = ex.getBindingResult().getFieldErrors().stream().map(ErrorDetails::new).toList();
    } else if (exception instanceof HandlerMethodValidationException ex) {
      this.details = ex.getAllErrors().stream().map(ErrorDetails::new).toList();
    } else {
      this.details = List.of(new ErrorDetails(exception));
    }
  }

  @Getter
  @Setter
  @JsonInclude(Include.NON_NULL)
  public static class ErrorDetails {

    private String details;
    private String location;
    private String value;

    public ErrorDetails(Exception exception) {
      this.details = exception.getMessage();
    }

    public ErrorDetails(FieldError fieldError) {
      this.details = fieldError.getDefaultMessage();
      this.location = fieldError.getField();
      this.value = String.valueOf(fieldError.getRejectedValue());
    }

    public ErrorDetails(MessageSourceResolvable msr) {
      if (msr instanceof FieldError fieldError) {
        this.details = fieldError.getDefaultMessage();
        this.location = fieldError.getField();
      } else if (msr instanceof ObjectError objectError) {
        this.details = objectError.getDefaultMessage();
        this.location = objectError.getObjectName();
      }
    }
  }
}
