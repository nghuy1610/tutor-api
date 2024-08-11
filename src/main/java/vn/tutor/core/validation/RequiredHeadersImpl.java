package vn.tutor.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

public class RequiredHeadersImpl implements ConstraintValidator<RequiredHeaders, HttpHeaders> {
  private String[] headers;
  private String message;

  @Override
  public void initialize(RequiredHeaders constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
    this.headers = constraintAnnotation.headers();
    this.message = constraintAnnotation.message();
  }

  @Override
    public boolean isValid(HttpHeaders httpHeaders, ConstraintValidatorContext  context) {
    boolean isValid = true;
    for (String headerName : headers) {
      String headerValue = httpHeaders.getFirst(headerName);
      if (StringUtils.isBlank(headerValue)) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addPropertyNode(headerName).addConstraintViolation();
        isValid = false;
      }
    }
    return isValid;
  }
}
