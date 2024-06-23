package vn.tutor.core.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
        } else {
            this.details = List.of(new ErrorDetails(exception));
        }
    }

    @Getter
    @Setter
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
    }
}
