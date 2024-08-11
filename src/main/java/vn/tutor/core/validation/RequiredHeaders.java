package vn.tutor.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RequiredHeadersImpl.class)
public @interface RequiredHeaders {
  String message() default "Missing required header";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
  String[] headers() default {};
}
