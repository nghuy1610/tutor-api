package vn.tutor.core.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorDto {
    private int status;
    private String errorCode;
    private String errorMessage;
}
